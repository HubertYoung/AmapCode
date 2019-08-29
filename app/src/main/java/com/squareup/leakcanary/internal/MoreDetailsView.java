package com.squareup.leakcanary.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public final class MoreDetailsView extends View {
    private static final Paint iconPaint;
    private boolean opened;

    static {
        Paint paint = new Paint(1);
        iconPaint = paint;
        paint.setColor(-8083771);
    }

    public MoreDetailsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        iconPaint.setStrokeWidth(LeakCanaryUi.dpToPixel(2.0f, getResources()));
    }

    /* access modifiers changed from: protected */
    public final void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int i = height / 2;
        int i2 = width / 2;
        if (this.opened) {
            float f = (float) i;
            canvas.drawLine(0.0f, f, (float) width, f, iconPaint);
            return;
        }
        float f2 = (float) i;
        canvas.drawLine(0.0f, f2, (float) width, f2, iconPaint);
        float f3 = (float) i2;
        canvas.drawLine(f3, 0.0f, f3, (float) height, iconPaint);
    }

    public final void setOpened(boolean z) {
        if (z != this.opened) {
            this.opened = z;
            invalidate();
        }
    }
}
