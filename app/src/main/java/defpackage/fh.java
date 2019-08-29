package defpackage;

import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import com.airbnb.lottie.model.content.ShapeTrimPath.Type;
import java.util.List;

/* renamed from: fh reason: default package */
/* compiled from: EllipseContent */
public final class fh implements fn, a {
    private final Path a = new Path();
    private final String b;
    private final ew c;
    private final fu<?, PointF> d;
    private final fu<?, PointF> e;
    private final hm f;
    @Nullable
    private ft g;
    private boolean h;

    public fh(ew ewVar, hx hxVar, hm hmVar) {
        this.b = hmVar.a;
        this.c = ewVar;
        this.d = hmVar.c.a();
        this.e = hmVar.b.a();
        this.f = hmVar;
        hxVar.a(this.d);
        hxVar.a(this.e);
        this.d.a((a) this);
        this.e.a((a) this);
    }

    public final void a(List<fe> list, List<fe> list2) {
        for (int i = 0; i < list.size(); i++) {
            fe feVar = list.get(i);
            if (feVar instanceof ft) {
                ft ftVar = (ft) feVar;
                if (ftVar.a == Type.Simultaneously) {
                    this.g = ftVar;
                    this.g.a(this);
                }
            }
        }
    }

    public final String b() {
        return this.b;
    }

    public final Path e() {
        if (this.h) {
            return this.a;
        }
        this.a.reset();
        PointF pointF = (PointF) this.d.a();
        float f2 = pointF.x / 2.0f;
        float f3 = pointF.y / 2.0f;
        float f4 = f2 * 0.55228f;
        float f5 = 0.55228f * f3;
        this.a.reset();
        if (this.f.d) {
            float f6 = -f3;
            this.a.moveTo(0.0f, f6);
            float f7 = 0.0f - f4;
            float f8 = -f2;
            float f9 = 0.0f - f5;
            this.a.cubicTo(f7, f6, f8, f9, f8, 0.0f);
            float f10 = f5 + 0.0f;
            float f11 = f6;
            this.a.cubicTo(f8, f10, f7, f3, 0.0f, f3);
            float f12 = f4 + 0.0f;
            this.a.cubicTo(f12, f3, f2, f10, f2, 0.0f);
            this.a.cubicTo(f2, f9, f12, f11, 0.0f, f11);
        } else {
            float f13 = -f3;
            this.a.moveTo(0.0f, f13);
            float f14 = f4 + 0.0f;
            float f15 = 0.0f - f5;
            this.a.cubicTo(f14, f13, f2, f15, f2, 0.0f);
            float f16 = f5 + 0.0f;
            this.a.cubicTo(f2, f16, f14, f3, 0.0f, f3);
            float f17 = 0.0f - f4;
            float f18 = -f2;
            this.a.cubicTo(f17, f3, f18, f16, f18, 0.0f);
            float f19 = f13;
            this.a.cubicTo(f18, f15, f17, f19, 0.0f, f19);
        }
        PointF pointF2 = (PointF) this.e.a();
        this.a.offset(pointF2.x, pointF2.y);
        this.a.close();
        ij.a(this.a, this.g);
        this.h = true;
        return this.a;
    }

    public final void a() {
        this.h = false;
        this.c.invalidateSelf();
    }
}
