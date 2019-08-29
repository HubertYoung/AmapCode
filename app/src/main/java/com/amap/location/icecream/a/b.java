package com.amap.location.icecream.a;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: MD5Util */
public class b {
    public static String a(byte[] bArr) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException unused) {
            messageDigest = null;
        }
        if (messageDigest == null) {
            return "";
        }
        byte[] digest = messageDigest.digest(bArr);
        StringBuilder sb = new StringBuilder();
        for (byte valueOf : digest) {
            sb.append(String.format("%02X", new Object[]{Byte.valueOf(valueOf)}));
        }
        return sb.toString().toUpperCase();
    }
}
