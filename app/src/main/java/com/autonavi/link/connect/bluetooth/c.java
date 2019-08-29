package com.autonavi.link.connect.bluetooth;

import java.security.MessageDigest;
import java.util.Arrays;

/* compiled from: BtUtils */
public class c {
    public static byte[] a(int i) {
        byte[] bArr = new byte[4];
        bArr[3] = (byte) (i & 255);
        bArr[2] = (byte) ((i >> 8) & 255);
        bArr[1] = (byte) ((i >> 16) & 255);
        bArr[0] = (byte) ((i >> 24) & 255);
        return bArr;
    }

    public static int a(byte[] bArr) {
        return (bArr[3] & 255) + ((bArr[2] & 255) << 8) + ((bArr[1] & 255) << 16) + ((bArr[0] & 255) << 24);
    }

    public static boolean a(byte[] bArr, byte[] bArr2) {
        return Arrays.equals(b(bArr), bArr2);
    }

    public static byte[] b(byte[] bArr) {
        try {
            return MessageDigest.getInstance("MD5").digest(bArr);
        } catch (Exception unused) {
            throw new UnsupportedOperationException("MD5 algorithm not available on this device.");
        }
    }
}
