package defpackage;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.annotation.Nullable;

/* renamed from: gj reason: default package */
/* compiled from: TransformKeyframeAnimation */
public final class gj {
    public final fu<PointF, PointF> a;
    public final fu<?, PointF> b;
    public final fu<gw, gw> c;
    public final fu<Float, Float> d;
    public final fu<Integer, Integer> e;
    @Nullable
    public final fu<?, Float> f;
    @Nullable
    public final fu<?, Float> g;
    private final Matrix h = new Matrix();

    public gj(hi hiVar) {
        this.a = hiVar.a.a();
        this.b = hiVar.b.a();
        this.c = hiVar.c.a();
        this.d = hiVar.d.a();
        this.e = hiVar.e.a();
        if (hiVar.f != null) {
            this.f = hiVar.f.a();
        } else {
            this.f = null;
        }
        if (hiVar.g != null) {
            this.g = hiVar.g.a();
        } else {
            this.g = null;
        }
    }

    public final void a(hx hxVar) {
        hxVar.a(this.a);
        hxVar.a(this.b);
        hxVar.a(this.c);
        hxVar.a(this.d);
        hxVar.a(this.e);
        if (this.f != null) {
            hxVar.a(this.f);
        }
        if (this.g != null) {
            hxVar.a(this.g);
        }
    }

    public final void a(a aVar) {
        this.a.a(aVar);
        this.b.a(aVar);
        this.c.a(aVar);
        this.d.a(aVar);
        this.e.a(aVar);
        if (this.f != null) {
            this.f.a(aVar);
        }
        if (this.g != null) {
            this.g.a(aVar);
        }
    }

    public final Matrix a() {
        this.h.reset();
        PointF pointF = (PointF) this.b.a();
        if (!(pointF.x == 0.0f && pointF.y == 0.0f)) {
            this.h.preTranslate(pointF.x, pointF.y);
        }
        float floatValue = ((Float) this.d.a()).floatValue();
        if (floatValue != 0.0f) {
            this.h.preRotate(floatValue);
        }
        gw gwVar = (gw) this.c.a();
        if (!(gwVar.a == 1.0f && gwVar.b == 1.0f)) {
            this.h.preScale(gwVar.a, gwVar.b);
        }
        PointF pointF2 = (PointF) this.a.a();
        if (!(pointF2.x == 0.0f && pointF2.y == 0.0f)) {
            this.h.preTranslate(-pointF2.x, -pointF2.y);
        }
        return this.h;
    }

    public final Matrix a(float f2) {
        PointF pointF = (PointF) this.b.a();
        PointF pointF2 = (PointF) this.a.a();
        gw gwVar = (gw) this.c.a();
        float floatValue = ((Float) this.d.a()).floatValue();
        this.h.reset();
        this.h.preTranslate(pointF.x * f2, pointF.y * f2);
        double d2 = (double) f2;
        this.h.preScale((float) Math.pow((double) gwVar.a, d2), (float) Math.pow((double) gwVar.b, d2));
        this.h.preRotate(floatValue * f2, pointF2.x, pointF2.y);
        return this.h;
    }
}
