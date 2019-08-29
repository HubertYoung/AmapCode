package com.alipay.mobile.map.model.geocode;

import com.alipay.mobile.map.model.LatLonPoint;
import java.io.Serializable;

public class GeocodeResult extends CodeResult implements Serializable {
    private static final long serialVersionUID = 1;
    private String adcode;
    private String district;
    private String formatAddress;
    private LatLonPoint latLonPoint;
    private String level;
    private String neighborhood;
    private String province;
    private String township;

    public String getAdcode() {
        return this.adcode;
    }

    public void setAdcode(String str) {
        this.adcode = str;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String str) {
        this.district = str;
    }

    public String getFormatAddress() {
        return this.formatAddress;
    }

    public void setFormatAddress(String str) {
        this.formatAddress = str;
    }

    public LatLonPoint getLatLonPoint() {
        return this.latLonPoint;
    }

    public void setLatLonPoint(LatLonPoint latLonPoint2) {
        this.latLonPoint = latLonPoint2;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String str) {
        this.level = str;
    }

    public String getNeighborhood() {
        return this.neighborhood;
    }

    public void setNeighborhood(String str) {
        this.neighborhood = str;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String str) {
        this.province = str;
    }

    public String getTownship() {
        return this.township;
    }

    public void setTownship(String str) {
        this.township = str;
    }
}
