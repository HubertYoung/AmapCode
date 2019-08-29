package com.alipay.mobile.antui.dialog;

/* compiled from: AUProgressDialog */
final class ak implements Runnable {
    final /* synthetic */ AUProgressDialog a;

    ak(AUProgressDialog this$0) {
        this.a = this$0;
    }

    public final void run() {
        this.a.cancelAnimation();
    }
}
