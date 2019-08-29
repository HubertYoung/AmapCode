package com.autonavi.jni.eyrie.amap.redesign.maps.vmap;

import android.content.Context;

public class VMapSurface {
    private final int mEngineId;
    private long mNativePtr = nativeCreateInstance(this.mEngineId);

    private static native long nativeCreateInstance(int i);

    private static native long nativeCreatePage(long j, String str, boolean z, int i);

    private static native void nativeDestroyInstance(long j);

    private static native void nativeDestroyPage(long j, long j2);

    public VMapSurface(int i) {
        this.mEngineId = i;
    }

    public void release() {
        nativeDestroyInstance(this.mNativePtr);
        this.mNativePtr = 0;
    }

    public VMapPage createVMapPage(Context context, String str) {
        return createVMapPage(context, str, false, 0);
    }

    public VMapPage createVMapPage(Context context, String str, boolean z) {
        return createVMapPage(context, str, z, 0);
    }

    public VMapPage createVMapPage(Context context, String str, int i) {
        return createVMapPage(context, str, false, i);
    }

    private VMapPage createVMapPage(Context context, String str, boolean z, int i) {
        return new VMapPage(nativeCreatePage(this.mNativePtr, str, z, i), this.mEngineId, context);
    }

    public void destroyVMapPage(VMapPage vMapPage) {
        vMapPage.onDestroy();
        nativeDestroyPage(this.mNativePtr, vMapPage.mNativePtr);
        vMapPage.mNativePtr = 0;
    }
}
