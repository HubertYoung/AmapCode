package com.autonavi.miniapp.plugin.lbs;

import java.util.HashMap;

public class CloudItem {
    private String createTime;
    private HashMap<String, String> customeField;
    private int describeContents;
    private int distance;
    private LatLonPoint latLonPoint;
    private String poiID;
    private String snippet;
    private String title;
    private String updateTime;

    public int getDescribeContents() {
        return this.describeContents;
    }

    public void setDescribeContents(int i) {
        this.describeContents = i;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public HashMap<String, String> getCustomeField() {
        return this.customeField;
    }

    public void setCustomeField(HashMap<String, String> hashMap) {
        this.customeField = hashMap;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int i) {
        this.distance = i;
    }

    public LatLonPoint getLatLonPoint() {
        return this.latLonPoint;
    }

    public void setLatLonPoint(LatLonPoint latLonPoint2) {
        this.latLonPoint = latLonPoint2;
    }

    public String getPoiID() {
        return this.poiID;
    }

    public void setPoiID(String str) {
        this.poiID = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getSnippet() {
        return this.snippet;
    }

    public void setSnippet(String str) {
        this.snippet = str;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String str) {
        this.updateTime = str;
    }

    public boolean equals(Object obj) {
        if (obj instanceof CloudItem) {
            return this.poiID.equals(((CloudItem) obj).poiID);
        }
        return false;
    }
}
