package com.autonavi.miniapp.plugin.lbs;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.io.Serializable;

public class LatLonPoint implements Serializable, Cloneable {
    private static final String TAG = "LatLonPoint";
    private static final long serialVersionUID = 1;
    private double latitude;
    private double longitude;

    public LatLonPoint() {
        this.latitude = 0.0d;
        this.longitude = 0.0d;
    }

    public LatLonPoint(double d, double d2) {
        this.latitude = d;
        this.longitude = d2;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double d) {
        this.latitude = d;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double d) {
        this.longitude = d;
    }

    public LatLonPoint clone() {
        try {
            return (LatLonPoint) super.clone();
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error((String) TAG, "clone exceptin, msg=".concat(String.valueOf(e)));
            return null;
        }
    }
}
