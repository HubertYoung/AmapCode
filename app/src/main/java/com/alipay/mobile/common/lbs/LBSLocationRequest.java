package com.alipay.mobile.common.lbs;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LBSLocationRequest {
    private String bizType;
    private long cacheTimeInterval = TimeUnit.SECONDS.toMillis(30);
    private long callbackInterval;
    private Map<String, Object> extraInfo;
    private boolean isHighAccuracy = false;
    private boolean isOnceLocation = true;
    private LBSLocation location;
    private boolean needAddress = true;
    private boolean needSpeed = false;
    private int reGeoLevel = 0;
    private long timeOut = TimeUnit.SECONDS.toMillis(31);

    public boolean isNeedSpeed() {
        return this.needSpeed;
    }

    public void setNeedSpeed(boolean z) {
        this.needSpeed = z;
    }

    public String getBizType() {
        return this.bizType;
    }

    public void setBizType(String str) {
        this.bizType = str;
    }

    public long getCacheTimeInterval() {
        return this.cacheTimeInterval;
    }

    public void setCacheTimeInterval(long j) {
        this.cacheTimeInterval = j;
    }

    public long getTimeOut() {
        return this.timeOut;
    }

    public void setTimeOut(long j) {
        this.timeOut = j;
    }

    public int getReGeoLevel() {
        return this.reGeoLevel;
    }

    public void setReGeoLevel(int i) {
        this.reGeoLevel = i;
    }

    public LBSLocation getLocation() {
        return this.location;
    }

    public void setLocation(LBSLocation lBSLocation) {
        this.location = lBSLocation;
    }

    public boolean isHighAccuracy() {
        return this.isHighAccuracy;
    }

    public void setIsHighAccuracy(boolean z) {
        this.isHighAccuracy = z;
    }

    public boolean isOnceLocation() {
        return this.isOnceLocation;
    }

    public void setOnceLocation(boolean z) {
        this.isOnceLocation = z;
    }

    public long getCallbackInterval() {
        return this.callbackInterval;
    }

    public void setCallbackInterval(long j) {
        this.callbackInterval = j;
    }

    public boolean isNeedAddress() {
        return this.needAddress;
    }

    public void setNeedAddress(boolean z) {
        this.needAddress = z;
    }

    public Map<String, Object> getExtraInfo() {
        return this.extraInfo;
    }

    public void setExtraInfo(Map<String, Object> map) {
        this.extraInfo = map;
    }
}
