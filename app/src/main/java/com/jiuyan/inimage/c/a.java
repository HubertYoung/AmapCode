package com.jiuyan.inimage.c;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: RoundCornerShapeDrawable */
public class a extends Drawable {
    private final String a;
    private float[] b;
    private Paint c;
    private RectF d;
    private Path e;
    private float[] f;
    private int g;
    private Style h;
    private float i;

    public a() {
        this.a = a.class.getSimpleName();
        this.b = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        this.c = new Paint();
        this.d = new RectF();
        this.e = new Path();
        this.f = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        this.g = 0;
        this.h = Style.FILL;
        this.c = new Paint(1);
        a();
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    private void a() {
        this.f[0] = this.b[0];
        this.f[1] = this.b[0];
        this.f[2] = this.b[1];
        this.f[3] = this.b[1];
        this.f[4] = this.b[2];
        this.f[5] = this.b[2];
        this.f[6] = this.b[3];
        this.f[7] = this.b[3];
        this.c.setStyle(this.h);
        this.c.setColor(this.g);
        this.c.setStrokeWidth(this.i);
        if (this.h == Style.FILL) {
            this.d.set(0.0f, 0.0f, (float) getBounds().width(), (float) getBounds().height());
        } else if (this.h == Style.STROKE) {
            int ceil = (int) Math.ceil((double) (this.c.getStrokeWidth() / 2.0f));
            this.d.set((float) ceil, (float) ceil, (float) (getBounds().width() - ceil), (float) (getBounds().height() - ceil));
        }
    }

    public void a(int i2) {
        this.g = i2;
        invalidateSelf();
    }

    public void a(b bVar) {
        if (bVar == b.SOLID) {
            this.h = Style.FILL;
        } else if (bVar == b.HOLLOW) {
            this.h = Style.STROKE;
        }
        invalidateSelf();
    }

    public void a(float f2) {
        this.i = f2;
        invalidateSelf();
    }

    public void b(float f2) {
        this.b = new float[]{f2, f2, f2, f2};
        invalidateSelf();
    }

    public void draw(Canvas canvas) {
        a();
        canvas.save();
        canvas.drawColor(0);
        this.e.reset();
        this.e.addRoundRect(this.d, this.f, Direction.CW);
        canvas.drawPath(this.e, this.c);
        canvas.restore();
    }

    public void setAlpha(int i2) {
        this.c.setAlpha(i2);
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public int getOpacity() {
        return 0;
    }
}
