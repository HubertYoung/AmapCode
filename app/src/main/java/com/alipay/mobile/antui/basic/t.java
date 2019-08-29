package com.alipay.mobile.antui.basic;

/* compiled from: AUHorizontalListView */
final class t implements Runnable {
    final /* synthetic */ AUHorizontalListView a;

    private t(AUHorizontalListView aUHorizontalListView) {
        this.a = aUHorizontalListView;
    }

    /* synthetic */ t(AUHorizontalListView x0, byte b) {
        this(x0);
    }

    public final void run() {
        if (!this.a.mDataChanged) {
            this.a.fireOnSelected();
            this.a.performAccessibilityActionsOnSelected();
        } else if (this.a.mAdapter != null) {
            this.a.post(this);
        }
    }
}
