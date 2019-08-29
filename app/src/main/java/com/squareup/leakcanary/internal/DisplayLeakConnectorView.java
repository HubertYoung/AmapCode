package com.squareup.leakcanary.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public final class DisplayLeakConnectorView extends View {
    private static final Paint clearPaint = new Paint(1);
    private static final Paint iconPaint = new Paint(1);
    private static final Paint leakPaint = new Paint(1);
    private static final Paint rootPaint = new Paint(1);
    private Bitmap cache;
    private Type type = Type.NODE;

    public enum Type {
        START,
        NODE,
        END
    }

    static {
        iconPaint.setColor(-4539718);
        rootPaint.setColor(-8083771);
        leakPaint.setColor(-5155506);
        clearPaint.setColor(0);
        clearPaint.setXfermode(LeakCanaryUi.CLEAR_XFER_MODE);
    }

    public DisplayLeakConnectorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public final void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        if (!(this.cache == null || (this.cache.getWidth() == width && this.cache.getHeight() == height))) {
            this.cache.recycle();
            this.cache = null;
        }
        if (this.cache == null) {
            this.cache = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Canvas canvas2 = new Canvas(this.cache);
            float f = (float) width;
            float f2 = f / 2.0f;
            float f3 = (float) height;
            float f4 = f3 / 2.0f;
            float f5 = f / 3.0f;
            float dpToPixel = LeakCanaryUi.dpToPixel(4.0f, getResources());
            iconPaint.setStrokeWidth(dpToPixel);
            rootPaint.setStrokeWidth(dpToPixel);
            switch (this.type) {
                case NODE:
                    canvas2.drawLine(f2, 0.0f, f2, f3, iconPaint);
                    canvas2.drawCircle(f2, f4, f2, iconPaint);
                    canvas2.drawCircle(f2, f4, f5, clearPaint);
                    break;
                case START:
                    float f6 = f2 - (dpToPixel / 2.0f);
                    canvas2.drawRect(0.0f, 0.0f, f, f6, rootPaint);
                    canvas2.drawCircle(0.0f, f6, f6, clearPaint);
                    canvas2.drawCircle(f, f6, f6, clearPaint);
                    Canvas canvas3 = canvas2;
                    float f7 = f2;
                    float f8 = f2;
                    canvas3.drawLine(f7, 0.0f, f8, f4, rootPaint);
                    canvas3.drawLine(f7, f4, f8, f3, iconPaint);
                    canvas2.drawCircle(f2, f4, f2, iconPaint);
                    canvas2.drawCircle(f2, f4, f5, clearPaint);
                    break;
                default:
                    canvas2.drawLine(f2, 0.0f, f2, f4, iconPaint);
                    canvas2.drawCircle(f2, f4, f5, leakPaint);
                    break;
            }
        }
        canvas.drawBitmap(this.cache, 0.0f, 0.0f, null);
    }

    public final void setType(Type type2) {
        if (type2 != this.type) {
            this.type = type2;
            if (this.cache != null) {
                this.cache.recycle();
                this.cache = null;
            }
            invalidate();
        }
    }
}
