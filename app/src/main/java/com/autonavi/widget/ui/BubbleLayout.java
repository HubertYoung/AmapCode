package com.autonavi.widget.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.autonavi.minimap.R;

public class BubbleLayout extends RelativeLayout {
    public static float DEFAULT_STROKE_WIDTH = -1.0f;
    private int mArrowDirection;
    private float mArrowHeight;
    private float mArrowPosition;
    private float mArrowWidth;
    private ero mBubble;
    private int mBubbleColor;
    private float mCornersRadius;
    private int mStrokeColor;
    private float mStrokeWidth;

    public BubbleLayout(Context context) {
        this(context, null, 0);
    }

    public BubbleLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BubbleLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.BubbleLayout);
        this.mArrowWidth = obtainStyledAttributes.getDimension(R.styleable.BubbleLayout_bubble_arrowWidth, convertDpToPixel(8.0f, context));
        this.mArrowHeight = obtainStyledAttributes.getDimension(R.styleable.BubbleLayout_bubble_arrowHeight, convertDpToPixel(8.0f, context));
        this.mCornersRadius = obtainStyledAttributes.getDimension(R.styleable.BubbleLayout_bubble_cornersRadius, 0.0f);
        this.mArrowPosition = obtainStyledAttributes.getDimension(R.styleable.BubbleLayout_bubble_arrowPosition, convertDpToPixel(12.0f, context));
        this.mBubbleColor = obtainStyledAttributes.getColor(R.styleable.BubbleLayout_bubble_bubbleColor, -1);
        this.mStrokeWidth = obtainStyledAttributes.getDimension(R.styleable.BubbleLayout_bubble_strokeWidth, DEFAULT_STROKE_WIDTH);
        this.mStrokeColor = obtainStyledAttributes.getColor(R.styleable.BubbleLayout_bubble_strokeColor, -7829368);
        this.mArrowDirection = obtainStyledAttributes.getInt(R.styleable.BubbleLayout_bubble_arrowDirection, 0);
        obtainStyledAttributes.recycle();
        initPadding();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        initDrawable(0, getWidth(), 0, getHeight());
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        if (this.mBubble != null) {
            this.mBubble.draw(canvas);
        }
        super.dispatchDraw(canvas);
    }

    private void initDrawable(int i, int i2, int i3, int i4) {
        if (i2 >= i && i4 >= i3) {
            ero ero = new ero(new RectF((float) i, (float) i3, (float) i2, (float) i4), this.mArrowWidth, this.mCornersRadius, this.mArrowHeight, this.mArrowPosition, this.mStrokeWidth, this.mStrokeColor, this.mBubbleColor, this.mArrowDirection);
            this.mBubble = ero;
        }
    }

    private void initPadding() {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        switch (this.mArrowDirection) {
            case 0:
                paddingLeft = (int) (((float) paddingLeft) + this.mArrowWidth);
                break;
            case 1:
                paddingTop = (int) (((float) paddingTop) + this.mArrowHeight);
                break;
            case 2:
                paddingRight = (int) (((float) paddingRight) + this.mArrowWidth);
                break;
            case 3:
                paddingBottom = (int) (((float) paddingBottom) + this.mArrowHeight);
                break;
        }
        if (this.mStrokeWidth > 0.0f) {
            paddingLeft = (int) (((float) paddingLeft) + this.mStrokeWidth);
            paddingRight = (int) (((float) paddingRight) + this.mStrokeWidth);
            paddingTop = (int) (((float) paddingTop) + this.mStrokeWidth);
            paddingBottom = (int) (((float) paddingBottom) + this.mStrokeWidth);
        }
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    private void resetPadding() {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        switch (this.mArrowDirection) {
            case 0:
                paddingLeft = (int) (((float) paddingLeft) - this.mArrowWidth);
                break;
            case 1:
                paddingTop = (int) (((float) paddingTop) - this.mArrowHeight);
                break;
            case 2:
                paddingRight = (int) (((float) paddingRight) - this.mArrowWidth);
                break;
            case 3:
                paddingBottom = (int) (((float) paddingBottom) - this.mArrowHeight);
                break;
        }
        if (this.mStrokeWidth > 0.0f) {
            paddingLeft = (int) (((float) paddingLeft) - this.mStrokeWidth);
            paddingRight = (int) (((float) paddingRight) - this.mStrokeWidth);
            paddingTop = (int) (((float) paddingTop) - this.mStrokeWidth);
            paddingBottom = (int) (((float) paddingBottom) - this.mStrokeWidth);
        }
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    static float convertDpToPixel(float f, Context context) {
        return f * ((float) (context.getResources().getDisplayMetrics().densityDpi / 160));
    }

    public BubbleLayout setArrowDirection(int i) {
        resetPadding();
        this.mArrowDirection = i;
        initPadding();
        return this;
    }

    public BubbleLayout setArrowWidth(float f) {
        resetPadding();
        this.mArrowWidth = f;
        initPadding();
        return this;
    }

    public BubbleLayout setCornersRadius(float f) {
        this.mCornersRadius = f;
        requestLayout();
        return this;
    }

    public BubbleLayout setArrowHeight(float f) {
        resetPadding();
        this.mArrowHeight = f;
        initPadding();
        return this;
    }

    public BubbleLayout setArrowPosition(float f) {
        resetPadding();
        this.mArrowPosition = f;
        initPadding();
        return this;
    }

    public BubbleLayout setBubbleColor(int i) {
        this.mBubbleColor = i;
        requestLayout();
        return this;
    }

    public BubbleLayout setStrokeWidth(float f) {
        resetPadding();
        this.mStrokeWidth = f;
        initPadding();
        return this;
    }

    public BubbleLayout setStrokeColor(int i) {
        this.mStrokeColor = i;
        requestLayout();
        return this;
    }
}
