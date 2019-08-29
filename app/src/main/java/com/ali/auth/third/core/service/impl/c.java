package com.ali.auth.third.core.service.impl;

import com.ali.auth.third.core.trace.SDKLogger;

class c implements Runnable {
    final /* synthetic */ Runnable a;
    final /* synthetic */ a b;

    c(a aVar, Runnable runnable) {
        this.b = aVar;
        this.a = runnable;
    }

    public void run() {
        try {
            this.a.run();
        } catch (Throwable th) {
            SDKLogger.e("kernel", th.getMessage(), th);
        }
    }
}
