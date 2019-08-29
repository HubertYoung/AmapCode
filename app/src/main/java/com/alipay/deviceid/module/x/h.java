package com.alipay.deviceid.module.x;

import java.security.MessageDigest;

public final class h {
    public static String a(String str) {
        try {
            if (e.a(str)) {
                return null;
            }
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(str.getBytes("UTF-8"));
            byte[] digest = instance.digest();
            StringBuilder sb = new StringBuilder();
            for (byte valueOf : digest) {
                sb.append(String.format("%02x", new Object[]{Byte.valueOf(valueOf)}));
            }
            return sb.toString();
        } catch (Exception unused) {
            return null;
        }
    }
}
