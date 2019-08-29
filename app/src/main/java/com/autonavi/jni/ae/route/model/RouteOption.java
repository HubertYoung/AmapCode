package com.autonavi.jni.ae.route.model;

public class RouteOption {
    private long mPtr = nativeCreate();

    private native long nativeCreate();

    private native void nativeDestroy(long j);

    public native boolean setConstrainCode(int i);

    public native boolean setPOIForRequest(POIForRequest pOIForRequest);

    public native boolean setRequestRouteType(int i);

    public void release() {
        nativeDestroy(this.mPtr);
    }
}
