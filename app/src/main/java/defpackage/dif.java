package defpackage;

import android.graphics.Rect;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapViewUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;

/* renamed from: dif reason: default package */
/* compiled from: PreviewTools */
public final class dif {
    private static int b = 400;
    private static int c;
    public a a;
    private PointOverlay d;

    /* renamed from: dif$a */
    /* compiled from: PreviewTools */
    public static class a {
        int a;
        int b;
        int c;
        int d;
        int e;
        public int f = -9999;
        bty g;
        Rect h;
        private int i;
        private int j;
        private int k;
        private int l;

        public final a a(Rect rect, int i2, int i3, int i4, int i5) {
            this.h = rect;
            if (rect == null) {
                throw new NullPointerException("OperateLineStation-hound should not be null");
            }
            this.i = i2;
            this.j = i3;
            this.k = i4;
            this.l = i5;
            return this;
        }

        public final a a(bty bty, int i2, int i3, int i4, int i5) {
            this.g = bty;
            this.c = i4;
            this.d = i5;
            this.a = i2;
            this.b = i3;
            this.e = 0;
            return this;
        }

        public final dif a() {
            return new dif(this);
        }

        /* access modifiers changed from: 0000 */
        public final int b() {
            return this.c - (((this.a + this.i) - this.k) / 2);
        }

        /* access modifiers changed from: 0000 */
        public final int c() {
            return this.d - (((this.b + this.j) - this.l) / 2);
        }

        public final float d() {
            return this.g.a(this.h.left, this.h.top, this.h.right, this.h.bottom, this.a - (this.i + this.k), this.b - (this.j + this.l));
        }
    }

    public dif(a aVar) {
        this.a = aVar;
    }

    public final void a() {
        a(this.a, false, b);
    }

    public final void b() {
        a(this.a, true, b);
    }

    private static boolean a(float f, float f2) {
        return Math.abs(f - f2) < 0.001f;
    }

    private void a(GeoPoint geoPoint) {
        if (this.d != null) {
            dhl dhl = new dhl(geoPoint);
            dhl.mDefaultMarker = this.d.createMarker(R.drawable.b_poi_real, 5);
            this.d.addItem(dhl);
        }
    }

    private void a(a aVar, boolean z, int i) {
        if (aVar.h != null) {
            aVar.g.a((((float) aVar.c) * 1.0f) / ((float) aVar.a), (((float) aVar.d) * 1.0f) / ((float) aVar.b));
            float d2 = aVar.d();
            GeoPoint a2 = cfg.a(aVar.g, new GeoPoint(aVar.h.centerX(), aVar.h.centerY()), aVar.b(), aVar.c(), d2, aVar.e);
            a(a2);
            if (z) {
                aVar.g.a(i, d2, aVar.f, c, a2 == null ? -9999 : a2.x, a2 == null ? -9999 : a2.y);
                MapViewUtil.updateLockMapAngleState(aVar.g, (float) c);
                return;
            }
            aVar.g.a(a2.x, a2.y);
            if (!a(aVar.g.v(), d2)) {
                aVar.g.T();
                aVar.g.f(d2);
            }
            aVar.g.g((float) c);
        }
    }
}
