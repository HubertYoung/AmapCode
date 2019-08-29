package com.alipay.sdk.app;

final class g implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ boolean b;
    final /* synthetic */ H5PayCallback c;
    final /* synthetic */ PayTask d;

    g(PayTask payTask, String str, boolean z, H5PayCallback h5PayCallback) {
        this.d = payTask;
        this.a = str;
        this.b = z;
        this.c = h5PayCallback;
    }

    public final void run() {
        this.c.onPayResult(this.d.h5Pay(this.a, this.b));
    }
}
