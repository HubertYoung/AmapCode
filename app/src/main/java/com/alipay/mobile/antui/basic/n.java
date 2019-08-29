package com.alipay.mobile.antui.basic;

import android.view.View;

/* compiled from: AUHorizontalListView */
final class n extends u implements Runnable {
    final /* synthetic */ AUHorizontalListView a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    private n(AUHorizontalListView aUHorizontalListView) {
        // this.a = aUHorizontalListView;
        super(aUHorizontalListView, 0);
    }

    /* synthetic */ n(AUHorizontalListView x0, byte b) {
        this(x0);
    }

    public final void run() {
        int motionPosition = this.a.mMotionPosition;
        View child = this.a.getChildAt(motionPosition - this.a.mFirstPosition);
        if (child != null) {
            long longPressId = this.a.mAdapter.getItemId(this.a.mMotionPosition);
            boolean handled = false;
            if (b() && !this.a.mDataChanged) {
                handled = this.a.performLongPress(child, motionPosition, longPressId);
            }
            if (handled) {
                this.a.mTouchMode = -1;
                this.a.setPressed(false);
                child.setPressed(false);
                return;
            }
            this.a.mTouchMode = 2;
        }
    }
}
