package com.autonavi.map.core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

public class FlexBoxLayout extends ViewGroup {
    private int horizontalSpace;
    private float mDensity;
    private volatile long mForceLayoutSeq;
    private volatile long mLayoutSeq;
    private int mScreenWidth;
    private int verticalSpace;

    public FlexBoxLayout(Context context) {
        this(context, null);
    }

    public FlexBoxLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mForceLayoutSeq = 0;
        this.mLayoutSeq = 0;
        this.mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
        this.mDensity = context.getResources().getDisplayMetrics().density;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        try {
            int mode = MeasureSpec.getMode(i);
            int size = MeasureSpec.getSize(i);
            int mode2 = MeasureSpec.getMode(i2);
            int size2 = MeasureSpec.getSize(i2);
            int childCount = getChildCount();
            int paddingLeft = getPaddingLeft();
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < childCount; i5++) {
                View childAt = getChildAt(i5);
                if (childAt.getVisibility() != 8) {
                    measureChild(childAt, i, i2);
                    if (childAt.getMeasuredHeight() + childAt.getPaddingTop() + childAt.getPaddingBottom() > i4) {
                        i4 = childAt.getMeasuredHeight() + childAt.getPaddingTop() + childAt.getPaddingBottom();
                    }
                    paddingLeft += childAt.getMeasuredWidth() + dip2px((float) this.horizontalSpace) + childAt.getPaddingLeft() + childAt.getPaddingRight();
                    if (paddingLeft > (size - getPaddingRight()) - getPaddingLeft()) {
                        paddingLeft = getPaddingLeft();
                        i3 += i4 + dip2px((float) this.verticalSpace);
                        i4 = 0;
                    }
                }
            }
            int i6 = i3 + i4;
            if (mode != 1073741824) {
                size = this.mScreenWidth;
            }
            if (mode2 != 1073741824) {
                size2 = i6 + getPaddingBottom() + getPaddingTop();
            }
            setMeasuredDimension(size, size2);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void forceNextLayout() {
        this.mForceLayoutSeq++;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!z) {
            try {
                if (this.mForceLayoutSeq != this.mLayoutSeq) {
                }
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
        this.mLayoutSeq++;
        this.mForceLayoutSeq = this.mLayoutSeq;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int childCount = getChildCount();
        int i5 = paddingLeft;
        int i6 = paddingTop;
        int i7 = 0;
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                if (i8 > 0) {
                    int i9 = i8 - 1;
                    View childAt2 = getChildAt(i9);
                    boolean z2 = childAt2.getVisibility() == 8;
                    if (!z2 && childAt2.getMeasuredHeight() > i7) {
                        i7 = getChildAt(i9).getMeasuredHeight();
                    }
                    i5 += !z2 ? childAt2.getMeasuredWidth() : dip2px((float) this.horizontalSpace) + 0;
                    if (childAt.getMeasuredWidth() + i5 > (getWidth() - getPaddingRight()) - getPaddingLeft()) {
                        i5 = getPaddingLeft();
                        i6 += i7 + dip2px((float) this.verticalSpace);
                        i7 = 0;
                    }
                }
                childAt.layout(i5, i6, childAt.getMeasuredWidth() + i5, childAt.getMeasuredHeight() + i6);
            } else {
                childAt.layout(0, 0, 0, 0);
            }
        }
    }

    private int dip2px(float f) {
        return (int) ((f * this.mDensity) + 0.5f);
    }

    public void setHorizontalSpace(int i) {
        this.horizontalSpace = i;
    }

    public void setVerticalSpace(int i) {
        this.verticalSpace = i;
    }
}
