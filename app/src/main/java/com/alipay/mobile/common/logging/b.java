package com.alipay.mobile.common.logging;

/* compiled from: LogContextImpl */
final class b implements Runnable {
    final /* synthetic */ LogContextImpl a;

    b(LogContextImpl this$0) {
        this.a = this$0;
    }

    public final void run() {
        this.a.p.a("applog", true);
        this.a.p.a("trafficLog", true);
    }
}
