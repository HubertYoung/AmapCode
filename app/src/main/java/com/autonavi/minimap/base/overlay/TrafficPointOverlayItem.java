package com.autonavi.minimap.base.overlay;

import com.autonavi.common.model.GeoPoint;

public class TrafficPointOverlayItem extends MapPointOverlayItem {
    private String mEventId = null;

    public TrafficPointOverlayItem(GeoPoint geoPoint, int i, String str) {
        super(geoPoint, i);
        this.mEventId = str;
    }

    public String getTrafficEventId() {
        return this.mEventId;
    }
}
