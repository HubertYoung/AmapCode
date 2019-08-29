package com.alipay.mobile.antui.basic;

/* compiled from: AUPullRefreshView */
final class ah implements Runnable {
    final /* synthetic */ AUPullRefreshView a;

    ah(AUPullRefreshView this$0) {
        this.a = this$0;
    }

    public final void run() {
        this.a.finishInternal();
    }
}
