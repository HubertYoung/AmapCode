package com.autonavi.jni.ae.route.route;

import com.autonavi.jni.ae.route.model.FormWay;
import com.autonavi.jni.ae.route.model.LinkStatus;
import com.autonavi.jni.ae.route.model.LongSolidLane;

public class RouteLink {
    private int mLinkIndex;
    private long mRoutePtr;
    private int mSegmentIndex;

    private native int nativeGetLength();

    private native double[] nativeGetLink3DCoor();

    private native int nativeGetLink3DCoorNum();

    private native int nativeGetLinkCityCode();

    private native double[] nativeGetLinkCoor();

    private native int nativeGetLinkCoorNum();

    private native int nativeGetLinkCoorOffset();

    private native FormWay nativeGetLinkFormWay();

    private native LongSolidLane nativeGetLinkLongSolidLane();

    private native int nativeGetLinkOwnership();

    private native int nativeGetLinkRoadClass();

    private native String nativeGetLinkRoadName();

    private native LinkStatus nativeGetLinkTrafficStatus();

    private native int nativeGetLinkType();

    private native int nativeGetLinkUrID();

    private native int nativeGetTPId();

    private native int nativeGetTileId();

    private native long nativeGetTopoId64();

    private native boolean nativeHaveTrafficLights();

    private native boolean nativeIsToll();

    private native boolean naviteIsRestricting();

    public RouteLink(long j, int i, int i2) {
        this.mRoutePtr = j;
        this.mSegmentIndex = i;
        this.mLinkIndex = i2;
    }

    public String getLinkRoadName() {
        return nativeGetLinkRoadName();
    }

    public LinkStatus getLinkTrafficStatus() {
        return nativeGetLinkTrafficStatus();
    }

    public LongSolidLane getLinkLongSolidLane() {
        return nativeGetLinkLongSolidLane();
    }

    public double[] getLinkCoor() {
        return nativeGetLinkCoor();
    }

    public double[] getLink3DCoor() {
        return nativeGetLink3DCoor();
    }

    public int getLinkCoorOffset() {
        return nativeGetLinkCoorOffset();
    }

    public int getLinkCoorNum() {
        return nativeGetLinkCoorNum();
    }

    public int getLink3DCoorNum() {
        return nativeGetLink3DCoorNum();
    }

    public int getLinkOwnership() {
        return nativeGetLinkOwnership();
    }

    public int getLinkRoadClass() {
        return nativeGetLinkRoadClass();
    }

    public int getLinkType() {
        return nativeGetLinkType();
    }

    public int getLinkCityCode() {
        return nativeGetLinkCityCode();
    }

    public int getLinkUrID() {
        return nativeGetLinkUrID();
    }

    public boolean haveTrafficLights() {
        return nativeHaveTrafficLights();
    }

    public long getTopoId64() {
        return nativeGetTopoId64();
    }

    public boolean isRestricting() {
        return naviteIsRestricting();
    }

    public FormWay getLinkFormWay() {
        return nativeGetLinkFormWay();
    }

    public boolean isToll() {
        return nativeIsToll();
    }

    public int getLength() {
        return nativeGetLength();
    }

    public int getTileId() {
        return nativeGetTileId();
    }

    public int getTPId() {
        return nativeGetTPId();
    }
}
