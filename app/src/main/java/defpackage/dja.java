package defpackage;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;

/* renamed from: dja reason: default package */
/* compiled from: StickersPointOverlayItem */
public final class dja extends PointOverlayItem {
    public int a = -1;
    private int b;

    private dja(GeoPoint geoPoint, int i) {
        super(geoPoint);
        this.b = i;
    }

    public static dja a(POI poi) {
        if (poi != null) {
            return new dja(poi.getPoint(), poi.getIconId());
        }
        throw new IllegalArgumentException("poi shouldn't be null in createPointByPoi");
    }

    public static dja a(GeoPoint geoPoint, int i) {
        return new dja(geoPoint, i);
    }

    public final void onPrepareAddItem(PointOverlay pointOverlay) {
        super.onPrepareAddItem(pointOverlay);
        this.mDefaultMarker = pointOverlay.createMarker(this.b, 5);
    }
}
