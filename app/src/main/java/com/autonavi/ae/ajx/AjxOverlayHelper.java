package com.autonavi.ae.ajx;

public class AjxOverlayHelper {
    private static INativeMapViewCallback sNativeMapViewCallBack;
    private static IResourceConfigGetter sResourceConfigGetter;
    private static ISnapShotGetter sSnapShotGetter;
    private static ITextureConfigGetter sTextureConfigGetter;

    public interface INativeMapViewCallback {
        int createEagleEyeMapView(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, float f);

        void destroyEagleEyeMapView(int i);

        long getAnimationObserver(int i);
    }

    public interface IResourceConfigGetter {
        AjxResourceConfig getAjxResourceConfig(String str);
    }

    public interface ISnapShotGetter {
        AjxTextureConfig getSnapShotData(long j, long j2);
    }

    public interface ITextureConfigGetter {
        AjxTextureConfig getAjxTextureConfig(String str, boolean z);
    }

    private static native long nativeCreateAjxOverlayHelper();

    private static native void nativeDestroyMapViewControl(long j);

    private static native void nativeDestroyOverlayFactory(long j);

    private static native void nativeDestroyOverlayHelper(long j);

    private static native long nativeGetAjxNodeControl();

    private static native long nativeGetBusinessControl();

    private static native long nativeGetMapViewControl(long j);

    private static native long nativeGetOverlayFactory(long j);

    private static native long nativeGetTBTControl();

    private static native void nativeSetMainEngineID(int i);

    private static native void nativeSetUiWorker(AjxBLUIThread ajxBLUIThread);

    public static void setTextureConfigGetter(ITextureConfigGetter iTextureConfigGetter) {
        sTextureConfigGetter = iTextureConfigGetter;
    }

    public static void setSnapShotGetter(ISnapShotGetter iSnapShotGetter) {
        sSnapShotGetter = iSnapShotGetter;
    }

    public static void setResourceConfigGetter(IResourceConfigGetter iResourceConfigGetter) {
        sResourceConfigGetter = iResourceConfigGetter;
    }

    public static void setNativeMapViewCallBack(INativeMapViewCallback iNativeMapViewCallback) {
        sNativeMapViewCallBack = iNativeMapViewCallback;
    }

    public static long createAjxOverlayHelper() {
        return nativeCreateAjxOverlayHelper();
    }

    public static void destroyAjxOverlayHelper(long j) {
        nativeDestroyOverlayHelper(j);
    }

    private static int createEagleEyeMapView(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, float f) {
        if (sNativeMapViewCallBack != null) {
            return sNativeMapViewCallBack.createEagleEyeMapView(i, i2, i3, i4, i5, i6, i7, i8, f);
        }
        return -1;
    }

    private static void destroyEagleEyeMapView(int i) {
        if (sNativeMapViewCallBack != null) {
            sNativeMapViewCallBack.destroyEagleEyeMapView(i);
        }
    }

    private static long getAnimationObserver(int i) {
        if (sNativeMapViewCallBack != null) {
            return sNativeMapViewCallBack.getAnimationObserver(i);
        }
        return -1;
    }

    private static AjxTextureConfig getAjxTextureConfig(String str, boolean z) {
        if (sTextureConfigGetter != null) {
            return sTextureConfigGetter.getAjxTextureConfig(str, z);
        }
        return null;
    }

    private static AjxTextureConfig getSnapShotData(long j, long j2) {
        if (sSnapShotGetter != null) {
            return sSnapShotGetter.getSnapShotData(j, j2);
        }
        return null;
    }

    private static AjxResourceConfig getAjxResourceConfig(String str) {
        if (sResourceConfigGetter != null) {
            return sResourceConfigGetter.getAjxResourceConfig(str);
        }
        return null;
    }

    public static long getOverlayFactory(long j) {
        return nativeGetOverlayFactory(j);
    }

    public static long getMapViewControl(long j) {
        return nativeGetMapViewControl(j);
    }

    public static long getTBTControl() {
        return nativeGetTBTControl();
    }

    public static long getAjxNodeControl() {
        return nativeGetAjxNodeControl();
    }

    public static long getBusinessControl() {
        return nativeGetBusinessControl();
    }

    public static void destroyOverlayFactory(long j) {
        nativeDestroyOverlayFactory(j);
    }

    public static void destroyMapViewControl(long j) {
        nativeDestroyMapViewControl(j);
    }

    public static void setUiWorker(AjxBLUIThread ajxBLUIThread) {
        nativeSetUiWorker(ajxBLUIThread);
    }

    public static void setMainEngineID(int i) {
        nativeSetMainEngineID(i);
    }
}
