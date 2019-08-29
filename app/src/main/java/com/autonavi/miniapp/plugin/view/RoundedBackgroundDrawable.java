package com.autonavi.miniapp.plugin.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class RoundedBackgroundDrawable extends Drawable {
    private boolean drawBottomTriangle;
    private int drawColor;
    private Paint paint = new Paint();
    private int roundCornerRadius;
    private int shadowColor = -2013265920;
    private int shadowOffsetX;
    private int shadowOffsetY;
    private int shadowSize;
    private int triangleHeight;
    private int triangleWidth;

    public int getOpacity() {
        return -3;
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    public void setDrawColor(int i) {
        this.drawColor = i;
        invalidateSelf();
    }

    public void setRoundCorner(int i) {
        this.roundCornerRadius = i;
        invalidateSelf();
    }

    public void setDrawBottomTriangle(boolean z) {
        this.drawBottomTriangle = z;
        invalidateSelf();
    }

    public void setShadowSize(int i) {
        this.shadowSize = i;
        invalidateSelf();
    }

    public void setShadowOffset(int i, int i2) {
        this.shadowOffsetX = i;
        this.shadowOffsetY = i2;
    }

    public void setShadowColor(int i) {
        this.shadowColor = i;
        invalidateSelf();
    }

    public void setBottomTriangleSize(int i, int i2) {
        this.triangleWidth = i;
        this.triangleHeight = i2;
        invalidateSelf();
    }

    public void draw(@NonNull Canvas canvas) {
        int i;
        Rect bounds = getBounds();
        this.paint.setAntiAlias(true);
        this.paint.setColor(this.drawColor);
        this.paint.setStyle(Style.FILL);
        this.paint.setShadowLayer((float) this.shadowSize, (float) this.shadowOffsetX, (float) this.shadowOffsetY, this.shadowColor);
        if (this.drawBottomTriangle) {
            i = (bounds.bottom - this.triangleHeight) - this.shadowSize;
        } else {
            i = bounds.bottom - this.shadowSize;
        }
        RectF rectF = new RectF((float) bounds.left, (float) bounds.top, (float) (bounds.right - this.shadowSize), (float) i);
        float min = (float) Math.min((int) (rectF.height() / 2.0f), this.roundCornerRadius);
        canvas.drawRoundRect(rectF, min, min, this.paint);
        if (this.drawBottomTriangle) {
            int i2 = i - 1;
            float f = (float) (this.triangleWidth / 2);
            float f2 = (float) ((bounds.right - bounds.left) / 2);
            int i3 = bounds.bottom;
            canvas.save();
            canvas.clipRect(new Rect(bounds.left, i2, bounds.right, bounds.bottom));
            Path path = new Path();
            float f3 = (float) ((int) (f2 - f));
            float f4 = (float) i2;
            path.moveTo(f3, f4);
            path.lineTo(f2, (float) (i3 - this.shadowSize));
            path.lineTo((float) ((int) (f + f2)), f4);
            path.lineTo(f3, f4);
            path.close();
            canvas.drawPath(path, this.paint);
            canvas.restore();
        }
    }
}
