package com.alipay.android.phone.inside.offlinecode.utils;

import java.security.MessageDigest;

public class MD5 {
    public static String getHexDigest(String str) throws Exception {
        MessageDigest instance = MessageDigest.getInstance("MD5");
        instance.update(str.getBytes());
        return toHex(instance.digest());
    }

    private static String toHex(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            stringBuffer.append(Character.forDigit((bArr[i] & 240) >> 4, 16));
            stringBuffer.append(Character.forDigit(bArr[i] & 15, 16));
        }
        return stringBuffer.toString();
    }
}
