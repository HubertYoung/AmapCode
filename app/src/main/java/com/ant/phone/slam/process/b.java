package com.ant.phone.slam.process;

import com.alipay.alipaylogger.Log;
import com.alipay.streammedia.cvengine.CVNativeEngineApi;

/* compiled from: SlamProcessor */
final class b implements Runnable {
    CVNativeEngineApi a;

    b(CVNativeEngineApi api) {
        this.a = api;
    }

    public final void run() {
        long start = System.currentTimeMillis();
        if (this.a != null) {
            try {
                this.a.Destory();
            } catch (Exception e) {
                Log.e("SlamProcessor", "cvengine destroy exception.e=" + e.getMessage());
            }
        }
        Log.i("SlamProcessor", "CVNativeEngineApi.Destroy cost=" + (System.currentTimeMillis() - start));
    }
}
