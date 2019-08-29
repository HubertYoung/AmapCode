package com.alipay.mobile.antui.load;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;

/* compiled from: AURefreshListView */
final class g implements AnimatorUpdateListener {
    final /* synthetic */ AURefreshListView a;
    private boolean b;
    private int c;
    private int d;
    private int e;
    private View f;

    public g(AURefreshListView aURefreshListView, View view, int targetTopMargin, boolean isCall) {
        this.a = aURefreshListView;
        this.b = isCall;
        this.f = view;
        this.c = targetTopMargin;
        MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
        if (layoutParams != null) {
            this.d = layoutParams.topMargin;
        }
        this.e = this.c - this.d;
    }

    public final void onAnimationUpdate(ValueAnimator animation) {
        int newTopMargin = (int) (((float) this.c) - (((float) this.e) * (1.0f - ((Float) animation.getAnimatedValue()).floatValue())));
        ((MarginLayoutParams) this.f.getLayoutParams()).topMargin = newTopMargin;
        this.a.loadingView.onPullOver(newTopMargin - this.d, this.c - this.d);
        this.f.requestLayout();
        if (newTopMargin >= this.a.loadingViewTopMargin + this.a.refreshDistance) {
            this.a.loadingView.startLoading();
            if (this.a.mOnPullRefreshListener != null && this.b) {
                this.a.mOnPullRefreshListener.onRefresh();
            }
        }
    }
}
