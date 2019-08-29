package com.alipay.mobile.antui.basic;

/* compiled from: AUTitleBar */
final class aq implements Runnable {
    final /* synthetic */ AUTitleBar a;

    aq(AUTitleBar this$0) {
        this.a = this$0;
    }

    public final void run() {
        this.a.mProgressBar.setVisibility(0);
    }
}
