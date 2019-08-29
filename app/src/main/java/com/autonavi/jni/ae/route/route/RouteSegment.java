package com.autonavi.jni.ae.route.route;

import com.autonavi.jni.ae.route.model.GeoPoint;

public class RouteSegment {
    private int mIndex;
    private long mRoutePtr;

    private native int nativeGetAssistAction();

    private native int nativeGetLinkCount();

    private native int nativeGetMainAction();

    private native double[] nativeGetSeg3DCoor();

    private native int nativeGetSegChargeLength();

    private native double[] nativeGetSegCoor();

    private native int nativeGetSegLength();

    private native int nativeGetSegTime();

    private native int nativeGetSegTollCost();

    private native String nativeGetSegTollPathName();

    private native GeoPoint nativeGetStartPoint();

    private native int nativeGetTollGateQuickPayType();

    private native int nativeGetTrafficLightNum();

    private native boolean nativeIsRightPassArea();

    public RouteSegment(long j, int i) {
        this.mRoutePtr = j;
        this.mIndex = i;
    }

    public int getLinkCount() {
        return nativeGetLinkCount();
    }

    public int getTollGateQuickPayType() {
        return nativeGetTollGateQuickPayType();
    }

    public RouteLink getLink(int i) {
        if (i >= getLinkCount() || i < 0) {
            return null;
        }
        return new RouteLink(this.mRoutePtr, this.mIndex, i);
    }

    public double[] getSegCoor() {
        return nativeGetSegCoor();
    }

    public double[] getSeg3DCoor() {
        return nativeGetSeg3DCoor();
    }

    public int getSegLength() {
        return nativeGetSegLength();
    }

    public int getSegTime() {
        return nativeGetSegTime();
    }

    public int getSegTollCost() {
        return nativeGetSegTollCost();
    }

    public String getSegTollPathName() {
        return nativeGetSegTollPathName();
    }

    public int getMainAction() {
        return nativeGetMainAction();
    }

    public int getAssistAction() {
        return nativeGetAssistAction();
    }

    public int getSegChargeLength() {
        return nativeGetSegChargeLength();
    }

    public boolean isRightPassArea() {
        return nativeIsRightPassArea();
    }

    public int getTrafficLightNum() {
        return nativeGetTrafficLightNum();
    }

    public GeoPoint getStartPoint() {
        return nativeGetStartPoint();
    }
}
