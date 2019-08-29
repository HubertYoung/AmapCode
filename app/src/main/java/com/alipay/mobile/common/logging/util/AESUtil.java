package com.alipay.mobile.common.logging.util;

import android.util.Log;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    private static Cipher a;
    private static Cipher b;

    public static synchronized byte[] encrypt(byte[] rawKey, byte[] source, int index, int length) {
        byte[] bArr = null;
        synchronized (AESUtil.class) {
            try {
                if (a == null) {
                    SecretKeySpec skeySpec = new SecretKeySpec(rawKey, "AES");
                    Cipher instance = Cipher.getInstance("AES");
                    a = instance;
                    instance.init(1, skeySpec);
                }
                bArr = a.doFinal(source, index, length);
            } catch (Throwable t) {
                a = null;
                Log.w("LogAESUtil", t);
            }
        }
        return bArr;
    }

    public static synchronized byte[] decrypt(byte[] rawKey, byte[] encrypted, int index, int length) {
        byte[] bArr = null;
        synchronized (AESUtil.class) {
            try {
                if (b == null) {
                    SecretKeySpec skeySpec = new SecretKeySpec(rawKey, "AES");
                    Cipher instance = Cipher.getInstance("AES");
                    b = instance;
                    instance.init(2, skeySpec);
                }
                bArr = b.doFinal(encrypted, index, length);
            } catch (Throwable t) {
                b = null;
                Log.w("LogAESUtil", t);
            }
        }
        return bArr;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] getRawKey(byte[] r5) {
        /*
            java.lang.String r3 = "AES"
            javax.crypto.KeyGenerator r1 = javax.crypto.KeyGenerator.getInstance(r3)     // Catch:{ Throwable -> 0x002d }
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x002d }
            r4 = 17
            if (r3 < r4) goto L_0x0026
            java.lang.String r3 = "SHA1PRNG"
            java.lang.String r4 = "Crypto"
            java.security.SecureRandom r2 = java.security.SecureRandom.getInstance(r3, r4)     // Catch:{ Throwable -> 0x0025 }
        L_0x0014:
            r2.setSeed(r5)     // Catch:{ Throwable -> 0x002d }
            r3 = 128(0x80, float:1.794E-43)
            r1.init(r3, r2)     // Catch:{ Throwable -> 0x002d }
            javax.crypto.SecretKey r3 = r1.generateKey()     // Catch:{ Throwable -> 0x002d }
            byte[] r3 = r3.getEncoded()     // Catch:{ Throwable -> 0x002d }
        L_0x0024:
            return r3
        L_0x0025:
            r3 = move-exception
        L_0x0026:
            java.lang.String r3 = "SHA1PRNG"
            java.security.SecureRandom r2 = java.security.SecureRandom.getInstance(r3)     // Catch:{ Throwable -> 0x002d }
            goto L_0x0014
        L_0x002d:
            r0 = move-exception
            java.lang.String r3 = "LogAESUtil"
            android.util.Log.w(r3, r0)
            r3 = 0
            goto L_0x0024
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.util.AESUtil.getRawKey(byte[]):byte[]");
    }
}
