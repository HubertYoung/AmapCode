package com.alipay.mobile.antui.load;

import android.content.Context;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;

/* compiled from: GridViewWithHeaderAndFooter */
final class j extends FrameLayout {
    final /* synthetic */ GridViewWithHeaderAndFooter a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public j(GridViewWithHeaderAndFooter gridViewWithHeaderAndFooter, Context context) {
        // this.a = gridViewWithHeaderAndFooter;
        super(context);
    }

    /* access modifiers changed from: protected */
    public final void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int realLeft = getPaddingLeft();
        if (realLeft != left) {
            offsetLeftAndRight(realLeft - left);
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    /* access modifiers changed from: protected */
    public final void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(this.a.getMeasuredWidth(), MeasureSpec.getMode(widthMeasureSpec)), heightMeasureSpec);
    }
}
