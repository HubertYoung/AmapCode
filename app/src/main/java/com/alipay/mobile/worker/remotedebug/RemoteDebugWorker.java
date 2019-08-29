package com.alipay.mobile.worker.remotedebug;

import android.util.Log;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.worker.WebWorker;

public class RemoteDebugWorker extends WebWorker {
    private static final String c = RemoteDebugWorker.class.getSimpleName();

    /* access modifiers changed from: protected */
    public final void a() {
        this.a = new RemoteDebugWorkerControllerProvider(this);
    }

    /* access modifiers changed from: protected */
    public final void d() {
        Log.e(c, "doInjectStartupParamsAndPushWorker");
        long time = System.currentTimeMillis();
        try {
            TinyAppRemoteDebugInterceptor interceptor = TinyAppRemoteDebugInterceptorManager.get().getTinyAppRemoteDebugInterceptor();
            if (interceptor != null) {
                interceptor.remoteLoadUrl(c());
            }
        } catch (Throwable thr) {
            H5Log.e(c, "doInjectStartupParamsAndPushWorker error: ", thr);
        }
        H5Log.e(c, "doInjectStartupParamsAndPushWorker cost = " + (System.currentTimeMillis() - time));
        b();
    }

    public void sendToWorker(String appId, String messageId, String action, String message, H5CallBack h5CallBack) {
        TinyAppRemoteDebugInterceptor interceptor = TinyAppRemoteDebugInterceptorManager.get().getTinyAppRemoteDebugInterceptor();
        if (interceptor != null) {
            interceptor.sendMessageToRemoteWorker(a(action, message));
        }
    }
}
