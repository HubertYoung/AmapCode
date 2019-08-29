package com.alipay.mobile.common.security;

import android.util.Base64;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class RSAHelper {
    public RSAHelper() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    private static PublicKey a(String algorithm, String bysKey) {
        try {
            return KeyFactory.getInstance(algorithm).generatePublic(new X509EncodedKeySpec(Base64.decode(bysKey, 2)));
        } catch (Exception e) {
            return null;
        }
    }

    public static String encrypt(String content, String key) {
        if ("".equals(content)) {
            return "";
        }
        try {
            PublicKey pubkey = a("RSA", key);
            if (pubkey == null) {
                return null;
            }
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(1, pubkey);
            return new String(Base64.encode(cipher.doFinal(content.getBytes("UTF-8")), 2));
        } catch (Exception e) {
            Log.e("RSAHelper", "", e);
            return null;
        }
    }
}
