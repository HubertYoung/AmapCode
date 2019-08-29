package com.autonavi.miniapp.plugin.map.property;

import com.autonavi.common.model.GeoPoint;
import java.io.Serializable;

public class Point implements Serializable {
    public GeoPoint latLng;
    public double latitude;
    public double longitude;

    /* access modifiers changed from: 0000 */
    public GeoPoint getLatLng() {
        if (this.latLng == null) {
            this.latLng = new GeoPoint(this.latitude, this.longitude);
        }
        return this.latLng;
    }
}
