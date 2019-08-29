package com.alipay.mobile.antui.basic;

import android.database.DataSetObserver;

/* compiled from: AUPinnedSectionListView */
final class ac extends DataSetObserver {
    final /* synthetic */ AUPinnedSectionListView a;

    ac(AUPinnedSectionListView this$0) {
        this.a = this$0;
    }

    public final void onChanged() {
        this.a.recreatePinnedShadow();
    }

    public final void onInvalidated() {
        this.a.recreatePinnedShadow();
    }
}
