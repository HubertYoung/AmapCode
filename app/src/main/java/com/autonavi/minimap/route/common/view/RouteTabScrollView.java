package com.autonavi.minimap.route.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

public class RouteTabScrollView extends HorizontalScrollView {
    private View mLeftShadow;
    private boolean mNeedAlphaChange = false;
    private View mRightShadow;

    public RouteTabScrollView(Context context) {
        super(context);
    }

    public RouteTabScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RouteTabScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setShadows(View view, View view2) {
        this.mLeftShadow = view;
        this.mRightShadow = view2;
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        dealScrollStatus();
    }

    /* access modifiers changed from: private */
    public void dealScrollStatus() {
        if (this.mNeedAlphaChange) {
            resolveScrollStatus();
        } else {
            resolveScrollShadowNoAlpha();
        }
    }

    public void setAlphaChangeStyle(boolean z) {
        this.mNeedAlphaChange = z;
    }

    private void resolveScrollStatus() {
        String name = getClass().getName();
        StringBuilder sb = new StringBuilder("resolveScrollStatus child width");
        sb.append(getChildAt(0).getMeasuredWidth());
        sb.append(" wid ");
        sb.append(getMeasuredWidth());
        eao.e(name, sb.toString());
        float scrollX = ((float) getScrollX()) / ((float) (getChildAt(0).getMeasuredWidth() - getMeasuredWidth()));
        if (this.mLeftShadow != null) {
            this.mLeftShadow.setAlpha(scrollX);
        }
        if (this.mRightShadow != null) {
            this.mRightShadow.setAlpha(1.0f - scrollX);
        }
    }

    private void resolveScrollShadowNoAlpha() {
        int i = 0;
        int measuredWidth = getChildAt(0).getMeasuredWidth() - getMeasuredWidth();
        int scrollX = getScrollX();
        if (this.mLeftShadow != null) {
            this.mLeftShadow.setAlpha(1.0f);
            this.mLeftShadow.setVisibility(scrollX == 0 ? 8 : 0);
        }
        if (this.mRightShadow != null) {
            this.mRightShadow.setAlpha(1.0f);
            View view = this.mRightShadow;
            if (scrollX == measuredWidth) {
                i = 8;
            }
            view.setVisibility(i);
        }
    }

    public void setShadowIsShow(boolean z) {
        post(new Runnable() {
            public final void run() {
                RouteTabScrollView.this.dealScrollStatus();
            }
        });
        int i = 8;
        if (this.mLeftShadow != null) {
            this.mLeftShadow.setVisibility(z ? 0 : 8);
        }
        if (this.mRightShadow != null) {
            View view = this.mRightShadow;
            if (z) {
                i = 0;
            }
            view.setVisibility(i);
        }
    }
}
