package com.autonavi.jni.eyrie.amap.tbt;

import com.autonavi.jni.eyrie.amap.tbt.model.RouteWayPoint;

public class NaviManager {
    public static final int NaviSceneLaunchModeSingleInstance = 2;
    public static final int NaviSceneLaunchModeStandard = 1;

    private static native int nativeCalcRouteFromData(int i, float f, float f2, String str, String str2, String str3, int i2, float f3, float f4, String str4, String str5, String str6, int i3, byte[] bArr);

    private static native long nativeCalcRouteFromDataNew(int i, RouteWayPoint routeWayPoint, byte[] bArr);

    private static native int nativeCreateAndInitScene(int i, int i2, int i3);

    private static native int nativeCreateAndInitSceneByEngineID(int i, int i2, int i3, int i4);

    private static native long nativeCreatePathResult(int[] iArr);

    private static native int nativeCreateScene();

    private static native int nativeCreateScene(int i);

    private static native void nativeDestroyScene(int i);

    private static native String nativeGetHorusVersion();

    private static native String nativeGetTotalVersion();

    private static native void nativeRegisterEventReceiver(NaviEventReceiver naviEventReceiver);

    private static native void nativeReleasePathResult(long j);

    private static native void nativeSetConfig(int i, String str);

    private static native void nativeSetConfig(int i, String str, int i2);

    private static native void nativeSetTTSPlayer(ITTSPlayer iTTSPlayer);

    private static native void nativeSyncDestroyScene(int i);

    private static native void nativeUnregisterEventReceiver(NaviEventReceiver naviEventReceiver);

    public static native void setGpsInfo(int i, double d, double d2, double d3, double d4, double d5, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    public static void setTTSPlayer(ITTSPlayer iTTSPlayer) {
        nativeSetTTSPlayer(iTTSPlayer);
    }

    public static void setConfig(int i, String str) {
        nativeSetConfig(i, str);
    }

    public static void setConfig(int i, String str, int i2) {
        nativeSetConfig(i, str, i2);
    }

    public static int calcRouteFromData(int i, float f, float f2, String str, String str2, String str3, int i2, float f3, float f4, String str4, String str5, String str6, int i3, byte[] bArr) {
        return nativeCalcRouteFromData(i, f, f2, str, str2, str3, i2, f3, f4, str4, str5, str6, i3, bArr);
    }

    public static long createPathResult(int[] iArr) {
        return nativeCreatePathResult(iArr);
    }

    public static void releasePathResult(long j) {
        nativeReleasePathResult(j);
    }

    public static long calcRouteFromDataNew(int i, RouteWayPoint routeWayPoint, byte[] bArr) {
        return nativeCalcRouteFromDataNew(i, routeWayPoint, bArr);
    }

    public static int createScene() {
        return nativeCreateScene();
    }

    public static int createScene(int i) {
        return nativeCreateScene(i);
    }

    public static int createAndInitScene(int i, int i2) {
        return nativeCreateAndInitScene(i, i2, 1);
    }

    public static int createAndInitScene(int i, int i2, int i3) {
        return nativeCreateAndInitScene(i, i2, i3);
    }

    public static int createAndInitSceneByEngineID(int i, int i2, int i3, int i4) {
        return nativeCreateAndInitSceneByEngineID(i, i2, i3, i4);
    }

    public static void destroyScene(int i) {
        nativeDestroyScene(i);
    }

    public static void syncDestroyScene(int i) {
        nativeSyncDestroyScene(i);
    }

    public static void registerEventReceiver(NaviEventReceiver naviEventReceiver) {
        nativeRegisterEventReceiver(naviEventReceiver);
    }

    public static void unregisterEventReceiver(NaviEventReceiver naviEventReceiver) {
        nativeUnregisterEventReceiver(naviEventReceiver);
    }

    public static String getHorusVersion() {
        return nativeGetHorusVersion();
    }

    public static String getTotalVersion() {
        return nativeGetTotalVersion();
    }
}
