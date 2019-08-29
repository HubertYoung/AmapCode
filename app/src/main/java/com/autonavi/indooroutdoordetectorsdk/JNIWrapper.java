package com.autonavi.indooroutdoordetectorsdk;

import com.autonavi.indoor.util.L;

public class JNIWrapper {
    static int mGpsMsg;
    static int mLightMsg;
    static int mMagMsg;

    public static native byte[] jniCompress(byte[] bArr);

    public static native String jniGetIndoorSwitchDebugString();

    public static native boolean jniGetSwitchInfo();

    public static native JniSwitchResult jniGetSwitchResult();

    public static native int jniSetAttitude(long j, double d, double d2, double d3);

    public static native boolean jniSetDebug(boolean z);

    public static native int jniSetFlag(long j, String str);

    public static native int jniSetGPSState(long j, int i, double d);

    public static native int jniSetLightData(long j, int i);

    public static native int jniSetMagData(long j, double d, double d2, double d3);

    public static native int jniSetNemaData(long j, String str);

    public static native int jniSetPedData(long j, int i, double d);

    public static native int jniStartIndoorSwitch();

    public static native int jniStopIndoorSwitch();

    static {
        try {
            System.loadLibrary("iodetector6.9");
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d("###Load so failed:".concat(String.valueOf("iodetector6.9")));
            }
            if (L.isLogging) {
                L.d(th);
            }
        }
        jniSetDebug(false);
    }

    public static void jniGetSwitchInfoCallback(int i, int i2, int i3) {
        mLightMsg = i;
        mGpsMsg = i2;
        mMagMsg = i3;
    }
}
