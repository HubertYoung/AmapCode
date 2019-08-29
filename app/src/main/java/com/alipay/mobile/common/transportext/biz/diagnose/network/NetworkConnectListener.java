package com.alipay.mobile.common.transportext.biz.diagnose.network;

import android.content.Context;
import android.content.Intent;
import com.alipay.mobile.common.adapter.RigorousNetworkConnReceiver;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.NetworkUtils;

public class NetworkConnectListener extends RigorousNetworkConnReceiver {
    private static final String TAG = "DIAGNOSE-LISTENER";
    private static NetworkConnectListener networkConnectListener = null;

    private NetworkConnectListener(Context context) {
        super(context);
    }

    public static NetworkConnectListener instance(Context context) {
        if (networkConnectListener == null) {
            networkConnectListener = new NetworkConnectListener(context);
        }
        return networkConnectListener;
    }

    public static boolean hasInstance() {
        return networkConnectListener != null;
    }

    public void onReceivee(Context context, Intent intent) {
        if (context == null) {
            LogCatUtil.warn((String) TAG, (String) "context is null.");
        } else if (!NetworkUtils.isNetworkAvailable(context)) {
            SpeedTestManager.netErrCode = -2;
            LogCatUtil.warn((String) TAG, (String) "network is unavailable.");
        } else {
            synchronized (NetworkCheck.class) {
                if (NetworkCheck.currentState != 1) {
                    LogCatUtil.info(TAG, "network change, but already has a detect. this will ignore.");
                    return;
                }
                NetworkCheck.currentState = 2;
                final NetworkCheck networkCheck = new NetworkCheck();
                NetworkAsyncTaskExecutor.executeLazy(new Runnable() {
                    public void run() {
                        LogCatUtil.info(NetworkConnectListener.TAG, "network change. begin to check network by Link.");
                        try {
                            networkCheck.checkNetwork();
                        } catch (Throwable e) {
                            LogCatUtil.warn((String) NetworkConnectListener.TAG, "network change throwable. " + e.toString());
                        } finally {
                            NetworkCheck.currentState = 1;
                        }
                    }
                });
            }
        }
    }
}
