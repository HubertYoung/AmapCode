package com.alipay.mobile.nebula.util;

import android.text.TextUtils;
import java.security.MessageDigest;
import java.util.Locale;

public class H5SecurityUtil {
    public static final String TAG = "SecurityUtil";

    public static String getMD5(String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        try {
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            char[] charArray = text.toCharArray();
            byte[] byteArray = new byte[charArray.length];
            for (int index = 0; index < charArray.length; index++) {
                byteArray[index] = (byte) charArray[index];
            }
            byte[] md5Bytes = md5Digest.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (byte b : md5Bytes) {
                int val = b & 255;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            H5Log.e(TAG, "exception detail", e);
            return null;
        }
    }

    public static String bytes2Hex(byte[] bytes) {
        String hs = "";
        for (byte b : bytes) {
            String stmp = Integer.toHexString(b & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toLowerCase(Locale.ENGLISH);
    }

    public static String getSHA1(String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        try {
            MessageDigest sha1Digest = MessageDigest.getInstance("SHA-1");
            sha1Digest.update(text.getBytes(), 0, text.length());
            return bytes2Hex(sha1Digest.digest());
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean pathSecurityCheck(String path) {
        if (path.contains("..") || path.contains("/") || path.contains("\\") || path.contains("%")) {
            return false;
        }
        return true;
    }
}
