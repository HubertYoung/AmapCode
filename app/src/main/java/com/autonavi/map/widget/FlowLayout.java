package com.autonavi.map.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.InputDeviceCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;

public class FlowLayout extends ViewGroup {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private boolean debugDraw = false;
    private int horizontalSpacing = 0;
    private int minCutOff;
    private int orientation = 0;
    private int verticalSpacing = 0;

    public static class LayoutParams extends android.view.ViewGroup.LayoutParams {
        private static int NO_SPACING = -1;
        /* access modifiers changed from: private */
        public int horizontalSpacing = NO_SPACING;
        /* access modifiers changed from: private */
        public boolean newLine = false;
        /* access modifiers changed from: private */
        public int verticalSpacing = NO_SPACING;
        /* access modifiers changed from: private */
        public int x;
        /* access modifiers changed from: private */
        public int y;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            readStyleParameters(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public boolean horizontalSpacingSpecified() {
            return this.horizontalSpacing != NO_SPACING;
        }

        public boolean verticalSpacingSpecified() {
            return this.verticalSpacing != NO_SPACING;
        }

        public void setPosition(int i, int i2) {
            this.x = i;
            this.y = i2;
        }

        private void readStyleParameters(Context context, AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FlowLayout_LayoutParams);
            try {
                this.horizontalSpacing = obtainStyledAttributes.getDimensionPixelSize(R.styleable.FlowLayout_LayoutParams_layout_horizontalSpacing, NO_SPACING);
                this.verticalSpacing = obtainStyledAttributes.getDimensionPixelSize(R.styleable.FlowLayout_LayoutParams_layout_verticalSpacing, NO_SPACING);
                this.newLine = obtainStyledAttributes.getBoolean(R.styleable.FlowLayout_LayoutParams_layout_newLine, false);
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
    }

    public FlowLayout(Context context) {
        super(context);
        readStyleParameters(context, null);
    }

    public FlowLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        readStyleParameters(context, attributeSet);
    }

    public FlowLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        readStyleParameters(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9 = i;
        int i10 = i2;
        int size = (MeasureSpec.getSize(i) - getPaddingRight()) - getPaddingLeft();
        int size2 = (MeasureSpec.getSize(i2) - getPaddingTop()) - getPaddingBottom();
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        if (this.orientation != 0) {
            size = size2;
            mode = mode2;
        }
        int childCount = getChildCount();
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        while (i11 < childCount) {
            View childAt = getChildAt(i11);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                i5 = childCount;
                childAt.measure(getChildMeasureSpec(i9, getPaddingLeft() + getPaddingRight(), layoutParams.width), getChildMeasureSpec(i10, getPaddingTop() + getPaddingBottom(), layoutParams.height));
                int i18 = size - i12;
                if (this.minCutOff > 0 && i18 > this.minCutOff) {
                    if (this.orientation == 0) {
                        if (i18 < childAt.getMeasuredWidth()) {
                            childAt.measure(MeasureSpec.makeMeasureSpec(i18, UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(childAt.getMeasuredHeight(), UCCore.VERIFY_POLICY_QUICK));
                        }
                    } else if (i18 < childAt.getMeasuredHeight()) {
                        childAt.measure(MeasureSpec.makeMeasureSpec(childAt.getMeasuredWidth(), UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(i18, UCCore.VERIFY_POLICY_QUICK));
                    }
                }
                int horizontalSpacing2 = getHorizontalSpacing(layoutParams);
                int verticalSpacing2 = getVerticalSpacing(layoutParams);
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i19 = horizontalSpacing2;
                if (this.orientation == 0) {
                    i6 = measuredHeight;
                } else {
                    i6 = measuredWidth;
                    measuredWidth = measuredHeight;
                    int i20 = i19;
                    i19 = verticalSpacing2;
                    verticalSpacing2 = i20;
                }
                int i21 = i12 + measuredWidth;
                int i22 = i21 + i19;
                if (layoutParams.newLine || (mode != 0 && i21 > size)) {
                    i15 += i16;
                    i16 = i6 + verticalSpacing2;
                    i22 = measuredWidth + i19;
                    i17 = i6;
                    i21 = measuredWidth;
                }
                int max = Math.max(i16, verticalSpacing2 + i6);
                int max2 = Math.max(i17, i6);
                if (this.orientation == 0) {
                    i8 = (getPaddingLeft() + i21) - measuredWidth;
                    i7 = getPaddingTop() + i15;
                } else {
                    i8 = getPaddingLeft() + i15;
                    i7 = (getPaddingTop() + i21) - measuredHeight;
                }
                layoutParams.setPosition(i8, i7);
                i17 = max2;
                i13 = Math.max(i13, i21);
                i16 = max;
                i14 = i15 + max2;
                i12 = i22;
            } else {
                i5 = childCount;
            }
            i11++;
            childCount = i5;
        }
        if (this.orientation == 0) {
            i4 = i13 + getPaddingLeft() + getPaddingRight();
            i3 = i14 + getPaddingBottom() + getPaddingTop();
        } else {
            i4 = i13 + getPaddingBottom() + getPaddingTop();
            i3 = i14 + getPaddingLeft() + getPaddingRight();
        }
        if (this.orientation == 0) {
            setMeasuredDimension(resolveSize(i4, i9), resolveSize(i3, i10));
        } else {
            setMeasuredDimension(resolveSize(i3, i9), resolveSize(i4, i10));
        }
    }

    private int getVerticalSpacing(LayoutParams layoutParams) {
        if (layoutParams.verticalSpacingSpecified()) {
            return layoutParams.verticalSpacing;
        }
        return this.verticalSpacing;
    }

    private int getHorizontalSpacing(LayoutParams layoutParams) {
        if (layoutParams.horizontalSpacingSpecified()) {
            return layoutParams.horizontalSpacing;
        }
        return this.horizontalSpacing;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            childAt.layout(layoutParams.x, layoutParams.y, layoutParams.x + childAt.getMeasuredWidth(), layoutParams.y + childAt.getMeasuredHeight());
        }
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j) {
        boolean drawChild = super.drawChild(canvas, view, j);
        drawDebugInfo(canvas, view);
        return drawChild;
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    private void readStyleParameters(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FlowLayout);
        try {
            this.horizontalSpacing = obtainStyledAttributes.getDimensionPixelSize(R.styleable.FlowLayout_horizontalSpacing, 0);
            this.verticalSpacing = obtainStyledAttributes.getDimensionPixelSize(R.styleable.FlowLayout_verticalSpacing, 0);
            this.orientation = obtainStyledAttributes.getInteger(R.styleable.FlowLayout_orientation, 0);
            this.debugDraw = obtainStyledAttributes.getBoolean(R.styleable.FlowLayout_debugDraw, false);
            this.minCutOff = obtainStyledAttributes.getDimensionPixelSize(R.styleable.FlowLayout_minCutOff, 0);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    private void drawDebugInfo(Canvas canvas, View view) {
        if (this.debugDraw) {
            Paint createPaint = createPaint(InputDeviceCompat.SOURCE_ANY);
            Paint createPaint2 = createPaint(-16711936);
            Paint createPaint3 = createPaint(SupportMenu.CATEGORY_MASK);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (layoutParams.horizontalSpacing > 0) {
                float right = (float) view.getRight();
                float top = ((float) view.getTop()) + (((float) view.getHeight()) / 2.0f);
                float f = top;
                Paint paint = createPaint;
                canvas.drawLine(right, top, right + ((float) layoutParams.horizontalSpacing), f, paint);
                Canvas canvas2 = canvas;
                canvas2.drawLine((((float) layoutParams.horizontalSpacing) + right) - 4.0f, top - 4.0f, right + ((float) layoutParams.horizontalSpacing), f, paint);
                Canvas canvas3 = canvas;
                canvas3.drawLine((((float) layoutParams.horizontalSpacing) + right) - 4.0f, top + 4.0f, right + ((float) layoutParams.horizontalSpacing), f, paint);
            } else if (this.horizontalSpacing > 0) {
                float right2 = (float) view.getRight();
                float top2 = ((float) view.getTop()) + (((float) view.getHeight()) / 2.0f);
                float f2 = top2;
                Paint paint2 = createPaint2;
                canvas.drawLine(right2, top2, right2 + ((float) this.horizontalSpacing), f2, paint2);
                Canvas canvas4 = canvas;
                canvas4.drawLine((((float) this.horizontalSpacing) + right2) - 4.0f, top2 - 4.0f, right2 + ((float) this.horizontalSpacing), f2, paint2);
                Canvas canvas5 = canvas;
                canvas5.drawLine((((float) this.horizontalSpacing) + right2) - 4.0f, top2 + 4.0f, right2 + ((float) this.horizontalSpacing), f2, paint2);
            }
            if (layoutParams.verticalSpacing > 0) {
                float left = ((float) view.getLeft()) + (((float) view.getWidth()) / 2.0f);
                float bottom = (float) view.getBottom();
                float f3 = left;
                Paint paint3 = createPaint;
                canvas.drawLine(left, bottom, f3, bottom + ((float) layoutParams.verticalSpacing), paint3);
                Canvas canvas6 = canvas;
                canvas6.drawLine(left - 4.0f, (((float) layoutParams.verticalSpacing) + bottom) - 4.0f, f3, bottom + ((float) layoutParams.verticalSpacing), paint3);
                Canvas canvas7 = canvas;
                canvas7.drawLine(left + 4.0f, (((float) layoutParams.verticalSpacing) + bottom) - 4.0f, f3, bottom + ((float) layoutParams.verticalSpacing), paint3);
            } else if (this.verticalSpacing > 0) {
                float left2 = ((float) view.getLeft()) + (((float) view.getWidth()) / 2.0f);
                float bottom2 = (float) view.getBottom();
                float f4 = left2;
                Paint paint4 = createPaint2;
                canvas.drawLine(left2, bottom2, f4, bottom2 + ((float) this.verticalSpacing), paint4);
                Canvas canvas8 = canvas;
                canvas8.drawLine(left2 - 4.0f, (((float) this.verticalSpacing) + bottom2) - 4.0f, f4, bottom2 + ((float) this.verticalSpacing), paint4);
                Canvas canvas9 = canvas;
                canvas9.drawLine(left2 + 4.0f, (((float) this.verticalSpacing) + bottom2) - 4.0f, f4, bottom2 + ((float) this.verticalSpacing), paint4);
            }
            if (layoutParams.newLine) {
                if (this.orientation == 0) {
                    float left3 = (float) view.getLeft();
                    float top3 = ((float) view.getTop()) + (((float) view.getHeight()) / 2.0f);
                    canvas.drawLine(left3, top3 - 6.0f, left3, top3 + 6.0f, createPaint3);
                    return;
                }
                float left4 = ((float) view.getLeft()) + (((float) view.getWidth()) / 2.0f);
                float top4 = (float) view.getTop();
                canvas.drawLine(left4 - 6.0f, top4, left4 + 6.0f, top4, createPaint3);
            }
        }
    }

    private Paint createPaint(int i) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(i);
        paint.setStrokeWidth(2.0f);
        return paint;
    }
}
