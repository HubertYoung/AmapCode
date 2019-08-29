package com.alipay.mobile.map.model.geocode;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.map.model.LatLonPoint;
import java.io.Serializable;

public class RegeocodeRoad implements Serializable, Cloneable {
    private static final String TAG = "RegeocodeRoad";
    private static final long serialVersionUID = -5476825043508110971L;
    private String direction;
    private float distance;
    private String id;
    private LatLonPoint latLngPoint;
    private String name;

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String str) {
        this.direction = str;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float f) {
        this.distance = f;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public LatLonPoint getLatLngPoint() {
        return this.latLngPoint;
    }

    public void setLatLngPoint(LatLonPoint latLonPoint) {
        this.latLngPoint = latLonPoint;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public RegeocodeRoad clone() {
        RegeocodeRoad regeocodeRoad;
        Object e;
        LatLonPoint latLonPoint = null;
        try {
            regeocodeRoad = (RegeocodeRoad) super.clone();
            try {
                if (getLatLngPoint() != null) {
                    latLonPoint = getLatLngPoint().clone();
                }
                regeocodeRoad.setLatLngPoint(latLonPoint);
            } catch (Exception e2) {
                e = e2;
                LoggerFactory.getTraceLogger().error((String) TAG, "clone exceptin, msg=".concat(String.valueOf(e)));
                return regeocodeRoad;
            }
        } catch (Exception e3) {
            Object obj = e3;
            regeocodeRoad = null;
            e = obj;
            LoggerFactory.getTraceLogger().error((String) TAG, "clone exceptin, msg=".concat(String.valueOf(e)));
            return regeocodeRoad;
        }
        return regeocodeRoad;
    }
}
