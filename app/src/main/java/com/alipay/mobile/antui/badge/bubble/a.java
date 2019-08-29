package com.alipay.mobile.antui.badge.bubble;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.internal.view.SupportMenu;

/* compiled from: AUBubbleDrawable */
final class a extends Drawable {
    private int a;
    private int b;
    private int c = 1;
    private int d = SupportMenu.CATEGORY_MASK;
    private float e;
    private float f;
    private Paint g = new Paint(1);
    private Path h = new Path();
    private Path i = new Path();
    private RectF j = new RectF();

    public a() {
        this.g.setColor(this.d);
        this.g.setStyle(Style.FILL);
        this.g.setStrokeCap(Cap.ROUND);
        this.g.setStrokeJoin(Join.ROUND);
    }

    public final void draw(@NonNull Canvas canvas) {
        this.f = Math.min((float) this.a, this.e) / 2.0f;
        switch (this.c) {
            case 0:
                b(canvas);
                return;
            case 1:
                a(canvas);
                return;
            case 2:
                c(canvas);
                return;
            default:
                a(canvas);
                return;
        }
    }

    private void a(Canvas canvas) {
        canvas.save();
        canvas.scale(-1.0f, 1.0f, (float) (this.a / 2), (float) (this.b / 2));
        b(canvas);
        canvas.restore();
    }

    private void b(Canvas canvas) {
        b();
        c();
        this.g.setStyle(Style.FILL);
        this.g.setStrokeWidth(0.0f);
        canvas.drawPath(this.h, this.g);
        this.g.setStyle(Style.FILL);
        canvas.drawPath(this.i, this.g);
    }

    private void c(Canvas canvas) {
        b();
        d();
        this.g.setStyle(Style.FILL);
        this.g.setStrokeWidth(0.0f);
        canvas.drawPath(this.h, this.g);
        canvas.drawPath(this.i, this.g);
    }

    private void b() {
        this.h.rewind();
        this.h.moveTo(this.f, 0.0f);
        this.h.lineTo(((float) this.a) - this.f, 0.0f);
        this.j.set(((float) this.a) - (this.f * 2.0f), 0.0f, (float) this.a, this.f * 2.0f);
        this.h.arcTo(this.j, -90.0f, 90.0f, false);
        this.h.lineTo((float) this.a, this.e - this.f);
        this.j.set(((float) this.a) - (this.f * 2.0f), this.e - (this.f * 2.0f), (float) this.a, this.e);
        this.h.arcTo(this.j, 0.0f, 90.0f, false);
        this.h.lineTo(this.f, this.e);
        this.j.set(0.0f, this.e - (this.f * 2.0f), this.f * 2.0f, this.e);
        this.h.arcTo(this.j, 90.0f, 90.0f, false);
        this.h.lineTo(0.0f, this.f);
        this.j.set(0.0f, 0.0f, this.f * 2.0f, this.f * 2.0f);
        this.h.arcTo(this.j, -180.0f, 90.0f, false);
    }

    private void c() {
        this.i.rewind();
        this.i.moveTo(((float) this.a) - (this.f / 2.0f), this.e - (this.f / 2.0f));
        this.i.quadTo((float) (((double) this.a) - (((double) this.f) / 2.2d)), this.e, ((float) this.a) - (this.f / 4.0f), (float) this.b);
        this.i.lineTo((((float) this.a) - this.f) - (this.f / 8.0f), this.e - (this.f / 20.0f));
        this.i.close();
    }

    private void d() {
        this.i.rewind();
        this.i.moveTo(((float) (this.a / 2)) - (((float) this.b) - this.e), this.e - 2.0f);
        this.i.lineTo((float) (this.a / 2), (float) this.b);
        this.i.lineTo(((float) (this.a / 2)) + (((float) this.b) - this.e), this.e - 2.0f);
        this.i.close();
    }

    public final void setAlpha(int alpha) {
        this.g.setAlpha(alpha);
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.g.setColorFilter(colorFilter);
    }

    public final int getOpacity() {
        return -3;
    }

    /* access modifiers changed from: 0000 */
    public final void a(int width) {
        this.a = width;
    }

    /* access modifiers changed from: 0000 */
    public final void b(int height) {
        this.b = height;
        this.e = this.c == 2 ? ((float) this.b) - (((float) this.b) / 6.6f) : (float) (this.b - (this.b / 14));
    }

    /* access modifiers changed from: 0000 */
    public final void c(int position) {
        this.c = position;
        invalidateSelf();
    }

    /* access modifiers changed from: 0000 */
    public final void d(int color) {
        this.d = color;
        this.g.setColor(this.d);
        invalidateSelf();
    }

    /* access modifiers changed from: 0000 */
    public final float a() {
        return this.e;
    }
}
