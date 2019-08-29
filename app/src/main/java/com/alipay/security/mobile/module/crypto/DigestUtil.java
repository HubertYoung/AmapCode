package com.alipay.security.mobile.module.crypto;

import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.security.MessageDigest;

public class DigestUtil {
    public static byte[] sha1ByByte(String str) {
        try {
            if (CommonUtils.isBlank(str)) {
                return null;
            }
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(str.getBytes("UTF-8"));
            return instance.digest();
        } catch (Exception unused) {
            return null;
        }
    }

    public static String sha1ByString(String str) {
        try {
            if (CommonUtils.isBlank(str)) {
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

    public static String digestWithSha1(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "";
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA1");
            instance.update(bArr);
            byte[] digest = instance.digest();
            StringBuilder sb = new StringBuilder();
            for (byte valueOf : digest) {
                sb.append(String.format("%02x", new Object[]{Byte.valueOf(valueOf)}));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String MD5(String str) {
        try {
            if (CommonUtils.isBlank(str)) {
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
        } catch (Exception unused) {
            return null;
        }
    }
}
