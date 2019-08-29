package com.autonavi.jni.route.wtbt;

public class WTBTDecoder {
    public static native String getVersion();

    public native void destroy();

    public native int[] getAllRouteID();

    public native double[] getEndCoor();

    public native String getEndPoiID();

    public native double[] getLinkCoor(int i, int i2);

    public native String getNaviID();

    public native int getRouteCrossingCount();

    public native int getRouteLength();

    public native WMilestone[] getRouteMilestones();

    public native WPoint[] getRouteSearchPoints();

    public native String getRouteText();

    public native int getRouteTime();

    public native int getRouteTravelTime();

    public native double[] getSegCoor(int i);

    public native int getSegLinkNum(int i);

    public native int getSegNum();

    public native double[] getStartCoor();

    public native int getStartDirection();

    public native int init(String str);

    public native int pushRouteData(int i, int i2, byte[] bArr, int i3);

    public native int selectRoute(int i);
}
