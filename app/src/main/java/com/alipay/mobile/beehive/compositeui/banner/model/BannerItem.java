package com.alipay.mobile.beehive.compositeui.banner.model;

import java.util.Map;

public class BannerItem {
    private static final long DEFAULT_LOOP_TIME = 5000;
    private Map<String, Object> extInfo;
    private String id;
    private String imageUrl;
    private int index;
    private boolean isDefault = false;
    private long loopTime = DEFAULT_LOOP_TIME;
    private String url;

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index2) {
        this.index = index2;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl2) {
        this.imageUrl = imageUrl2;
    }

    public long getLoopTime() {
        return this.loopTime;
    }

    public void setLoopTime(long loopTime2) {
        this.loopTime = loopTime2;
    }

    public boolean isDefault() {
        return this.isDefault;
    }

    public void setDefault(boolean isDefault2) {
        this.isDefault = isDefault2;
    }

    public Map<String, Object> getExtInfo() {
        return this.extInfo;
    }

    public void setExtInfo(Map<String, Object> extInfo2) {
        this.extInfo = extInfo2;
    }

    public String toString() {
        return "BannerItem [index=" + this.index + ", id=" + this.id + ", url=" + this.url + ", imageUrl=" + this.imageUrl + ", loopTime=" + this.loopTime + ", isDefault=" + this.isDefault + ", extInfo=" + this.extInfo + "]";
    }
}
