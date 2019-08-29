package com.autonavi.jni.server.aos;

import java.security.MessageDigest;

public class MD5Util {
    public static String getByteArrayMD5(byte[] bArr, String str, boolean z) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            return HexUtil.toHex(instance.digest(), z);
        } catch (Exception unused) {
            return str;
        }
    }

    public static String getByteArrayMD5(byte[] bArr) {
        return getByteArrayMD5(bArr, null, true);
    }

    public static String getStringMD5(String str) {
        char[] charArray = str.toCharArray();
        byte[] bArr = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            bArr[i] = (byte) charArray[i];
        }
        return getByteArrayMD5(bArr);
    }
}
