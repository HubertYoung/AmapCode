package com.autonavi.jni.eyrie.amap.ar;

import android.content.Context;

public class ArManager {
    private static native void nativeCheckSupport(String str, String str2);

    private static native void nativeInitARContext(Context context, String str, String str2, String str3);

    private static native boolean nativeIsSupportAr();

    private static native void nativePostFrameBytes(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, byte[][] bArr, int[] iArr, int[] iArr2);

    public static boolean isSupportAr() {
        return nativeIsSupportAr();
    }

    public static void postFrameBytes(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, byte[][] bArr, int[] iArr, int[] iArr2) {
        nativePostFrameBytes(i, i2, i3, i4, i5, i6, i7, i8, bArr, iArr, iArr2);
    }

    public static void initARContext(Context context, String str, String str2, String str3) {
        nativeInitARContext(context, str, str2, str3);
    }

    public static void checkSupport(String str, String str2) {
        nativeCheckSupport(str, str2);
    }
}
