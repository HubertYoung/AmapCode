package defpackage;

import android.graphics.Rect;
import com.amap.bundle.drivecommon.overlay.DriveBaseBoardPointItem;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;

/* renamed from: dhq reason: default package */
/* compiled from: RouteCarResultSearchPointOverlayItem */
public final class dhq extends DriveBaseBoardPointItem {
    public String g = "";
    private POI h;

    public dhq(POI poi) {
        super(poi.getPoint());
        this.h = poi;
    }

    public dhq(POI poi, GeoPoint geoPoint) {
        super(geoPoint);
        this.h = poi;
    }

    public final Rect[] a() {
        return new Rect[]{b()};
    }
}
