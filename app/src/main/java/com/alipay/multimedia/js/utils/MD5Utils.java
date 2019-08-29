package com.alipay.multimedia.js.utils;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.security.MessageDigest;

public class MD5Utils {
    public static final String ALGORIGTHM_MD5 = "MD5";
    protected static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public MD5Utils() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static MessageDigest getMD5Digest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getMD5String(byte[] bytes) {
        MessageDigest messagedigest = getMD5Digest();
        messagedigest.update(bytes);
        return bytesToHex(messagedigest.digest());
    }

    public static String bytesToHex(byte[] bytes) {
        return bytesToHex(bytes, 0, bytes.length);
    }

    public static String bytesToHex(byte[] bytes, int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(n * 2);
        int k = m + n;
        for (int l = m; l < k; l++) {
            a(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void a(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 240) >> 4];
        char c1 = hexDigits[bt & 15];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}
