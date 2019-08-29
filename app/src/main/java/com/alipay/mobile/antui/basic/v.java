package com.alipay.mobile.antui.basic;

/* compiled from: AUNetErrorView */
final class v implements Runnable {
    final /* synthetic */ AUNetErrorView a;

    v(AUNetErrorView this$0) {
        this.a = this$0;
    }

    public final void run() {
        this.a.resetNetErrorType(this.a.mType);
    }
}
