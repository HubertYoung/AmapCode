package com.autonavi.jni.ae.route.model;

public class RerouteOption {
    public long mPtr;

    public interface RequestRouteType {
        public static final int RequestRouteTypeBest = 0;
        public static final int RequestRouteTypeDist = 2;
        public static final int RequestRouteTypeMoney = 1;
        public static final int RequestRouteTypeMostly = 13;
        public static final int RequestRouteTypeMulti = 5;
        public static final int RequestRouteTypeNULL = -1;
        public static final int RequestRouteTypeNorm = 3;
        public static final int RequestRouteTypeTMC = 4;
        public static final int RequestRouteTypeTMCFree = 12;
        public static final int RequestRouteTypeThree = 9;
    }

    public interface RouteType {
        public static final int RouteTypeChangeJnyPnt = 9;
        public static final int RouteTypeChangeStratege = 3;
        public static final int RouteTypeCommon = 1;
        public static final int RouteTypeDamagedRoad = 7;
        public static final int RouteTypeLimitForbid = 11;
        public static final int RouteTypeLimitForbidOffLine = 13;
        public static final int RouteTypeLimitLine = 6;
        public static final int RouteTypeManualRefresh = 12;
        public static final int RouteTypeMax = 15;
        public static final int RouteTypeMutiRouteRequest = 14;
        public static final int RouteTypeParallelRoad = 4;
        public static final int RouteTypePressure = 8;
        public static final int RouteTypeTMC = 5;
        public static final int RouteTypeUpdateCityData = 10;
        public static final int RouteTypeYaw = 2;
    }

    private static native long nativeCreate();

    private static native long nativeCreateAndCopy(long j);

    private static native void nativeDestroy(long j);

    public native int getRequestRouteType();

    public native int getRerouteType();

    public native boolean setConstrainCode(int i);

    public native boolean setCurrentLocation(CurrentPositionInfo currentPositionInfo);

    public native boolean setNaviPath(long j);

    public native boolean setPOIForRequest(POIForRequest pOIForRequest);

    public native boolean setRemainNaviInfo(CurrentNaviInfo currentNaviInfo);

    public native boolean setRequestRouteType(int i);

    public native boolean setRerouteType(int i);

    public native boolean setRouteMode(int i);

    public RerouteOption(boolean z) {
        if (z) {
            this.mPtr = nativeCreate();
        }
    }

    public RerouteOption(long j) {
        this.mPtr = nativeCreateAndCopy(j);
    }

    public RerouteOption() {
        this(true);
    }

    public void release() {
        nativeDestroy(this.mPtr);
        this.mPtr = 0;
    }
}
