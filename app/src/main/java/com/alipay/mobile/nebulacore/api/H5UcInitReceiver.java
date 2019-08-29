package com.alipay.mobile.nebulacore.api;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.alipay.mobile.h5container.api.H5Bundle;
import com.alipay.mobile.h5container.api.H5Context;
import com.alipay.mobile.h5container.api.H5PageReadyListener;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5UcReadyCallBack;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class H5UcInitReceiver extends BroadcastReceiver {
    public static final String TAG = "H5UcInitReceiver";
    private boolean callBackPageReady = false;
    private List<H5Bundle> h5BundleList = Collections.synchronizedList(new ArrayList());
    private List<H5Context> h5ContextList = Collections.synchronizedList(new ArrayList());
    private List<H5PageReadyListener> h5PageReadyListenerList = Collections.synchronizedList(new ArrayList());
    private List<H5UcReadyCallBack> h5UcReadyCallBackList = Collections.synchronizedList(new ArrayList());

    public H5UcInitReceiver(boolean callBackPage) {
        this.callBackPageReady = callBackPage;
    }

    public void addH5Context(H5Context h5Context) {
        this.h5ContextList.add(h5Context);
    }

    public void addH5Bundle(H5Bundle h5Bundle) {
        this.h5BundleList.add(h5Bundle);
    }

    public void addH5PageReadyListener(H5PageReadyListener h5PageReadyListener) {
        this.h5PageReadyListenerList.add(h5PageReadyListener);
    }

    public void addH5UcReadyCallBack(H5UcReadyCallBack h5UcReadyCallBack) {
        this.h5UcReadyCallBackList.add(h5UcReadyCallBack);
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null && H5Param.H5_ACTION_UC_INIT_FINISH.equals(intent.getAction())) {
            H5Log.d(TAG, "receive " + this.callBackPageReady);
            try {
                if (!this.callBackPageReady) {
                    final boolean result = H5Utils.getBoolean(intent.getExtras(), (String) "result", false);
                    if (this.h5UcReadyCallBackList != null && !this.h5UcReadyCallBackList.isEmpty()) {
                        for (final H5UcReadyCallBack h5UcReadyCallBack : this.h5UcReadyCallBackList) {
                            H5Log.d(TAG, "!callBackPageReady uc init result " + result);
                            H5Utils.runOnMain(new Runnable() {
                                public void run() {
                                    if (h5UcReadyCallBack != null) {
                                        h5UcReadyCallBack.usIsReady(result);
                                    }
                                }
                            });
                        }
                    }
                    if (this.h5UcReadyCallBackList != null && !this.h5UcReadyCallBackList.isEmpty()) {
                        this.h5UcReadyCallBackList.clear();
                        return;
                    }
                    return;
                }
                if (this.h5ContextList != null && !this.h5ContextList.isEmpty() && this.h5BundleList != null && !this.h5BundleList.isEmpty() && this.h5PageReadyListenerList != null && !this.h5PageReadyListenerList.isEmpty()) {
                    boolean result2 = H5Utils.getBoolean(intent.getExtras(), (String) "result", false);
                    int i = 0;
                    for (H5Context h5Context : this.h5ContextList) {
                        if (h5Context != null && (h5Context.getContext() instanceof Activity)) {
                            Activity activity = (Activity) h5Context.getContext();
                            if (activity != null && !activity.isFinishing()) {
                                H5Log.d(TAG, "callBackPageReady uc init result " + result2);
                                H5PageReadyListener h5PageReadyListener = this.h5PageReadyListenerList.get(i);
                                H5Bundle h5Bundle = this.h5BundleList.get(i);
                                if (!(h5PageReadyListener == null || h5Bundle == null)) {
                                    h5PageReadyListener.getH5Page(Nebula.getService().createPage(h5Context, h5Bundle));
                                }
                            } else {
                                return;
                            }
                        }
                        i++;
                    }
                }
                if (this.h5ContextList != null && !this.h5ContextList.isEmpty()) {
                    this.h5ContextList.clear();
                }
                if (this.h5BundleList != null && !this.h5BundleList.isEmpty()) {
                    this.h5BundleList.clear();
                }
                if (this.h5PageReadyListenerList != null && !this.h5PageReadyListenerList.isEmpty()) {
                    this.h5PageReadyListenerList.clear();
                }
            } catch (Throwable throwable) {
                H5Log.e((String) TAG, throwable);
            }
        }
    }
}
