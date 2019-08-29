package defpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.route.health.HelLineOverlay;
import com.autonavi.jni.route.health.PathLineParser;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.route.common.view.HelPointOverlay;
import com.autonavi.minimap.route.ride.overlay.HelRideNaviOverlay;

/* renamed from: eek reason: default package */
/* compiled from: RouteHelRideOverlay */
public final class eek {
    public HelRideNaviOverlay a = null;
    public HelLineOverlay b = null;
    private HelLineOverlay c = null;
    private HelPointOverlay d = null;
    private HelPointOverlay e;
    private Context f;
    private int g = 0;
    private bty h;

    public eek(AbstractBaseMapPage abstractBaseMapPage) {
        this.h = abstractBaseMapPage.getMapManager().getMapView();
        if (this.h != null) {
            this.a = new HelRideNaviOverlay(this.h);
            abstractBaseMapPage.addOverlay(this.a);
            this.c = new HelLineOverlay(this.h);
            abstractBaseMapPage.addOverlay(this.c);
            this.b = new HelLineOverlay(this.h);
            abstractBaseMapPage.addOverlay(this.b);
            this.d = new HelPointOverlay(this.h);
            abstractBaseMapPage.addOverlay(this.d);
            this.e = new HelPointOverlay(this.h);
            abstractBaseMapPage.addOverlay(this.e);
            this.f = abstractBaseMapPage.getContext();
        }
    }

    public final void a(boolean z) {
        if (z) {
            this.a.clear();
        }
        this.b.clear();
        this.c.clear();
        this.d.clear();
        this.g = 0;
        this.e.clear();
    }

    public final void a(GeoPoint geoPoint, int i) {
        PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
        new MapViewLayoutParams(-2, -2, geoPoint, 3).mode = 0;
        View inflate = ((LayoutInflater) this.f.getSystemService("layout_inflater")).inflate(R.layout.route_run_milestone_view, null);
        StringBuilder sb = new StringBuilder();
        sb.append(i / 1000);
        ((TextView) inflate.findViewById(R.id.stone_miles)).setText(sb.toString());
        pointOverlayItem.mDefaultMarker = this.d.createMarker(this.g, inflate, 4, 0.0f, 0.0f, false);
        this.d.addItem(pointOverlayItem);
        this.g++;
    }

    public final void b(GeoPoint geoPoint, int i) {
        this.a.drawNaviLine_v2(geoPoint, i);
    }

    public final void a(GeoPoint geoPoint, int i, int i2) {
        this.a.drawNaviLine_v3(geoPoint, i, i2);
    }

    public final void a(int i, int i2, int i3) {
        this.a.updateCarPosition(i, i2, i3);
    }

    public final void a(final PathLineParser pathLineParser) {
        if (this.h != null) {
            this.h.c((Runnable) new Runnable() {
                public final void run() {
                    eek.this.b.clear();
                    eek.this.b.drawPathSegments(pathLineParser);
                }
            });
        }
    }

    public final void b(PathLineParser pathLineParser) {
        this.c.clear();
        this.c.drawPathSegments(pathLineParser);
    }

    public final void a(GeoPoint geoPoint) {
        if (geoPoint != null) {
            PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
            pointOverlayItem.mDefaultMarker = this.e.createMarker(R.drawable.run_point_start, 4);
            this.e.addItem(pointOverlayItem);
        }
    }
}
