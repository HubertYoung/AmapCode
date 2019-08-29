package com.amap.bundle.datamodel.poi;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;

public class POIFactory {
    public static POI createPOI(String str, GeoPoint geoPoint) {
        POIBase pOIBase = new POIBase();
        pOIBase.setPoint(geoPoint);
        pOIBase.setName(str);
        return pOIBase;
    }

    public static <T extends POI> T createPOI(Class<T> cls) {
        POIBase pOIBase = new POIBase();
        pOIBase.setPoint(new GeoPoint());
        return pOIBase.as(cls);
    }

    public static POI createPOI() {
        POIBase pOIBase = new POIBase();
        pOIBase.setPoint(new GeoPoint());
        return pOIBase;
    }
}
