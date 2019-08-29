package com.alipay.android.phone.inside.common.sec;

import android.util.Base64;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class RSAHelper {
    private static PublicKey b(String str, String str2) {
        try {
            return KeyFactory.getInstance(str).generatePublic(new X509EncodedKeySpec(Base64.decode(str2, 2)));
        } catch (Exception unused) {
            return null;
        }
    }

    public static String a(String str, String str2) {
        if ("".equals(str)) {
            return "";
        }
        try {
            PublicKey b = b("RSA", str2);
            if (b == null) {
                return null;
            }
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance.init(1, b);
            return new String(Base64.encode(instance.doFinal(str.getBytes("UTF-8")), 2));
        } catch (Exception e) {
            LoggerFactory.f().b("RSAHelper", "", e);
            return null;
        }
    }
}
