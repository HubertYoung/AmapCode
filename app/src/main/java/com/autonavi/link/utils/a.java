package com.autonavi.link.utils;

/* compiled from: EndianConversionUtils */
public class a {
    public static short a(byte[] bArr, int i) {
        return (short) (bArr[i] + (bArr[i + 1] << 8));
    }

    public static int b(byte[] bArr, int i) {
        return ((bArr[i + 3] << 24) & -16777216) | (bArr[i] & 255) | ((bArr[i + 1] << 8) & 65280) | ((bArr[i + 2] << 16) & 16711680);
    }

    public static void a(byte[] bArr, int i, short s) {
        bArr[i] = (byte) (s & 255);
        bArr[i + 1] = (byte) (s >>> 8);
    }

    public static void a(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 & 255);
        bArr[i + 1] = (byte) (i2 >>> 8);
    }

    public static void b(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 & 255);
        bArr[i + 1] = (byte) (i2 >>> 8);
        bArr[i + 2] = (byte) (i2 >>> 16);
        bArr[i + 3] = (byte) (i2 >>> 24);
    }
}
