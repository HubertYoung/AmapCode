package com.alipay.android.phone.inside.cashier.enc;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Des {
    public static String encrypt(String str, String str2) {
        return doFinal(1, str, str2);
    }

    public static String decrypt(String str, String str2) {
        return doFinal(2, str, str2);
    }

    public static String doFinal(int i, String str, String str2) {
        byte[] bArr;
        String str3;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "DES");
            Cipher instance = Cipher.getInstance("DES");
            instance.init(i, secretKeySpec);
            if (i == 2) {
                bArr = Base64.decode(str);
            } else {
                bArr = str.getBytes("UTF-8");
            }
            byte[] doFinal = instance.doFinal(bArr);
            if (i == 2) {
                str3 = new String(doFinal);
            } else {
                str3 = Base64.encode(doFinal);
            }
            return str3;
        } catch (Exception e) {
            LoggerFactory.f().c((String) "inside", (Throwable) e);
            return null;
        }
    }
}
