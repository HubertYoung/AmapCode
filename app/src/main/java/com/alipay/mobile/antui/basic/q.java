package com.alipay.mobile.antui.basic;

import android.view.View;
import android.widget.ListAdapter;

/* compiled from: AUHorizontalListView */
final class q extends u implements Runnable {
    int a;
    final /* synthetic */ AUHorizontalListView b;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    private q(AUHorizontalListView aUHorizontalListView) {
        // this.b = aUHorizontalListView;
        super(aUHorizontalListView, 0);
    }

    /* synthetic */ q(AUHorizontalListView x0, byte b2) {
        this(x0);
    }

    public final void run() {
        if (!this.b.mDataChanged) {
            ListAdapter adapter = this.b.mAdapter;
            int motionPosition = this.a;
            if (adapter != null && this.b.mItemCount > 0 && motionPosition != -1 && motionPosition < adapter.getCount() && b()) {
                View child = this.b.getChildAt(motionPosition - this.b.mFirstPosition);
                if (child != null) {
                    this.b.performItemClick(child, motionPosition, adapter.getItemId(motionPosition));
                }
            }
        }
    }
}
