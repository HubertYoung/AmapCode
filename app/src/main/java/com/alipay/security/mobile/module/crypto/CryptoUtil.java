package com.alipay.security.mobile.module.crypto;

import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class CryptoUtil {
    public static final byte[] DEFAULT_KEY = Hex.decode((String) "7B726A5DDD72CBF8D1700FB6EB278AFD7559C40A3761E5A71614D0AC9461ED8EE9F6AAEB443CD648");
    public static final String HMAC_SHA1 = "HmacSHA1";
    public static final String HMAC_SHA_1 = "HMAC-SHA-1";
    public static final String RAW = "RAW";

    private CryptoUtil() {
    }

    public static byte[] digestWithHmacSha1(byte[] bArr, byte[] bArr2) throws Exception {
        Mac mac;
        try {
            mac = Mac.getInstance(HMAC_SHA1);
        } catch (NoSuchAlgorithmException unused) {
            mac = Mac.getInstance(HMAC_SHA_1);
        }
        mac.init(new SecretKeySpec(bArr2, RAW));
        return mac.doFinal(bArr);
    }

    public static String digestWithHmacSha1(String str) throws UnsupportedEncodingException {
        Mac mac;
        if (CommonUtils.isBlank(str)) {
            return null;
        }
        try {
            mac = Mac.getInstance(HMAC_SHA1);
        } catch (NoSuchAlgorithmException unused) {
            try {
                mac = Mac.getInstance(HMAC_SHA_1);
            } catch (NoSuchAlgorithmException unused2) {
                return null;
            }
        }
        try {
            mac.init(new SecretKeySpec(DEFAULT_KEY, RAW));
            byte[] doFinal = mac.doFinal(str.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                sb.append(String.format("%02x", new Object[]{Byte.valueOf(doFinal[i])}));
            }
            return sb.toString();
        } catch (Exception unused3) {
            return null;
        }
    }
}
