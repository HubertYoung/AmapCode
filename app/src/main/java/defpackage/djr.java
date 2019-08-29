package defpackage;

import android.content.Context;
import android.graphics.Rect;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;

/* renamed from: djr reason: default package */
/* compiled from: RouteCarResultMapControl */
public final class djr {
    private static int f = 19;
    private static int g = 3;
    public int a = 0;
    /* access modifiers changed from: private */
    public bty b;
    private Context c;
    private cdz d;
    private a e = null;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private GeoPoint n;
    private float o = 17.0f;
    private boolean p = false;
    private boolean q = true;
    private int r = 0;

    /* renamed from: djr$a */
    /* compiled from: RouteCarResultMapControl */
    public class a {
        public float a = -1.0f;
        public GeoPoint b = null;
        public int c = 0;
        public int d = 0;

        public a() {
        }

        public final void a() {
            djr.this.b.a(500, this.a == -1.0f ? -9999.0f : this.a, this.c, this.d, this.b == null ? -9999 : this.b.x, this.b == null ? -9999 : this.b.y);
        }
    }

    public djr(bty bty, cdz cdz) {
        this.b = bty;
        this.c = AMapAppGlobal.getApplication();
        this.d = cdz;
        a(100, (int) MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_);
    }

    public final void a(int i2, int i3) {
        this.j = 40;
        this.k = i2;
        this.l = 40;
        this.m = i3;
        this.h = agn.a(this.c, (float) (this.k + this.m));
        this.i = agn.a(this.c, (float) (this.j + this.l));
    }

    private void a() {
        if (this.d != null) {
            this.d.f();
        }
    }

    public final void a(Rect rect) {
        if (this.a == 2) {
            this.a = 0;
        } else {
            if (this.a == 1) {
                this.a = 0;
            }
            if (rect != null) {
                a();
                int w = this.b.w();
                float b2 = b(rect);
                GeoPoint a2 = a(rect, b2);
                this.n = a2;
                this.o = b2;
                float f2 = (float) w;
                int i2 = (f2 > b2 ? 1 : (f2 == b2 ? 0 : -1));
                if (i2 > 0) {
                    a aVar = new a();
                    this.e = aVar;
                    aVar.a = b2;
                    aVar.b = a2.clone();
                } else if (i2 == 0) {
                    a aVar2 = new a();
                    this.e = aVar2;
                    aVar2.b = a2.clone();
                } else if (f2 < b2) {
                    a aVar3 = new a();
                    this.e = aVar3;
                    aVar3.b = a2.clone();
                    aVar3.a = b2;
                }
            } else {
                return;
            }
        }
        try {
            a aVar4 = this.e;
            if (aVar4 != null) {
                if (aVar4.b != null) {
                    this.b.a(aVar4.b.x, aVar4.b.y);
                }
                if (aVar4.a != -1.0f) {
                    this.b.d(aVar4.a);
                }
                aVar4.a();
            }
        } catch (Exception e2) {
            kf.a((Throwable) e2);
        }
    }

    private GeoPoint a(Rect rect, float f2) {
        float U = this.b.U();
        float a2 = ((float) agn.a(this.c, (float) (this.m - this.k))) * U;
        double d2 = (double) (19.0f - f2);
        return new GeoPoint(rect.centerX() + ((int) (((double) (((float) agn.a(this.c, (float) (this.l - this.j))) * U)) * Math.pow(2.0d, d2))), rect.centerY() + ((int) (((double) a2) * Math.pow(2.0d, d2))));
    }

    private float b(Rect rect) {
        float U = this.b.U();
        float am = (float) this.b.am();
        float al = (float) this.b.al();
        if (this.p && ((this.q && al > am) || (!this.q && al < am))) {
            float f2 = al - ((float) this.r);
            al = am + ((float) this.r);
            am = f2;
        }
        return Math.min((float) f, Math.max((float) g, Math.min((float) a((double) ((al - ((float) this.i)) * U), (double) rect.width()), (float) a((double) ((am - ((float) this.h)) * U), (double) rect.height()))));
    }

    private static double a(double d2, double d3) {
        return 20.0d - (Math.log(d3 / d2) / Math.log(2.0d));
    }
}
