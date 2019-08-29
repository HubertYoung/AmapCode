package defpackage;

import com.autonavi.bundle.searchcommon.overlay.SearchLineOverlay;
import com.autonavi.bundle.searchcommon.overlay.SearchPolygonOverlay;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import java.util.ArrayList;

/* renamed from: bbp reason: default package */
/* compiled from: SearchPolygonManager */
public final class bbp {
    public SearchLineOverlay a;
    public SearchPolygonOverlay b;

    public bbp(bty bty) {
        this.a = new SearchLineOverlay(bty);
        this.b = new SearchPolygonOverlay(bty);
        if (this.a != null) {
            this.a.setClickable(false);
        }
        if (this.b != null) {
            this.b.setClickable(false);
        }
    }

    public final void a(POI poi, boolean z) {
        if (poi != null) {
            a();
            if (this.a != null) {
                this.a.showPolygonLine(poi, z);
            }
            if (this.b != null) {
                this.b.drawPolygon(poi);
            }
        }
    }

    public final void a(ArrayList<ArrayList<GeoPoint>> arrayList) {
        if (arrayList != null) {
            a();
            if (this.a != null) {
                this.a.showPolygonLine(arrayList, true);
            }
            if (this.b != null) {
                this.b.drawPolygon(arrayList);
            }
        }
    }

    public final void a() {
        if (this.b != null) {
            this.b.clear();
        }
        if (this.a != null) {
            this.a.clear();
        }
    }

    public final void a(boolean z) {
        if (this.b != null) {
            POI currentPOI = this.b.getCurrentPOI();
            if (currentPOI == null) {
                this.b.setVisible(z);
            } else if ("citycard".equals(currentPOI.getIndustry())) {
                bty mapView = DoNotUseTool.getMapManager().getMapView();
                if (mapView != null) {
                    this.b.setVisible(mapView.w() < 13);
                }
            } else {
                this.b.setVisible(z);
            }
        }
        if (this.a != null) {
            this.a.setVisible(z);
        }
    }

    public final void b(boolean z) {
        if (this.b != null) {
            this.b.setVisible(z);
        }
    }

    public final void b() {
        if (this.a != null) {
            this.a.setVisible(true);
        }
    }

    public final boolean c() {
        if (this.b != null) {
            return this.b.isVisible();
        }
        return false;
    }

    public final boolean d() {
        if (this.a != null) {
            return this.a.isVisible();
        }
        return false;
    }
}
