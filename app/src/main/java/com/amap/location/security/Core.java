package com.amap.location.security;

import android.content.Context;
import com.autonavi.jni.location.security.NativeCore;

public class Core {
    public static String ce(double d, int i) {
        return NativeCore.ce(d, i);
    }

    public static int cd(int i) {
        return NativeCore.cd(i);
    }

    public static byte[] cole(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        return NativeCore.cole(bArr, bArr2, bArr3);
    }

    public static String saos(String str, String str2, String str3) {
        return NativeCore.saos(str, str2, str3);
    }

    public static float rvd(float[] fArr) {
        return NativeCore.rvd(fArr);
    }

    public static float mad(float[] fArr, float[] fArr2) {
        return NativeCore.mad(fArr, fArr2);
    }

    public static void rts() {
        NativeCore.rts();
    }

    public static byte[] avc(Context context, byte[] bArr, int i) {
        return NativeCore.avc(context, bArr, i);
    }

    public static long ivs(long j) {
        return NativeCore.ivs(j);
    }

    public static String getNativeUriSegments(int i, int i2) {
        return NativeCore.getNativeUriSegments(i, i2);
    }

    public static byte[] doEncrypt(int i, byte[] bArr, int i2, int i3) {
        return NativeCore.doEncrypt(i, bArr, i2, i3);
    }

    public static long encMac(String str) {
        return NativeCore.encMac(str);
    }

    public static String[] getAuthServers() {
        return NativeCore.getAuthServers();
    }

    public static String getTag(Context context, String str) {
        return NativeCore.getTag(context, str);
    }

    public static String encode(Context context, String str, int i) {
        return NativeCore.encode(context, str, i);
    }

    public static String transfer(Context context, String str, double d) {
        return NativeCore.transfer(context, str, d);
    }

    public static String load(Context context, String str, long j) {
        return NativeCore.load(context, str, j);
    }

    public static byte[] xxt(byte[] bArr, int i) {
        return NativeCore.xxt(bArr, i);
    }

    public static String xxtAos(String str, String str2, int i) {
        return NativeCore.xxtAos(str, str2, i);
    }

    public static String gwl(String str, int i, int i2, String str2) {
        return NativeCore.gwl(str, i, i2, str2);
    }

    public static String gcl(int i, int i2, int i3) {
        return NativeCore.gcl(i, i2, i3);
    }

    public static double gd(int i, int i2, double d, double d2) {
        return NativeCore.gd(i, i2, d, d2);
    }
}
