package com.autonavi.minimap.base.overlay;

import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;

public class POIOverlayItem extends PointOverlayItem {
    private POI mPOI = null;

    public POIOverlayItem(GeoPoint geoPoint) {
        super(geoPoint);
        this.mPOI = POIFactory.createPOI("", geoPoint);
    }

    public POIOverlayItem(POI poi) {
        super(poi.getPoint());
        this.mPOI = poi;
    }

    public POI getPOI() {
        return this.mPOI;
    }
}
