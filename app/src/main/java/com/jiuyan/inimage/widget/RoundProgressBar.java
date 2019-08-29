package com.jiuyan.inimage.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.v4.internal.view.SupportMenu;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.ab;

public class RoundProgressBar extends TextView {
    private Paint a;
    private int b;
    private int c;
    private int d;
    private float e;
    private float f;
    private int g;
    private int h;
    private boolean i;
    private int j;

    public RoundProgressBar(Context context) {
        this(context, null);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public RoundProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.a = new Paint();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ab.RoundProgressBar);
        this.b = obtainStyledAttributes.getColor(0, SupportMenu.CATEGORY_MASK);
        this.c = obtainStyledAttributes.getColor(1, -16711936);
        this.d = obtainStyledAttributes.getColor(3, -16711936);
        this.e = obtainStyledAttributes.getDimension(4, 15.0f);
        this.f = obtainStyledAttributes.getDimension(2, 5.0f);
        this.g = obtainStyledAttributes.getInteger(5, 100);
        this.i = obtainStyledAttributes.getBoolean(6, true);
        this.j = obtainStyledAttributes.getInt(7, 0);
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() / 2;
        int i2 = (int) (((float) width) - (this.f / 2.0f));
        this.a.setColor(this.b);
        this.a.setStyle(Style.STROKE);
        this.a.setStrokeWidth(this.f);
        this.a.setAntiAlias(true);
        canvas.drawCircle((float) width, (float) width, (float) i2, this.a);
        this.a.setStrokeWidth(0.0f);
        this.a.setColor(this.d);
        this.a.setStrokeWidth(this.f);
        this.a.setColor(this.c);
        RectF rectF = new RectF((float) (width - i2), (float) (width - i2), (float) (width + i2), (float) (width + i2));
        switch (this.j) {
            case 0:
                this.a.setStyle(Style.STROKE);
                canvas.drawArc(rectF, 0.0f, (float) ((this.h * 360) / this.g), false, this.a);
                return;
            case 1:
                this.a.setStyle(Style.FILL_AND_STROKE);
                if (this.h != 0) {
                    canvas.drawArc(rectF, 0.0f, (float) ((this.h * 360) / this.g), true, this.a);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public synchronized int getMax() {
        return this.g;
    }

    public synchronized void setMax(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.g = i2;
    }

    public synchronized int getProgress() {
        return this.h;
    }

    public synchronized void setProgress(float f2) {
        if (f2 < 0.0f) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (f2 > ((float) this.g)) {
            f2 = (float) this.g;
        }
        if (f2 <= ((float) this.g)) {
            this.h = (int) f2;
            setText(String.valueOf((int) ((f2 / ((float) this.g)) * 100.0f)) + "%");
            postInvalidate();
        }
    }

    public void setProgressText(String str) {
        setText(str);
        postInvalidate();
    }

    public int getCricleColor() {
        return this.b;
    }

    public void setCricleColor(int i2) {
        this.b = i2;
    }

    public int getCricleProgressColor() {
        return this.c;
    }

    public void setCricleProgressColor(int i2) {
        this.c = i2;
    }

    public int getTextColor() {
        return this.d;
    }

    public void setTextColor(int i2) {
        this.d = i2;
    }

    public float getTextSize() {
        return this.e;
    }

    public void setTextSize(float f2) {
        this.e = f2;
    }

    public float getRoundWidth() {
        return this.f;
    }

    public void setRoundWidth(float f2) {
        this.f = f2;
    }
}
