package com.alibaba.analytics.utils;

public class ByteUtils {
    public static int bytesToInt(byte[] bArr, int i, int i2) {
        if (bArr == null || i < 0 || i2 < 0 || bArr.length < i + i2) {
            return 0;
        }
        byte[] bArr2 = new byte[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            bArr2[i3] = bArr[i];
            i++;
        }
        return bytesToInt(bArr2);
    }

    public static int bytesToInt(byte[] bArr) {
        if (bArr == null || bArr.length > 4) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            i |= (bArr[i2] & 255) << (((bArr.length - i2) - 1) * 8);
        }
        return i;
    }

    public static String bytes2UTF8String(byte[] bArr) {
        try {
            return new String(bArr, "UTF-8");
        } catch (Exception unused) {
            return "";
        }
    }

    public static String bytes2UTF8string(byte[] bArr, int i, int i2) {
        if (bArr == null || i < 0 || i2 < 0 || bArr.length < i + i2) {
            return "";
        }
        byte[] bArr2 = new byte[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            bArr2[i3] = bArr[i];
            i++;
        }
        return bytes2UTF8String(bArr2);
    }

    public static byte[] intToBytes(int i, int i2) {
        if (i2 > 4 || i2 <= 0) {
            return null;
        }
        byte[] bArr = new byte[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            bArr[i3] = (byte) ((i >> (((i2 - i3) - 1) * 8)) & 255);
        }
        return bArr;
    }

    public static byte[] intToBytes1(int i) {
        return new byte[]{(byte) (i & 255)};
    }

    public static byte[] intToBytes2(int i) {
        return new byte[]{(byte) ((i >> 8) & 255), (byte) (i & 255)};
    }

    public static byte[] intToBytes3(int i) {
        return new byte[]{(byte) ((i >> 16) & 255), (byte) ((i >> 8) & 255), (byte) (i & 255)};
    }

    public static byte[] intToBytes4(int i) {
        return new byte[]{(byte) ((i >> 24) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 8) & 255), (byte) (i & 255)};
    }

    public static byte[] subBytes(byte[] bArr, int i, int i2) {
        if (bArr != null && i >= 0 && i2 >= 0) {
            int i3 = i + i2;
            if (bArr.length >= i3) {
                byte[] bArr2 = new byte[i2];
                for (int i4 = i; i4 < i3; i4++) {
                    bArr2[i4 - i] = bArr[i4];
                }
                return bArr2;
            }
        }
        return null;
    }
}
