package com.alipay.mobile.map.model;

public class SearchPoiRequest {
    private float accuracy;
    private boolean byfoursquare = false;
    private String city = "";
    private String keywords = "";
    private LatLonPoint latlng;
    private int pagenum = 0;
    private int pagesize = 20;
    private int radius = 5000;
    private String types;

    public String getTypes() {
        return this.types;
    }

    public void setTypes(String str) {
        this.types = str;
    }

    public float getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(float f) {
        this.accuracy = f;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String str) {
        this.city = str;
    }

    public LatLonPoint getLatlng() {
        return this.latlng;
    }

    public void setLatlng(LatLonPoint latLonPoint) {
        this.latlng = latLonPoint;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String str) {
        this.keywords = str;
    }

    public int getPagesize() {
        return this.pagesize;
    }

    public void setPagesize(int i) {
        this.pagesize = i;
    }

    public int getPagenum() {
        return this.pagenum;
    }

    public void setPagenum(int i) {
        this.pagenum = i;
    }

    public int getRadius() {
        return this.radius;
    }

    public void setRadius(int i) {
        this.radius = i;
    }

    public boolean isByfoursquare() {
        return this.byfoursquare;
    }

    public void setByfoursquare(boolean z) {
        this.byfoursquare = z;
    }
}
