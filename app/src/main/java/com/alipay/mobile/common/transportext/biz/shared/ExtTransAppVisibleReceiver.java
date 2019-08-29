package com.alipay.mobile.common.transportext.biz.shared;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.iprank.biz.SpeedTestBiz;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transportext.biz.shared.spdy.SpdyShortTimeoutHelper;
import java.util.concurrent.TimeUnit;

public class ExtTransAppVisibleReceiver extends BroadcastReceiver {
    private static final String TAG = "ExtTransAppVisibleReceiver";
    private boolean resume = true;
    private boolean screenon = true;

    public void regiester() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.alipay.mobile.framework.BROUGHT_TO_FOREGROUND");
            intentFilter.addAction("com.alipay.mobile.framework.USERLEAVEHINT");
            LocalBroadcastManager.getInstance(ExtTransportEnv.getAppContext()).registerReceiver(this, intentFilter);
            IntentFilter scrIntentFilter = new IntentFilter();
            scrIntentFilter.addAction("android.intent.action.SCREEN_OFF");
            scrIntentFilter.addAction("android.intent.action.SCREEN_ON");
            ExtTransportEnv.getAppContext().registerReceiver(this, scrIntentFilter);
        } catch (Exception e) {
            LogCatUtil.warn((String) TAG, (Throwable) e);
        }
    }

    public void unregister() {
        try {
            LocalBroadcastManager.getInstance(ExtTransportEnv.getAppContext()).unregisterReceiver(this);
        } catch (Exception e) {
            LogCatUtil.warn((String) TAG, "unregister exception: " + e.toString());
        }
    }

    public void onReceive(Context context, Intent intent) {
        LoggerFactory.getTraceLogger().info("Monitor", "onReceive:" + getClass().getSimpleName());
        String action = intent.getAction();
        if ("com.alipay.mobile.framework.BROUGHT_TO_FOREGROUND".equalsIgnoreCase(action)) {
            this.resume = true;
            if (this.screenon) {
                onResume();
            }
        } else if ("com.alipay.mobile.framework.USERLEAVEHINT".equalsIgnoreCase(action)) {
            this.resume = false;
            onStop();
        } else if ("android.intent.action.SCREEN_ON".equals(action)) {
            this.screenon = true;
            if (this.resume) {
                onResume();
            }
        } else if ("android.intent.action.SCREEN_OFF".equals(action)) {
            this.screenon = false;
            if (this.resume) {
                onStop();
            }
        }
    }

    private void onResume() {
        SpdyShortTimeoutHelper.adjustmentSpdyTimeout();
        triggerTestCases();
        stopSpeedTestTask(ExtTransportEnv.getAppContext());
    }

    private void onStop() {
        SpdyShortTimeoutHelper.setLeaveTime(System.currentTimeMillis());
        mayResumeSpeedTestTask(ExtTransportEnv.getAppContext());
    }

    private void stopSpeedTestTask(final Context context) {
        NetworkAsyncTaskExecutor.executeLazy(new Runnable() {
            public void run() {
                SpeedTestBiz.getInstance(context).setShouldStop(true);
            }
        });
    }

    private void mayResumeSpeedTestTask(final Context context) {
        NetworkAsyncTaskExecutor.executeLazy(new Runnable() {
            public void run() {
                SpeedTestBiz.getInstance(context).setShouldStop(false);
            }
        });
    }

    private void triggerTestCases() {
        if (MiscUtils.isDebugger(ExtTransportEnv.getAppContext())) {
            NetworkAsyncTaskExecutor.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    try {
                        LogCatUtil.info(ExtTransAppVisibleReceiver.TAG, "triggerTestCases#run  start");
                        Class aClass = Class.forName("com.alipay.mobile.common.transportext.test.ExtTestCaseSuit");
                        aClass.getDeclaredMethod("main", new Class[0]).invoke(aClass, new Object[0]);
                    } catch (Throwable e) {
                        LogCatUtil.warn((String) ExtTransAppVisibleReceiver.TAG, "triggerTestCases#run exception: " + e.toString());
                    }
                }
            }, 10, 40, TimeUnit.SECONDS);
        }
    }
}
