package com.autonavi.minimap.base.overlay;

import com.autonavi.common.model.GeoPoint;

public class RouteOverlayItem extends LineOverlayItem {
    public long mLineData;

    public RouteOverlayItem(int i, GeoPoint[] geoPointArr, int i2) {
        super(i, geoPointArr, i2);
    }

    public RouteOverlayItem(int i, long j, int i2) {
        super(i, null, i2);
        this.mLineData = j;
    }
}
