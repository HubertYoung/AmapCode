package com.sijla.g.a;

import java.security.MessageDigest;

public class d {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String a(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.trim().getBytes());
            return a(instance.digest());
        } catch (Throwable th) {
            th.printStackTrace();
            return str;
        }
    }

    private static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append(a[(b & 240) >>> 4]);
            sb.append(a[b & 15]);
        }
        return sb.toString();
    }
}
