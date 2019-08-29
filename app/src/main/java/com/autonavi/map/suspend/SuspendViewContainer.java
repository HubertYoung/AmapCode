package com.autonavi.map.suspend;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SuspendViewContainer extends SuspendViewBaseTemplate {
    public static final int POSITION_BOTTOM_MIDDLE = 7;
    public static final int POSITION_LEFT_BOTTOM = 3;
    public static final int POSITION_LEFT_MIDDLE = 2;
    public static final int POSITION_LEFT_TOP = 1;
    public static final int POSITION_RIGHT_BOTTOM = 6;
    public static final int POSITION_RIGHT_MIDDLE = 5;
    public static final int POSITION_RIGHT_TOP = 4;
    public static final int POSITION_TOP = 0;
    private SuspendPartitionView mBottomMiddleGroup;
    private SuspendPartitionView mLeftGroup;
    private SuspendPartitionView mRightGroup;
    private SuspendPartitionView mTopGroup;
    private float mTopPriority;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SuspendViewPosition {
    }

    public SuspendViewContainer(Context context) {
        this(context, null);
    }

    public SuspendViewContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SuspendViewContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTopPriority = 1.5f;
        init(context);
    }

    private void init(Context context) {
        initPartitions(context);
    }

    public void addView(View view, LayoutParams layoutParams, int i) {
        if (view != null && layoutParams != null) {
            SuspendPartitionView.LayoutParams a = SuspendPartitionView.LayoutParams.a(layoutParams);
            if (i == 3 || i == 6) {
                a.a = 80;
            } else if (i == 2 || i == 5) {
                a.a = 16;
            }
            super.addView(view, a, i);
        }
    }

    /* access modifiers changed from: protected */
    public void initPartitions(Context context) {
        this.mTopGroup = new SuspendPartitionView(context);
        this.mLeftGroup = new SuspendPartitionView(context);
        this.mLeftGroup.setClipChildren(false);
        this.mLeftGroup.setClipToPadding(false);
        this.mRightGroup = new SuspendPartitionView(context);
        this.mBottomMiddleGroup = new SuspendPartitionView(context);
        setOrientation(1, this.mLeftGroup, this.mRightGroup);
        setOrientation(0, this.mTopGroup, this.mBottomMiddleGroup);
        this.mRightGroup.setGravity(5);
        this.mRightGroup.setClipToPadding(false);
        this.mRightGroup.setClipChildren(false);
        addViewInLayout(this.mTopGroup, this.mLeftGroup, this.mRightGroup, this.mBottomMiddleGroup);
    }

    private void addViewInLayout(View... viewArr) {
        int length = viewArr.length;
        for (int i = 0; i < length; i++) {
            addViewInLayout(viewArr[i], i, generateDefaultLayoutParams(), true);
        }
    }

    private void setOrientation(int i, SuspendPartitionView... suspendPartitionViewArr) {
        for (SuspendPartitionView orientation : suspendPartitionViewArr) {
            orientation.setOrientation(i);
        }
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
    }

    public ViewGroup getViewGroupByPosition(int i) {
        switch (i) {
            case 0:
                return this.mTopGroup;
            case 1:
                return this.mLeftGroup;
            case 2:
                return this.mLeftGroup;
            case 3:
                return this.mLeftGroup;
            case 4:
                return this.mRightGroup;
            case 5:
                return this.mRightGroup;
            case 6:
                return this.mRightGroup;
            case 7:
                return this.mBottomMiddleGroup;
            default:
                return this;
        }
    }

    public void setPartitionVisibility(int i, int i2) {
        switch (i) {
            case 0:
                this.mTopGroup.setVisibility(i2);
                return;
            case 1:
                this.mLeftGroup.setStartVisibility(i2);
                return;
            case 2:
                this.mLeftGroup.setCenterVisibility(i2);
                return;
            case 3:
                this.mLeftGroup.setEndVisibility(i2);
                return;
            case 4:
                this.mRightGroup.setStartVisibility(i2);
                return;
            case 5:
                this.mRightGroup.setCenterVisibility(i2);
                return;
            case 6:
                this.mRightGroup.setEndVisibility(i2);
                return;
            case 7:
                this.mBottomMiddleGroup.setVisibility(i2);
                return;
            default:
                throw new IllegalArgumentException("not support position!");
        }
    }

    private void setupMinPriority(boolean z) {
        float f = -2.14748365E9f;
        this.mLeftGroup.setMinPriority(z ? -2.14748365E9f : this.mTopPriority);
        SuspendPartitionView suspendPartitionView = this.mRightGroup;
        if (!z) {
            f = this.mTopPriority;
        }
        suspendPartitionView.setMinPriority(f);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6 = i3 - i;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingBottom = (i4 - i2) - getPaddingBottom();
        int i7 = paddingBottom - paddingTop;
        int paddingRight = i6 - getPaddingRight();
        boolean z2 = this.mLeftGroup.getHeightForPriority(this.mTopPriority) <= i7 && this.mRightGroup.getHeightForPriority(this.mTopPriority) + this.mTopGroup.getMeasuredHeight() <= i7;
        setupMinPriority(z2);
        if (z2) {
            this.mTopGroup.layout(paddingLeft, paddingTop, paddingRight, this.mTopGroup.getMeasuredHeight() + paddingTop);
            i5 = this.mTopGroup.getMeasuredHeight() + paddingTop;
        } else {
            this.mTopGroup.layout(0, 0, 0, 0);
            i5 = paddingTop;
        }
        this.mLeftGroup.layout(paddingLeft, paddingTop, this.mLeftGroup.getMeasuredWidth() + paddingLeft, paddingBottom);
        SuspendPartitionView suspendPartitionView = this.mRightGroup;
        suspendPartitionView.layout(paddingRight - suspendPartitionView.getMeasuredWidth(), i5, paddingRight, paddingBottom);
        this.mBottomMiddleGroup.layout(paddingLeft, paddingBottom - this.mBottomMiddleGroup.getMeasuredHeight(), paddingRight, paddingBottom);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (((double) getAlpha()) <= 0.1d) {
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }
}
