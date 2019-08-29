package defpackage;

import android.graphics.Rect;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapViewUtil;

/* renamed from: eak reason: default package */
/* compiled from: PreviewTools */
public final class eak {
    private static int b = 400;
    private static int c;
    public a a;

    /* renamed from: eak$a */
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
        public int k = -9999;
        public float l = 0.0f;
        bty m;
        Rect n;

        public final a a(Rect rect, int i2, int i3, int i4, int i5) {
            this.n = rect;
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
            this.m = bty;
            this.g = i4;
            this.h = i5;
            this.a = i2;
            this.b = i3;
            this.i = 0;
            return this;
        }

        public final eak a() {
            return new eak(this);
        }
    }

    public eak(a aVar) {
        this.a = aVar;
    }

    public static void a(a aVar) {
        float f;
        if (aVar.n != null) {
            aVar.m.b(aVar.g, aVar.h);
            if (aVar.l == 0.0f) {
                f = aVar.m.a(aVar.n.left, aVar.n.top, aVar.n.right, aVar.n.bottom, aVar.a - (aVar.c + aVar.e), aVar.b - (aVar.d + aVar.f));
            } else {
                f = aVar.l;
            }
            GeoPoint a2 = cfg.a(aVar.m, new GeoPoint(aVar.n.centerX(), aVar.n.centerY()), aVar.g - (((aVar.a + aVar.c) - aVar.e) / 2), aVar.h - (((aVar.b + aVar.d) - aVar.f) / 2), f, aVar.i);
            if (a2 != null) {
                StringBuilder sb = new StringBuilder("PreviewTools, _preview#centerP.x = ");
                sb.append(a2.x);
                sb.append(", centerP.y = ");
                sb.append(a2.y);
                sb.append(", targetLevel = ");
                sb.append(f);
                AMapLog.e("Aragorn", sb.toString());
            }
            aVar.m.a(b, f, aVar.j, aVar.k, a2 == null ? -9999 : a2.x, a2 == null ? -9999 : a2.y);
            MapViewUtil.updateLockMapAngleState(aVar.m, (float) c);
        }
    }
}
