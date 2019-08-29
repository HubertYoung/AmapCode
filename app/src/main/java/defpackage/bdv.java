package defpackage;

import android.content.Context;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.core.MapViewUtil;
import com.autonavi.map.core.Real3DManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.IAMapHomePage;
import com.autonavi.map.fragmentcontainer.page.NotMapSkinPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

/* renamed from: bdv reason: default package */
/* compiled from: MainPageFeatureController */
public final class bdv {
    a a;
    private bru b;
    private MapSharePreference c = new MapSharePreference(SharePreferenceName.SharedPreferences);

    /* renamed from: bdv$a */
    /* compiled from: MainPageFeatureController */
    public static abstract class a {
        public abstract AbstractBaseMapPage a();
    }

    public bdv(a aVar) {
        this.a = aVar;
        this.b = new bru(new defpackage.bru.a() {
            public final Context a() {
                return bdv.this.a.a().getContext();
            }

            public final bty b() {
                return bdv.this.a.a().getMapView();
            }

            public final MapManager c() {
                return bdv.this.a.a().getMapManager();
            }
        });
    }

    public final void a() {
        AMapPageUtil.getPageContext();
        this.a.a().getMapView();
        f();
        e();
        brz.a().a(true, false);
        h();
    }

    public final void b() {
        AMapPageUtil.getPageContext();
        this.a.a().getMapView();
        brz.a().a(false, true);
    }

    public final void c() {
        AMapPageUtil.getPageContext();
        Real3DManager.a().c((bro) this.a.a().getMapManager());
    }

    public final void d() {
        bid pageContext = AMapPageUtil.getPageContext();
        boolean isMapHomePage = pageContext instanceof IAMapHomePage ? ((IAMapHomePage) pageContext).isMapHomePage() : false;
        if (!isMapHomePage) {
            Real3DManager.a().d(this.a.a().getMapManager());
        }
        if (!isMapHomePage && (this.a.a() instanceof bek)) {
            ((bek) this.a.a()).k();
        }
    }

    private void e() {
        if (this.a.a().getMapView().k()) {
            Boolean a2 = bru.a();
            boolean s = this.a.a().getMapView().s();
            if (a2 != null && a2.booleanValue() != s) {
                this.b.a(a2.booleanValue(), false, false);
            }
        }
    }

    private void f() {
        g();
        bty mapView = this.a.a().getMapView();
        if (mapView != null) {
            a(mapView.p(true));
            mapView.t(this.c.getBooleanValue("blind_mode_status", false));
        }
    }

    public static void a(AbstractBaseMapPage abstractBaseMapPage) {
        bty mapView = abstractBaseMapPage.getMapView();
        MapManager mapManager = abstractBaseMapPage.getMapManager();
        if (!(abstractBaseMapPage instanceof NotMapSkinPage) && mapView != null && mapManager != null && mapManager.isMapSurfaceCreated()) {
            mapView.a(bin.a.p("101"), mapView.ae(), 0);
        }
    }

    private void g() {
        bty mapView = this.a.a().getMapView();
        if (mapView != null) {
            int l = bim.aa().l((String) "101");
            if (l == 0) {
                if (mapView.p(false) == 0) {
                    mapView.q(true);
                }
            } else if (l == 1) {
                mapView.q(false);
            } else if (l == 2) {
                mapView.q(false);
            }
        }
    }

    private void a(int i) {
        bty mapView = this.a.a().getMapView();
        if (mapView != null) {
            mapView.a(i == 2);
        }
    }

    private void h() {
        bty mapView = this.a.a().getMapView();
        if (mapView != null) {
            MapViewUtil.updateLockMapAngleState(mapView, mapView.J());
        }
    }
}
