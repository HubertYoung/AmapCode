package com.alipay.mobile.antui.basic;

import android.database.DataSetObserver;
import android.os.Parcelable;

/* compiled from: AUHorizontalListView */
final class k extends DataSetObserver {
    final /* synthetic */ AUHorizontalListView a;
    private Parcelable b;

    private k(AUHorizontalListView aUHorizontalListView) {
        this.a = aUHorizontalListView;
        this.b = null;
    }

    /* synthetic */ k(AUHorizontalListView x0, byte b2) {
        this(x0);
    }

    public final void onChanged() {
        this.a.mDataChanged = true;
        this.a.mOldItemCount = this.a.mItemCount;
        this.a.mItemCount = this.a.getAdapter().getCount();
        if (!this.a.mHasStableIds || this.b == null || this.a.mOldItemCount != 0 || this.a.mItemCount <= 0) {
            this.a.rememberSyncState();
        } else {
            this.a.onRestoreInstanceState(this.b);
            this.b = null;
        }
        this.a.checkFocus();
        this.a.requestLayout();
    }

    public final void onInvalidated() {
        this.a.mDataChanged = true;
        if (this.a.mHasStableIds) {
            this.b = this.a.onSaveInstanceState();
        }
        this.a.mOldItemCount = this.a.mItemCount;
        this.a.mItemCount = 0;
        this.a.mSelectedPosition = -1;
        this.a.mSelectedRowId = Long.MIN_VALUE;
        this.a.mNextSelectedPosition = -1;
        this.a.mNextSelectedRowId = Long.MIN_VALUE;
        this.a.mNeedSync = false;
        this.a.checkFocus();
        this.a.requestLayout();
    }
}
