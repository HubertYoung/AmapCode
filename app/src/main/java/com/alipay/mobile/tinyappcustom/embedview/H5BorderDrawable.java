package com.alipay.mobile.tinyappcustom.embedview;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class H5BorderDrawable extends Drawable {
    private static final PathEffect a = new DashPathEffect(new float[]{1.0f, 0.0f}, 0.0f);
    private Paint b = new Paint();
    private RectF c = new RectF();
    private int d = -1;
    private int e = -16777216;
    private float f;
    private float g;
    private String h = "solid";
    private PathEffect i = null;
    private PathEffect j = null;

    H5BorderDrawable() {
        this.b.setAntiAlias(true);
    }

    public void setBackgroundColor(int backgroundColor) {
        this.d = backgroundColor;
    }

    public void setBorder(float borderWidth, int borderColor) {
        this.f = borderWidth;
        this.e = borderColor;
        this.i = new DashPathEffect(new float[]{borderWidth * 4.0f, borderWidth * 4.0f}, 0.0f);
        this.j = new DashPathEffect(new float[]{borderWidth, borderWidth}, 0.0f);
    }

    public void setBorderStyle(String borderStyle) {
        this.h = borderStyle;
    }

    public void setBorderRadius(float borderRadius) {
        this.g = borderRadius;
    }

    public void draw(Canvas canvas) {
        drawBackground(canvas);
        drawBorder(canvas);
    }

    public void drawBackground(Canvas canvas) {
        this.c.set(this.f / 2.0f, this.f / 2.0f, ((float) canvas.getWidth()) - (this.f / 2.0f), ((float) canvas.getHeight()) - (this.f / 2.0f));
        this.b.setStyle(Style.FILL);
        this.b.setColor(this.d);
        if (this.g > 0.0f) {
            canvas.drawRoundRect(this.c, this.g, this.g, this.b);
        } else {
            canvas.drawRect(this.c, this.b);
        }
    }

    public void drawBorder(Canvas canvas) {
        if (this.f > 0.0f) {
            this.c.set(this.f / 2.0f, this.f / 2.0f, ((float) canvas.getWidth()) - (this.f / 2.0f), ((float) canvas.getHeight()) - (this.f / 2.0f));
            this.b.setStyle(Style.STROKE);
            this.b.setStrokeWidth(this.f);
            this.b.setColor(this.e);
            if ("dotted".equals(this.h)) {
                this.b.setPathEffect(this.j);
            } else if ("dashed".equals(this.h)) {
                this.b.setPathEffect(this.i);
            } else {
                this.b.setPathEffect(a);
            }
            if (this.g > 0.0f) {
                canvas.drawRoundRect(this.c, this.g, this.g, this.b);
            } else {
                canvas.drawRect(this.c, this.b);
            }
        }
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public int getOpacity() {
        return -1;
    }
}
