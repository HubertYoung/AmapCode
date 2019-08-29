package com.alipay.mobile.map.model.geocode;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.map.model.LatLonPoint;
import java.io.Serializable;

public class StreetNumber implements Serializable, Cloneable {
    private static final String TAG = "StreetNumber";
    private static final long serialVersionUID = 4190326962112365979L;
    private String direction;
    private float distance;
    private LatLonPoint latLonPoint;
    private String number;
    private String street;

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

    public LatLonPoint getLatLonPoint() {
        return this.latLonPoint;
    }

    public void setLatLonPoint(LatLonPoint latLonPoint2) {
        this.latLonPoint = latLonPoint2;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String str) {
        this.number = str;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String str) {
        this.street = str;
    }

    public StreetNumber clone() {
        StreetNumber streetNumber;
        Object e;
        LatLonPoint latLonPoint2 = null;
        try {
            streetNumber = (StreetNumber) super.clone();
            try {
                if (getLatLonPoint() != null) {
                    latLonPoint2 = getLatLonPoint().clone();
                }
                streetNumber.setLatLonPoint(latLonPoint2);
            } catch (Exception e2) {
                e = e2;
                LoggerFactory.getTraceLogger().error((String) TAG, "clone exceptin, msg=".concat(String.valueOf(e)));
                return streetNumber;
            }
        } catch (Exception e3) {
            Object obj = e3;
            streetNumber = null;
            e = obj;
            LoggerFactory.getTraceLogger().error((String) TAG, "clone exceptin, msg=".concat(String.valueOf(e)));
            return streetNumber;
        }
        return streetNumber;
    }
}
