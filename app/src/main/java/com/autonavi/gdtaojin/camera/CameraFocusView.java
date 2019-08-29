package com.autonavi.gdtaojin.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

public class CameraFocusView extends View {
    private Paint mOvalPaint;
    private int mOval_b;
    private int mOval_l;
    private int mOval_r;
    private int mOval_t;
    private int mStrokeWidth = 2;
    private int padding = 3;

    public CameraFocusView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initRocketView();
    }

    private void initRocketView() {
        this.mOvalPaint = new Paint();
        this.mOvalPaint.setAntiAlias(true);
        this.mOvalPaint.setColor(-1);
        this.mOvalPaint.setStyle(Style.STROKE);
        this.mOvalPaint.setStrokeWidth((float) this.mStrokeWidth);
        setPadding(this.padding, this.padding, this.padding, this.padding);
    }

    public void changeRecColor(boolean z) {
        if (z) {
            this.mOvalPaint.setColor(-16711936);
        } else {
            this.mOvalPaint.setColor(-1);
        }
    }

    public void setOvalRect(int i, int i2, int i3, int i4) {
        this.mOval_l = i + this.padding;
        this.mOval_t = i2 + this.padding;
        this.mOval_r = i3;
        this.mOval_b = i4;
        requestLayout();
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0);
        canvas.drawRect(new RectF((float) this.mOval_l, (float) this.mOval_t, (float) this.mOval_r, (float) this.mOval_b), this.mOvalPaint);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        setMeasuredDimension(measureWidth(i), measureHeight(i2));
    }

    private int measureWidth(int i) {
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        if (mode == 1073741824) {
            return size;
        }
        int paddingLeft = this.mOval_r + getPaddingLeft() + getPaddingRight();
        return mode == Integer.MIN_VALUE ? Math.min(paddingLeft, size) : paddingLeft;
    }

    private int measureHeight(int i) {
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        if (mode == 1073741824) {
            return size;
        }
        int paddingTop = this.mOval_b + getPaddingTop() + getPaddingBottom();
        return mode == Integer.MIN_VALUE ? Math.min(paddingTop, size) : paddingTop;
    }
}
