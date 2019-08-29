package com.autonavi.minimap.drive.navi.navitts.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class StickHeadScrollView extends NestedScrollView {
    /* access modifiers changed from: private */
    public View contentView;
    /* access modifiers changed from: private */
    public View headView;
    private int mHeaderState = 0;
    private a mHeaderStateChangedListener;
    /* access modifiers changed from: private */
    public boolean mLayoutChanged = false;

    public interface a {
        void onStateChanged(int i);
    }

    private static int clamp(int i, int i2, int i3) {
        if (i2 > i3 || i < 0) {
            return 0;
        }
        return i;
    }

    public boolean onStartNestedScroll(View view, View view2, int i) {
        return (i & 2) != 0;
    }

    public StickHeadScrollView(Context context) {
        super(context);
    }

    public StickHeadScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public StickHeadScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setHeaderStateChangedListener(a aVar) {
        this.mHeaderStateChangedListener = aVar;
    }

    public void resetHeight(View view, View view2) {
        this.headView = view;
        this.contentView = view2;
    }

    private void resetHeight() {
        if (this.headView != null && this.contentView != null) {
            getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public final void onGlobalLayout() {
                    if (StickHeadScrollView.this.mLayoutChanged) {
                        if (VERSION.SDK_INT >= 16) {
                            StickHeadScrollView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            StickHeadScrollView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }
                        StickHeadScrollView.this.contentView.getLayoutParams().height = StickHeadScrollView.this.getHeight() - StickHeadScrollView.this.headView.getHeight();
                        StickHeadScrollView.this.contentView.requestLayout();
                    }
                }
            });
        }
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mLayoutChanged = z;
        if (this.mLayoutChanged) {
            resetHeight();
        }
    }

    public void scrollTo(int i, int i2) {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            int clamp = clamp(i, (getWidth() - getPaddingRight()) - getPaddingLeft(), childAt.getWidth());
            int clamp2 = clamp(i2, (getHeight() - getPaddingBottom()) - getPaddingTop(), childAt.getHeight());
            if (clamp != getScrollX() || clamp2 != getScrollY()) {
                super.scrollTo(clamp, clamp2);
            }
        }
    }

    public boolean isHiddenTop() {
        return getScrollY() > 0 && getScrollY() >= getChildAt(0).getHeight() - getHeight();
    }

    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        if (!dispatchNestedPreScroll(i, i2, iArr, null)) {
            boolean z = i2 > 0 && getScrollY() < getChildAt(0).getHeight() - getHeight();
            boolean z2 = i2 < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(view, -1);
            if (this.mHeaderStateChangedListener != null) {
                if (i2 > 0 && getScrollY() >= getChildAt(0).getHeight() - getHeight()) {
                    if (this.mHeaderState != 2) {
                        this.mHeaderState = 2;
                        this.mHeaderStateChangedListener.onStateChanged(2);
                    }
                } else if (z2 && this.mHeaderState != 1) {
                    this.mHeaderState = 1;
                    this.mHeaderStateChangedListener.onStateChanged(1);
                }
            }
            if (z || z2) {
                scrollBy(0, i2);
                iArr[1] = i2;
            }
        }
    }
}
