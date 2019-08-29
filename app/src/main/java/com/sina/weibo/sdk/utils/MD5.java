package com.sina.weibo.sdk.utils;

import com.autonavi.common.SuperId;
import java.io.PrintStream;
import java.security.MessageDigest;

public class MD5 {
    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String hexdigest(String str) {
        try {
            return hexdigest(str.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String hexdigest(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            byte[] digest = instance.digest();
            char[] cArr = new char[32];
            int i = 0;
            for (int i2 = 0; i2 < 16; i2++) {
                byte b = digest[i2];
                int i3 = i + 1;
                cArr[i] = hexDigits[(b >>> 4) & 15];
                i = i3 + 1;
                cArr[i3] = hexDigits[b & 15];
            }
            return new String(cArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] strArr) {
        PrintStream printStream = System.out;
        hexdigest((String) SuperId.BIT_1_NEARBY_SEARCH);
    }
}
