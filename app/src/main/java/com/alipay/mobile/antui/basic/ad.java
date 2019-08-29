package com.alipay.mobile.antui.basic;

/* compiled from: AUPinnedSectionListView */
final class ad implements Runnable {
    final /* synthetic */ AUPinnedSectionListView a;

    ad(AUPinnedSectionListView this$0) {
        this.a = this$0;
    }

    public final void run() {
        this.a.recreatePinnedShadow();
    }
}
