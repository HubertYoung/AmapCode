package defpackage;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import com.airbnb.lottie.model.content.ShapeTrimPath.Type;
import java.util.List;

/* renamed from: fp reason: default package */
/* compiled from: RectangleContent */
public final class fp implements fn, a {
    private final Path a = new Path();
    private final RectF b = new RectF();
    private final String c;
    private final ew d;
    private final fu<?, PointF> e;
    private final fu<?, PointF> f;
    private final fu<?, Float> g;
    @Nullable
    private ft h;
    private boolean i;

    public fp(ew ewVar, hx hxVar, hr hrVar) {
        this.c = hrVar.a;
        this.d = ewVar;
        this.e = hrVar.b.a();
        this.f = hrVar.c.a();
        this.g = hrVar.d.a();
        hxVar.a(this.e);
        hxVar.a(this.f);
        hxVar.a(this.g);
        this.e.a((a) this);
        this.f.a((a) this);
        this.g.a((a) this);
    }

    public final String b() {
        return this.c;
    }

    public final void a(List<fe> list, List<fe> list2) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            fe feVar = list.get(i2);
            if (feVar instanceof ft) {
                ft ftVar = (ft) feVar;
                if (ftVar.a == Type.Simultaneously) {
                    this.h = ftVar;
                    this.h.a(this);
                }
            }
        }
    }

    public final Path e() {
        if (this.i) {
            return this.a;
        }
        this.a.reset();
        PointF pointF = (PointF) this.f.a();
        float f2 = pointF.x / 2.0f;
        float f3 = pointF.y / 2.0f;
        float floatValue = this.g == null ? 0.0f : ((Float) this.g.a()).floatValue();
        float min = Math.min(f2, f3);
        if (floatValue > min) {
            floatValue = min;
        }
        PointF pointF2 = (PointF) this.e.a();
        this.a.moveTo(pointF2.x + f2, (pointF2.y - f3) + floatValue);
        this.a.lineTo(pointF2.x + f2, (pointF2.y + f3) - floatValue);
        int i2 = (floatValue > 0.0f ? 1 : (floatValue == 0.0f ? 0 : -1));
        if (i2 > 0) {
            float f4 = floatValue * 2.0f;
            this.b.set((pointF2.x + f2) - f4, (pointF2.y + f3) - f4, pointF2.x + f2, pointF2.y + f3);
            this.a.arcTo(this.b, 0.0f, 90.0f, false);
        }
        this.a.lineTo((pointF2.x - f2) + floatValue, pointF2.y + f3);
        if (i2 > 0) {
            float f5 = floatValue * 2.0f;
            this.b.set(pointF2.x - f2, (pointF2.y + f3) - f5, (pointF2.x - f2) + f5, pointF2.y + f3);
            this.a.arcTo(this.b, 90.0f, 90.0f, false);
        }
        this.a.lineTo(pointF2.x - f2, (pointF2.y - f3) + floatValue);
        if (i2 > 0) {
            float f6 = floatValue * 2.0f;
            this.b.set(pointF2.x - f2, pointF2.y - f3, (pointF2.x - f2) + f6, (pointF2.y - f3) + f6);
            this.a.arcTo(this.b, 180.0f, 90.0f, false);
        }
        this.a.lineTo((pointF2.x + f2) - floatValue, pointF2.y - f3);
        if (i2 > 0) {
            float f7 = floatValue * 2.0f;
            this.b.set((pointF2.x + f2) - f7, pointF2.y - f3, pointF2.x + f2, (pointF2.y - f3) + f7);
            this.a.arcTo(this.b, 270.0f, 90.0f, false);
        }
        this.a.close();
        ij.a(this.a, this.h);
        this.i = true;
        return this.a;
    }

    public final void a() {
        this.i = false;
        this.d.invalidateSelf();
    }
}
