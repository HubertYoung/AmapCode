package com.alipay.mobile.antui.basic;

import android.view.View;

/* compiled from: AUHorizontalListView */
final class j implements Runnable {
    final /* synthetic */ View a;
    final /* synthetic */ q b;
    final /* synthetic */ AUHorizontalListView c;

    j(AUHorizontalListView this$0, View view, q qVar) {
        this.c = this$0;
        this.a = view;
        this.b = qVar;
    }

    public final void run() {
        this.c.mTouchMode = -1;
        this.c.setPressed(false);
        this.a.setPressed(false);
        if (!this.c.mDataChanged) {
            this.b.run();
        }
        this.c.mTouchModeReset = null;
    }
}
