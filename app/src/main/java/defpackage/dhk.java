package defpackage;

import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drivecommon.overlay.DriveBaseBoardPointItem;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;

/* renamed from: dhk reason: default package */
/* compiled from: RouteCarPOIBaseOverlayItem */
public abstract class dhk extends DriveBaseBoardPointItem {
    public POI g = null;

    public dhk(GeoPoint geoPoint) {
        super(geoPoint);
        this.g = POIFactory.createPOI("", geoPoint);
    }

    public dhk(POI poi) {
        super(poi.getPoint());
        this.g = poi;
    }
}
