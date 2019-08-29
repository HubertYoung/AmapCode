package com.alipay.mobile.antui.basic;

import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/* compiled from: AUPullRefreshView */
final class ag implements OnGlobalLayoutListener {
    final /* synthetic */ AUPullRefreshView a;

    ag(AUPullRefreshView this$0) {
        this.a = this$0;
    }

    public final void onGlobalLayout() {
        this.a.mMaxMagin = this.a.mOverView.getOverViewHeight();
        this.a.getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }
}
