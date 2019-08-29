package com.alipay.mobile.common.logging.util;

import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String encrypt(String inputText) {
        if (inputText == null || "".equals(inputText.trim())) {
            throw new IllegalArgumentException("input is null");
        }
        try {
            MessageDigest m = MessageDigest.getInstance("md5");
            m.update(inputText.getBytes("UTF8"));
            return a(m.digest());
        } catch (NoSuchAlgorithmException e) {
            Log.e("md5", "md5", e);
        } catch (UnsupportedEncodingException e2) {
            Log.e("md5", "md5", e2);
        }
        return null;
    }

    private static String a(byte[] arr) {
        StringBuffer hexString = new StringBuffer();
        for (byte b : arr) {
            String hex = Integer.toHexString(b & 255);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
