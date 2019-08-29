package com.autonavi.jni.ae.route.route;

import java.util.HashMap;
import java.util.Map;

public class CalcRouteResult {
    private long mPtr;
    public Map<Object, Object> mResultInfo = new HashMap();
    private int mRouteReqState;

    private native int nativeAddPath(long j);

    private native void nativeDestroy();

    private native Route nativeGetRoute(int i);

    private native boolean nativeRemovePath(long j);

    public native int nativeGetPathCount();

    public void destroy() {
        nativeDestroy();
    }

    public int getPathCount() {
        return nativeGetPathCount();
    }

    public Route getRoute(int i) {
        return nativeGetRoute(i);
    }

    public int addPath(long j) {
        return nativeAddPath(j);
    }

    public boolean removePath(long j) {
        return nativeRemovePath(j);
    }

    public int getRouteReqState() {
        return this.mRouteReqState;
    }

    public long getPtr() {
        return this.mPtr;
    }

    public void setPtr(long j) {
        this.mPtr = j;
    }
}
