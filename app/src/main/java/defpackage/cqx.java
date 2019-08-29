package defpackage;

import android.graphics.Rect;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapViewUtil;

/* renamed from: cqx reason: default package */
/* compiled from: PreviewTools */
public final class cqx {
    private static int a = 200;
    private static int b;
    private a c;

    /* renamed from: cqx$a */
    /* compiled from: PreviewTools */
    public static class a {
        int a;
        int b;
        int c;
        int d;
        int e;
        int f;
        int g;
        int h;
        int i;
        public int j = -9999;
        bty k;
        Rect l;

        public final a a(Rect rect, int i2, int i3, int i4, int i5) {
            this.l = rect;
            if (rect == null) {
                throw new NullPointerException("OperateLineStation-hound should not be null");
            }
            this.c = i2;
            this.d = i3;
            this.e = i4;
            this.f = i5;
            return this;
        }

        public final a a(bty bty, int i2, int i3, int i4, int i5) {
            this.k = bty;
            this.g = i4;
            this.h = i5;
            this.a = i2;
            this.b = i3;
            this.i = 0;
            return this;
        }

        public final cqx a() {
            return new cqx(this);
        }
    }

    public cqx(a aVar) {
        this.c = aVar;
    }

    public final void a() {
        a aVar = this.c;
        if (aVar.l != null) {
            aVar.k.b(aVar.g, aVar.h);
            float a2 = aVar.k.a(aVar.l.left, aVar.l.top, aVar.l.right, aVar.l.bottom, aVar.a - (aVar.c + aVar.e), aVar.b - (aVar.d + aVar.f));
            GeoPoint a3 = cfg.a(aVar.k, new GeoPoint(aVar.l.centerX(), aVar.l.centerY()), aVar.g - (((aVar.a + aVar.c) - aVar.e) / 2), aVar.h - (((aVar.b + aVar.d) - aVar.f) / 2), a2, aVar.i);
            aVar.k.a(a, a2, aVar.j, b, a3 == null ? -9999 : a3.x, a3 == null ? -9999 : a3.y);
            MapViewUtil.updateLockMapAngleState(aVar.k, (float) b);
        }
    }
}
