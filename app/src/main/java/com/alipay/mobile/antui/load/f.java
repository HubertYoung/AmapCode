package com.alipay.mobile.antui.load;

import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/* compiled from: AURefreshListView */
final class f extends Animation {
    final /* synthetic */ AURefreshListView a;
    private int b;
    private int c;
    private int d;
    private View e;
    private boolean f;

    protected f(AURefreshListView aURefreshListView, View view, int targetTopMargin) {
        this.a = aURefreshListView;
        this.e = view;
        this.b = targetTopMargin;
        MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
        if (layoutParams != null) {
            this.c = layoutParams.topMargin;
        }
        this.d = this.b - this.c;
    }

    public final void a(boolean animationEnded) {
        this.f = animationEnded;
    }

    /* access modifiers changed from: protected */
    public final void applyTransformation(float interpolatedTime, Transformation t) {
        if (!this.f) {
            ((MarginLayoutParams) this.e.getLayoutParams()).topMargin = (int) (((float) this.b) - (((float) this.d) * (1.0f - interpolatedTime)));
            this.e.requestLayout();
        }
    }
}
