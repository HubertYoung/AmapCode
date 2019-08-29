package com.alipay.android.phone.inside.log.util.sec;

import android.text.TextUtils;
import android.util.Base64;
import com.alipay.mobile.security.bio.utils.DESCoder;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DesCBC {
    private static String a = "DESede/CBC/PKCS5Padding";

    public static String a(String str, String str2) throws Exception {
        if (TextUtils.isEmpty(str2)) {
            return str2;
        }
        String str3 = null;
        byte[] a2 = a(str, str2.getBytes());
        if (a2 != null) {
            str3 = Base64.encodeToString(a2, 0);
        }
        return str3;
    }

    public static String b(String str, String str2) throws Exception {
        if (TextUtils.isEmpty(str2)) {
            return str2;
        }
        byte[] b = b(str, Base64.decode(str2, 0));
        String str3 = null;
        if (b != null) {
            str3 = new String(b);
        }
        return str3;
    }

    public static byte[] a(String str, byte[] bArr) throws Exception {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), DESCoder.ALGORITHM);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[8]);
            Cipher instance = Cipher.getInstance(a);
            instance.init(1, secretKeySpec, ivParameterSpec);
            return instance.doFinal(bArr);
        } catch (Exception e) {
            throw e;
        }
    }

    public static byte[] b(String str, byte[] bArr) throws Exception {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), DESCoder.ALGORITHM);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[8]);
            Cipher instance = Cipher.getInstance(a);
            instance.init(2, secretKeySpec, ivParameterSpec);
            return instance.doFinal(bArr);
        } catch (Exception e) {
            throw e;
        }
    }
}
