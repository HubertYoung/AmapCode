package com.alibaba.analytics.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    public static char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String toHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            sb.append(hexChar[(bArr[i] & 240) >>> 4]);
            sb.append(hexChar[bArr[i] & 15]);
        }
        return sb.toString();
    }

    public static String getMd5Hex(byte[] bArr) {
        byte[] md5 = getMd5(bArr);
        return md5 != null ? toHexString(md5) : "0000000000000000";
    }

    private static byte[] getMd5(byte[] bArr) {
        if (bArr != null) {
            try {
                MessageDigest instance = MessageDigest.getInstance("MD5");
                instance.update(bArr);
                return instance.digest();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
