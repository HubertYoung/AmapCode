package defpackage;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;

/* renamed from: acp reason: default package */
/* compiled from: PlanThirdMapController */
public final class acp {
    public a a;
    public AbstractBaseMapPage b;
    public int c = -1;

    /* renamed from: acp$a */
    /* compiled from: PlanThirdMapController */
    public static class a {
        public float a;
        public float b;
        public float c;
        public GeoPoint d = new GeoPoint();
        public boolean e = false;
        int f = 0;
        int g = 1;

        protected a() {
        }
    }

    public acp(AbstractBaseMapPage abstractBaseMapPage) {
        this.b = abstractBaseMapPage;
    }

    public final void a() {
        this.a = new a();
        cde suspendManager = this.b.getSuspendManager();
        MapManager mapManager = this.b.getMapManager();
        if (suspendManager != null && mapManager != null && mapManager.getMapView() != null) {
            bty mapView = mapManager.getMapView();
            bty mapView2 = this.b.getMapView();
            this.a.a = mapView.I();
            this.a.b = mapView.v();
            this.a.c = mapView.J();
            this.a.d = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
            this.a.e = this.b.getSuspendManager().d().g();
            suspendManager.d().b(1);
            suspendManager.d().a(false);
            suspendManager.d().f();
            this.a.f = mapView2.j(false);
            this.a.g = mapView2.k(false);
        }
    }
}
