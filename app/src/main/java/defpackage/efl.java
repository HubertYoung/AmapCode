package defpackage;

import android.graphics.Rect;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.route.common.view.HelPointOverlay;
import com.autonavi.minimap.route.foot.overlay.RouteFootLineOverlay;
import com.autonavi.minimap.route.run.beans.RunTraceHistory.RunType;
import com.autonavi.minimap.route.run.beans.RunTraceHistory.a;
import com.autonavi.minimap.route.run.overlay.RunTraceOverlay;

/* renamed from: efl reason: default package */
/* compiled from: RunFinishOverlay */
public final class efl {
    private static int p = 19;
    private static int q = 3;
    public RunTraceOverlay a;
    public HelPointOverlay b;
    public RouteFootLineOverlay c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int i;
    public a[] j;
    public int[] k;
    public POI l;
    public POI m;
    public POI n;
    public boolean o;
    private bty r;

    public efl(AbstractBaseMapPage abstractBaseMapPage) {
        this.r = abstractBaseMapPage.getMapManager().getMapView();
        if (this.r != null) {
            this.a = new RunTraceOverlay(this.r);
            this.b = new HelPointOverlay(this.r);
            this.b.setClickable(false);
            this.c = new RouteFootLineOverlay(this.r);
            this.r.e(0.0f);
            this.r.g(0.0f);
            abstractBaseMapPage.getSuspendManager().d().f();
            a();
            this.r.g(1728053247);
            this.r.m(true);
            abstractBaseMapPage.addOverlay(this.a);
            abstractBaseMapPage.addOverlay(this.b);
            abstractBaseMapPage.addOverlay(this.c);
        }
    }

    public final void a(RunType runType) {
        if (runType == RunType.RUN_TYPE) {
            a(this.a.getBound());
            return;
        }
        if (runType == RunType.FOOT_TYPE) {
            a(this.c.getBound());
        }
    }

    public final void a() {
        this.b.clear();
        this.a.clear();
        this.c.clear();
        this.r.m(false);
    }

    private void a(Rect rect) {
        if (rect != null) {
            float b2 = b(rect);
            GeoPoint a2 = a(rect, b2);
            this.r.b(400, b2 == -1.0f ? -9999.0f : b2, (int) this.r.I(), (int) this.r.J(), a2.x, a2.y);
        }
    }

    private float b(Rect rect) {
        float U = this.r.U();
        return Math.min((float) p, Math.max((float) q, Math.min((float) a((double) ((((float) this.r.al()) - ((float) this.i)) * U), (double) rect.width()), (float) a((double) ((((float) this.r.am()) - ((float) this.h)) * U), (double) rect.height()))));
    }

    private static double a(double d2, double d3) {
        return 20.0d - (Math.log(d3 / d2) / Math.log(2.0d));
    }

    private GeoPoint a(Rect rect, float f2) {
        float U = this.r.U();
        float f3 = ((float) (this.g - this.e)) * U;
        double d2 = (double) (19.0f - f2);
        return new GeoPoint(rect.centerX() + ((int) (((double) (((float) (this.f - this.d)) * U)) * Math.pow(2.0d, d2))), rect.centerY() + ((int) (((double) f3) * Math.pow(2.0d, d2))));
    }
}
