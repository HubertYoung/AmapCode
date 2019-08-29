package com.alipay.mobile.security.bio.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignHelper {
    public static String SHA1(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-1").digest(str.getBytes());
            String str2 = "";
            for (int i = 1; i < digest.length; i++) {
                String hexString = Integer.toHexString(digest[i] & 255);
                if (hexString.length() == 1) {
                    str2 = str2 + "0" + hexString;
                } else {
                    str2 = str2 + hexString;
                }
            }
            return str2.toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            BioLog.e(e.toString());
            return null;
        }
    }

    public static String MD5(String str) {
        try {
            if (StringUtil.isNullorEmpty(str)) {
                return null;
            }
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes("UTF-8"));
            byte[] digest = instance.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                sb.append(String.format("%02x", new Object[]{Byte.valueOf(digest[i])}));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String MD5(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            byte[] digest = instance.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                sb.append(String.format("%02x", new Object[]{Byte.valueOf(digest[i])}));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
