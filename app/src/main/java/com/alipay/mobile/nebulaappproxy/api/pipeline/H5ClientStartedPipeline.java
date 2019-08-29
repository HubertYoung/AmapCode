package com.alipay.mobile.nebulaappproxy.api.pipeline;

import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5NetworkUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulaappproxy.api.receiver.H5NetChangeReceiver;
import com.alipay.mobile.nebulaappproxy.api.receiver.H5UserActionReceiver;

public class H5ClientStartedPipeline implements Runnable {
    public void run() {
        try {
            H5Log.d("H5ClientStartedPipeline", "H5ClientStartedPipeline start");
            a();
            b();
        } catch (Throwable throwable) {
            H5Log.e((String) "H5ClientStartedPipeline", throwable);
        }
    }

    private static void a() {
        H5NetworkUtil.getInstance().addListener(new H5NetChangeReceiver());
    }

    private static void b() {
        H5UserActionReceiver h5UserActionReceiver = new H5UserActionReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.alipay.mobile.framework.BROUGHT_TO_FOREGROUND");
        intentFilter.addAction("com.alipay.mobile.framework.USERLEAVEHINT");
        LocalBroadcastManager.getInstance(H5Utils.getContext()).registerReceiver(h5UserActionReceiver, intentFilter);
    }
}
