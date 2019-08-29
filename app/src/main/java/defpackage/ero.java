package defpackage;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/* renamed from: ero reason: default package */
/* compiled from: BubbleDrawable */
public final class ero extends Drawable {
    private RectF a;
    private Path b = new Path();
    private Paint c = new Paint(1);
    private Path d;
    private Paint e;
    private float f;
    private float g;
    private float h;
    private float i;
    private float j;

    public final int getOpacity() {
        return -3;
    }

    public ero(RectF rectF, float f2, float f3, float f4, float f5, float f6, int i2, int i3, int i4) {
        this.a = rectF;
        this.f = f2;
        this.g = f3;
        this.h = f4;
        this.i = f5;
        this.j = f6;
        this.c.setColor(i3);
        if (f6 > 0.0f) {
            this.e = new Paint(1);
            this.e.setColor(i2);
            this.d = new Path();
            a(i4, this.b, f6);
            a(i4, this.d, 0.0f);
            return;
        }
        a(i4, this.b, 0.0f);
    }

    /* access modifiers changed from: protected */
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
    }

    public final void draw(@NonNull Canvas canvas) {
        if (this.j > 0.0f) {
            canvas.drawPath(this.d, this.e);
        }
        canvas.drawPath(this.b, this.c);
    }

    public final void setAlpha(int i2) {
        this.c.setAlpha(i2);
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.c.setColorFilter(colorFilter);
    }

    public final int getIntrinsicWidth() {
        return (int) this.a.width();
    }

    public final int getIntrinsicHeight() {
        return (int) this.a.height();
    }

    private void a(int i2, Path path, float f2) {
        switch (i2) {
            case 0:
                if (this.g <= 0.0f) {
                    b(this.a, path, f2);
                    return;
                } else if (f2 <= 0.0f || f2 <= this.g) {
                    a(this.a, path, f2);
                    return;
                } else {
                    b(this.a, path, f2);
                    return;
                }
            case 1:
                if (this.g <= 0.0f) {
                    d(this.a, path, f2);
                    return;
                } else if (f2 <= 0.0f || f2 <= this.g) {
                    c(this.a, path, f2);
                    return;
                } else {
                    d(this.a, path, f2);
                    return;
                }
            case 2:
                if (this.g <= 0.0f) {
                    f(this.a, path, f2);
                    return;
                } else if (f2 <= 0.0f || f2 <= this.g) {
                    e(this.a, path, f2);
                    return;
                } else {
                    f(this.a, path, f2);
                    return;
                }
            case 3:
                if (this.g <= 0.0f) {
                    h(this.a, path, f2);
                    return;
                } else if (f2 <= 0.0f || f2 <= this.g) {
                    g(this.a, path, f2);
                    break;
                } else {
                    h(this.a, path, f2);
                    return;
                }
        }
    }

    private void a(RectF rectF, Path path, float f2) {
        path.moveTo(this.f + rectF.left + this.g + f2, rectF.top + f2);
        path.lineTo((rectF.width() - this.g) - f2, rectF.top + f2);
        path.arcTo(new RectF(rectF.right - this.g, rectF.top + f2, rectF.right - f2, this.g + rectF.top), 270.0f, 90.0f);
        path.lineTo(rectF.right - f2, (rectF.bottom - this.g) - f2);
        path.arcTo(new RectF(rectF.right - this.g, rectF.bottom - this.g, rectF.right - f2, rectF.bottom - f2), 0.0f, 90.0f);
        path.lineTo(rectF.left + this.f + this.g + f2, rectF.bottom - f2);
        path.arcTo(new RectF(rectF.left + this.f + f2, rectF.bottom - this.g, this.g + rectF.left + this.f, rectF.bottom - f2), 90.0f, 90.0f);
        float f3 = f2 / 2.0f;
        path.lineTo(rectF.left + this.f + f2, (this.h + this.i) - f3);
        path.lineTo(rectF.left + f2 + f2, this.i + (this.h / 2.0f));
        path.lineTo(rectF.left + this.f + f2, this.i + f3);
        path.lineTo(rectF.left + this.f + f2, rectF.top + this.g + f2);
        path.arcTo(new RectF(rectF.left + this.f + f2, rectF.top + f2, this.g + rectF.left + this.f, this.g + rectF.top), 180.0f, 90.0f);
        path.close();
    }

    private void b(RectF rectF, Path path, float f2) {
        path.moveTo(this.f + rectF.left + f2, rectF.top + f2);
        path.lineTo(rectF.width() - f2, rectF.top + f2);
        path.lineTo(rectF.right - f2, rectF.bottom - f2);
        path.lineTo(rectF.left + this.f + f2, rectF.bottom - f2);
        float f3 = f2 / 2.0f;
        path.lineTo(rectF.left + this.f + f2, (this.h + this.i) - f3);
        path.lineTo(rectF.left + f2 + f2, this.i + (this.h / 2.0f));
        path.lineTo(rectF.left + this.f + f2, this.i + f3);
        path.lineTo(rectF.left + this.f + f2, rectF.top + f2);
        path.close();
    }

    private void c(RectF rectF, Path path, float f2) {
        path.moveTo(rectF.left + Math.min(this.i, this.g) + f2, rectF.top + this.h + f2);
        float f3 = f2 / 2.0f;
        path.lineTo(rectF.left + this.i + f3, rectF.top + this.h + f2);
        path.lineTo(rectF.left + (this.f / 2.0f) + this.i, rectF.top + f2 + f2);
        path.lineTo(((rectF.left + this.f) + this.i) - f3, rectF.top + this.h + f2);
        path.lineTo((rectF.right - this.g) - f2, rectF.top + this.h + f2);
        path.arcTo(new RectF(rectF.right - this.g, rectF.top + this.h + f2, rectF.right - f2, this.g + rectF.top + this.h), 270.0f, 90.0f);
        path.lineTo(rectF.right - f2, (rectF.bottom - this.g) - f2);
        path.arcTo(new RectF(rectF.right - this.g, rectF.bottom - this.g, rectF.right - f2, rectF.bottom - f2), 0.0f, 90.0f);
        path.lineTo(rectF.left + this.g + f2, rectF.bottom - f2);
        path.arcTo(new RectF(rectF.left + f2, rectF.bottom - this.g, this.g + rectF.left, rectF.bottom - f2), 90.0f, 90.0f);
        path.lineTo(rectF.left + f2, rectF.top + this.h + this.g + f2);
        path.arcTo(new RectF(rectF.left + f2, rectF.top + this.h + f2, this.g + rectF.left, this.g + rectF.top + this.h), 180.0f, 90.0f);
        path.close();
    }

    private void d(RectF rectF, Path path, float f2) {
        path.moveTo(rectF.left + this.i + f2, rectF.top + this.h + f2);
        float f3 = f2 / 2.0f;
        path.lineTo(rectF.left + this.i + f3, rectF.top + this.h + f2);
        path.lineTo(rectF.left + (this.f / 2.0f) + this.i, rectF.top + f2 + f2);
        path.lineTo(((rectF.left + this.f) + this.i) - f3, rectF.top + this.h + f2);
        path.lineTo(rectF.right - f2, rectF.top + this.h + f2);
        path.lineTo(rectF.right - f2, rectF.bottom - f2);
        path.lineTo(rectF.left + f2, rectF.bottom - f2);
        path.lineTo(rectF.left + f2, rectF.top + this.h + f2);
        path.lineTo(rectF.left + this.i + f2, rectF.top + this.h + f2);
        path.close();
    }

    private void e(RectF rectF, Path path, float f2) {
        path.moveTo(rectF.left + this.g + f2, rectF.top + f2);
        path.lineTo(((rectF.width() - this.g) - this.f) - f2, rectF.top + f2);
        path.arcTo(new RectF((rectF.right - this.g) - this.f, rectF.top + f2, (rectF.right - this.f) - f2, this.g + rectF.top), 270.0f, 90.0f);
        float f3 = f2 / 2.0f;
        path.lineTo((rectF.right - this.f) - f2, this.i + f3);
        path.lineTo((rectF.right - f2) - f2, this.i + (this.h / 2.0f));
        path.lineTo((rectF.right - this.f) - f2, (this.i + this.h) - f3);
        path.lineTo((rectF.right - this.f) - f2, (rectF.bottom - this.g) - f2);
        path.arcTo(new RectF((rectF.right - this.g) - this.f, rectF.bottom - this.g, (rectF.right - this.f) - f2, rectF.bottom - f2), 0.0f, 90.0f);
        path.lineTo(rectF.left + this.f + f2, rectF.bottom - f2);
        path.arcTo(new RectF(rectF.left + f2, rectF.bottom - this.g, this.g + rectF.left, rectF.bottom - f2), 90.0f, 90.0f);
        path.arcTo(new RectF(rectF.left + f2, rectF.top + f2, this.g + rectF.left, this.g + rectF.top), 180.0f, 90.0f);
        path.close();
    }

    private void f(RectF rectF, Path path, float f2) {
        path.moveTo(rectF.left + f2, rectF.top + f2);
        path.lineTo((rectF.width() - this.f) - f2, rectF.top + f2);
        float f3 = f2 / 2.0f;
        path.lineTo((rectF.right - this.f) - f2, this.i + f3);
        path.lineTo((rectF.right - f2) - f2, this.i + (this.h / 2.0f));
        path.lineTo((rectF.right - this.f) - f2, (this.i + this.h) - f3);
        path.lineTo((rectF.right - this.f) - f2, rectF.bottom - f2);
        path.lineTo(rectF.left + f2, rectF.bottom - f2);
        path.lineTo(rectF.left + f2, rectF.top + f2);
        path.close();
    }

    private void g(RectF rectF, Path path, float f2) {
        path.moveTo(rectF.left + this.g + f2, rectF.top + f2);
        path.lineTo((rectF.width() - this.g) - f2, rectF.top + f2);
        path.arcTo(new RectF(rectF.right - this.g, rectF.top + f2, rectF.right - f2, this.g + rectF.top), 270.0f, 90.0f);
        path.lineTo(rectF.right - f2, ((rectF.bottom - this.h) - this.g) - f2);
        path.arcTo(new RectF(rectF.right - this.g, (rectF.bottom - this.g) - this.h, rectF.right - f2, (rectF.bottom - this.h) - f2), 0.0f, 90.0f);
        float f3 = f2 / 2.0f;
        path.lineTo(((rectF.left + this.f) + this.i) - f3, (rectF.bottom - this.h) - f2);
        path.lineTo(rectF.left + this.i + (this.f / 2.0f), (rectF.bottom - f2) - f2);
        path.lineTo(rectF.left + this.i + f3, (rectF.bottom - this.h) - f2);
        path.lineTo(rectF.left + Math.min(this.g, this.i) + f2, (rectF.bottom - this.h) - f2);
        path.arcTo(new RectF(rectF.left + f2, (rectF.bottom - this.g) - this.h, this.g + rectF.left, (rectF.bottom - this.h) - f2), 90.0f, 90.0f);
        path.lineTo(rectF.left + f2, rectF.top + this.g + f2);
        path.arcTo(new RectF(rectF.left + f2, rectF.top + f2, this.g + rectF.left, this.g + rectF.top), 180.0f, 90.0f);
        path.close();
    }

    private void h(RectF rectF, Path path, float f2) {
        path.moveTo(rectF.left + f2, rectF.top + f2);
        path.lineTo(rectF.right - f2, rectF.top + f2);
        path.lineTo(rectF.right - f2, (rectF.bottom - this.h) - f2);
        float f3 = f2 / 2.0f;
        path.lineTo(((rectF.left + this.f) + this.i) - f3, (rectF.bottom - this.h) - f2);
        path.lineTo(rectF.left + this.i + (this.f / 2.0f), (rectF.bottom - f2) - f2);
        path.lineTo(rectF.left + this.i + f3, (rectF.bottom - this.h) - f2);
        path.lineTo(rectF.left + this.i + f2, (rectF.bottom - this.h) - f2);
        path.lineTo(rectF.left + f2, (rectF.bottom - this.h) - f2);
        path.lineTo(rectF.left + f2, rectF.top + f2);
        path.close();
    }
}
