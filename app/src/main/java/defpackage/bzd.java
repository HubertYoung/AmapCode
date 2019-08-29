package defpackage;

import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.MapPointOverlay;
import com.autonavi.minimap.base.overlay.MapPointOverlayItem;

/* renamed from: bzd reason: default package */
/* compiled from: SearchPoiDetailOverlayManager */
public final class bzd {
    public MapPointOverlay a = new MapPointOverlay(this.g.getMapManager().getMapView(), R.drawable.b_poi_hl);
    public MapPointOverlayItem b;
    public bbp c;
    public POI d;
    bql e;
    private MapPointOverlayItem f;
    private MapBasePage g;

    public bzd(bql bql) {
        this.e = bql;
        this.g = bql.a();
        this.g.getMapView().F().b((BaseMapOverlay) this.a);
    }

    public final void a() {
        this.c = new bbp(this.g.getMapManager().getMapView());
        this.g.getMapManager().getMapView().F().b((BaseMapOverlay) this.c.a);
        this.g.getMapManager().getMapView().F().b((BaseMapOverlay) this.c.b);
    }

    public final void a(final MapPointOverlayItem mapPointOverlayItem) {
        this.d = null;
        this.a.setOverlayOnTop(true);
        this.a.setItem(mapPointOverlayItem);
        this.b = mapPointOverlayItem;
        this.f = mapPointOverlayItem;
        this.g.getMapManager().getMapView().X();
        this.g.getMapManager().getMapView().a((Runnable) new Runnable() {
            public final void run() {
                bzd.this.e.a(mapPointOverlayItem.getGeoPoint());
            }
        }, 100);
    }

    public final void a(POI poi) {
        this.d = poi;
        this.c.a(poi, false);
    }

    public final void b() {
        this.a.clear();
        this.a.setOverlayOnTop(false);
        this.c.a();
        this.d = null;
        this.b = null;
    }

    public final void a(boolean z) {
        if (this.c != null) {
            this.c.a(z);
        }
    }
}
