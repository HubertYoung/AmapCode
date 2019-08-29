package com.alipay.mobile.common.logging;

/* compiled from: LoggerFactoryBinder */
final class d implements Runnable {
    final /* synthetic */ LogContextImpl a;

    d(LogContextImpl logContextImpl) {
        this.a = logContextImpl;
    }

    public final void run() {
        this.a.refreshSessionId();
    }
}
