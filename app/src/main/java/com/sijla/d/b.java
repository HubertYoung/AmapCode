package com.sijla.d;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class b {
    private static byte[] a = new byte[8];

    static {
        int i = 0;
        while (i < 8) {
            int i2 = i + 1;
            a[i] = (byte) i2;
            i = i2;
        }
    }

    public static String a(String str, String str2) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(a);
            SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), "DES");
            Cipher instance = Cipher.getInstance("DES/CBC/PKCS5Padding");
            instance.init(1, secretKeySpec, ivParameterSpec);
            return a.a(instance.doFinal(str2.getBytes()));
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static String b(String str, String str2) {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(a);
        SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), "DES");
        try {
            Cipher instance = Cipher.getInstance("DES/CBC/PKCS5Padding");
            instance.init(2, secretKeySpec, ivParameterSpec);
            return new String(instance.doFinal(a.a(str2)));
        } catch (Throwable unused) {
            return "";
        }
    }
}
