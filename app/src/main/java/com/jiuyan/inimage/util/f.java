package com.jiuyan.inimage.util;

import android.text.TextUtils;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: ImageUtils */
public class f {
    public static String a(String str) {
        return a(str, ".");
    }

    public static String a(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return ".null.png";
        }
        return str2 + b(str) + ".png";
    }

    public static String b(String str) {
        return new BigInteger(a(str.getBytes())).abs().toString(36);
    }

    private static byte[] a(byte[] bArr) {
        boolean z = false;
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            return instance.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return z;
        }
    }
}
