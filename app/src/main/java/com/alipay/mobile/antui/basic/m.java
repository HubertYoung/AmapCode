package com.alipay.mobile.antui.basic;

import android.view.View;

/* compiled from: AUHorizontalListView */
final class m extends u implements Runnable {
    final /* synthetic */ AUHorizontalListView a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    private m(AUHorizontalListView aUHorizontalListView) {
        // this.a = aUHorizontalListView;
        super(aUHorizontalListView, 0);
    }

    /* synthetic */ m(AUHorizontalListView x0, byte b) {
        this(x0);
    }

    public final void run() {
        if (this.a.isPressed() && this.a.mSelectedPosition >= 0) {
            View v = this.a.getChildAt(this.a.mSelectedPosition - this.a.mFirstPosition);
            if (!this.a.mDataChanged) {
                boolean handled = false;
                if (b()) {
                    handled = this.a.performLongPress(v, this.a.mSelectedPosition, this.a.mSelectedRowId);
                }
                if (handled) {
                    this.a.setPressed(false);
                    v.setPressed(false);
                    return;
                }
                return;
            }
            this.a.setPressed(false);
            if (v != null) {
                v.setPressed(false);
            }
        }
    }
}
