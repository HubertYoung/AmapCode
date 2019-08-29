package com.autonavi.indoor.onlinelocation;

import com.autonavi.indoor.entity.ScanData;
import com.autonavi.indoor.util.L;

public class JNIWrapper {
    static ScanData a;
    static byte[] b;
    static int c;
    static double d;
    static double e;
    static int f;

    public static native boolean jniAddMatStepData(long j, int i, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10, double d11, double d12, double d13, double d14, double d15, int i2, double d16, double d17);

    public static native boolean jniAddOnlineResult(long j, double d2, double d3, int i, double d4, double d5, double d6, long j2, double d7, double d8);

    public static native boolean jniAddPDRResult(long j, int i, double d2);

    public static native boolean jniAddPress(long j, double d2);

    public static native boolean jniAddScan(long j, int i, int i2);

    public static native boolean jniGetLocateResult(long j);

    public static native boolean jniGetSendOnlineRequest(long j, String str, boolean z);

    public static native void jniReset();

    public static native boolean jniSetDebug(boolean z);

    public static native void jniSetRequestHeader(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, long j);

    static {
        try {
            System.loadLibrary("onlinelocation6.9");
            jniSetDebug(false);
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d("###Load so failed:".concat(String.valueOf("onlinelocation6.9")));
            }
            if (L.isLogging) {
                L.d(th);
            }
        }
    }

    public static boolean jniSetScan(long j, ScanData scanData) {
        a = scanData;
        boolean jniAddScan = jniAddScan(j, scanData.type_ == 0 ? 2 : 1, scanData.size());
        a = null;
        return jniAddScan;
    }

    public static String jniGetIDCallback(int i) {
        if (a == null || i >= a.size()) {
            return null;
        }
        return a.scans_.get(i).mID;
    }

    public static int jniGetRssiCallback(int i) {
        if (a == null || i >= a.size()) {
            return 0;
        }
        return a.scans_.get(i).mRSSI;
    }

    public static String jniGetSsidCallback(int i) {
        if (a == null || i >= a.size()) {
            return null;
        }
        return a.scans_.get(i).mSsid;
    }

    public static void jniGetSendOnlineRequestCallback(int i, byte[] bArr) {
        b = bArr;
        c = i;
    }

    public static void jniGetLocateResultCallback(double d2, double d3, int i) {
        d = d2;
        e = d3;
        f = i;
    }
}
