package com.alipay.mobile.map.model.geocode;

import java.io.Serializable;

public class CodeResult implements Serializable {
    private static final long serialVersionUID = 1;
    private String building;
    private String city;
    @Deprecated
    private String cityCode;

    public String getBuilding() {
        return this.building;
    }

    public void setBuilding(String str) {
        this.building = str;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String str) {
        this.city = str;
    }

    @Deprecated
    public String getCityCode() {
        return this.cityCode;
    }

    @Deprecated
    public void setCityCode(String str) {
        this.cityCode = str;
    }
}
