package com.alipay.mobile.common.logging.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class RSAUtil {
    private static Cipher a;
    private static Cipher b;

    private static PublicKey a(String algorithm, String bysKey) {
        return KeyFactory.getInstance(algorithm).generatePublic(new X509EncodedKeySpec(Base64.decode(bysKey)));
    }

    private static PrivateKey b(String algorithm, String bysKey) {
        return KeyFactory.getInstance(algorithm).generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(bysKey)));
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0055 A[SYNTHETIC, Splitter:B:30:0x0055] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x005e A[SYNTHETIC, Splitter:B:35:0x005e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized byte[] encrypt(byte[] r10, java.lang.String r11) {
        /*
            r7 = 117(0x75, float:1.64E-43)
            java.lang.Class<com.alipay.mobile.common.logging.util.RSAUtil> r8 = com.alipay.mobile.common.logging.util.RSAUtil.class
            monitor-enter(r8)
            r3 = 0
            r0 = 0
            javax.crypto.Cipher r6 = a     // Catch:{ Exception -> 0x004a }
            if (r6 != 0) goto L_0x001d
            java.lang.String r6 = "RSA"
            java.security.PublicKey r5 = a(r6, r11)     // Catch:{ Exception -> 0x004a }
            java.lang.String r6 = "RSA/ECB/PKCS1Padding"
            javax.crypto.Cipher r6 = javax.crypto.Cipher.getInstance(r6)     // Catch:{ Exception -> 0x004a }
            a = r6     // Catch:{ Exception -> 0x004a }
            r9 = 1
            r6.init(r9, r5)     // Catch:{ Exception -> 0x004a }
        L_0x001d:
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x004a }
            r1.<init>()     // Catch:{ Exception -> 0x004a }
            r4 = 0
        L_0x0023:
            int r6 = r10.length     // Catch:{ Exception -> 0x006a, all -> 0x0067 }
            if (r4 >= r6) goto L_0x003a
            javax.crypto.Cipher r9 = a     // Catch:{ Exception -> 0x006a, all -> 0x0067 }
            int r6 = r10.length     // Catch:{ Exception -> 0x006a, all -> 0x0067 }
            int r6 = r6 - r4
            if (r6 >= r7) goto L_0x0038
            int r6 = r10.length     // Catch:{ Exception -> 0x006a, all -> 0x0067 }
            int r6 = r6 - r4
        L_0x002e:
            byte[] r6 = r9.doFinal(r10, r4, r6)     // Catch:{ Exception -> 0x006a, all -> 0x0067 }
            r1.write(r6)     // Catch:{ Exception -> 0x006a, all -> 0x0067 }
            int r4 = r4 + 117
            goto L_0x0023
        L_0x0038:
            r6 = r7
            goto L_0x002e
        L_0x003a:
            r1.flush()     // Catch:{ Exception -> 0x006a, all -> 0x0067 }
            byte[] r3 = r1.toByteArray()     // Catch:{ Exception -> 0x006a, all -> 0x0067 }
            r1.close()     // Catch:{ IOException -> 0x0047, all -> 0x006d }
            r0 = r1
        L_0x0045:
            monitor-exit(r8)
            return r3
        L_0x0047:
            r6 = move-exception
            r0 = r1
            goto L_0x0045
        L_0x004a:
            r2 = move-exception
        L_0x004b:
            r6 = 0
            a = r6     // Catch:{ all -> 0x005b }
            java.lang.String r6 = "LogRSAUtil"
            android.util.Log.w(r6, r2)     // Catch:{ all -> 0x005b }
            if (r0 == 0) goto L_0x0045
            r0.close()     // Catch:{ IOException -> 0x0059 }
            goto L_0x0045
        L_0x0059:
            r6 = move-exception
            goto L_0x0045
        L_0x005b:
            r6 = move-exception
        L_0x005c:
            if (r0 == 0) goto L_0x0061
            r0.close()     // Catch:{ IOException -> 0x0065 }
        L_0x0061:
            throw r6     // Catch:{ all -> 0x0062 }
        L_0x0062:
            r6 = move-exception
        L_0x0063:
            monitor-exit(r8)
            throw r6
        L_0x0065:
            r7 = move-exception
            goto L_0x0061
        L_0x0067:
            r6 = move-exception
            r0 = r1
            goto L_0x005c
        L_0x006a:
            r2 = move-exception
            r0 = r1
            goto L_0x004b
        L_0x006d:
            r6 = move-exception
            r0 = r1
            goto L_0x0063
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.util.RSAUtil.encrypt(byte[], java.lang.String):byte[]");
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0055 A[SYNTHETIC, Splitter:B:30:0x0055] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x005e A[SYNTHETIC, Splitter:B:35:0x005e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized byte[] decrypt(byte[] r10, java.lang.String r11) {
        /*
            r7 = 128(0x80, float:1.794E-43)
            java.lang.Class<com.alipay.mobile.common.logging.util.RSAUtil> r8 = com.alipay.mobile.common.logging.util.RSAUtil.class
            monitor-enter(r8)
            r2 = 0
            r0 = 0
            javax.crypto.Cipher r6 = b     // Catch:{ Exception -> 0x004a }
            if (r6 != 0) goto L_0x001d
            java.lang.String r6 = "RSA"
            java.security.PrivateKey r5 = b(r6, r11)     // Catch:{ Exception -> 0x004a }
            java.lang.String r6 = "RSA/ECB/PKCS1Padding"
            javax.crypto.Cipher r6 = javax.crypto.Cipher.getInstance(r6)     // Catch:{ Exception -> 0x004a }
            b = r6     // Catch:{ Exception -> 0x004a }
            r9 = 2
            r6.init(r9, r5)     // Catch:{ Exception -> 0x004a }
        L_0x001d:
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x004a }
            r1.<init>()     // Catch:{ Exception -> 0x004a }
            r4 = 0
        L_0x0023:
            int r6 = r10.length     // Catch:{ Exception -> 0x006a, all -> 0x0067 }
            if (r4 >= r6) goto L_0x003a
            javax.crypto.Cipher r9 = b     // Catch:{ Exception -> 0x006a, all -> 0x0067 }
            int r6 = r10.length     // Catch:{ Exception -> 0x006a, all -> 0x0067 }
            int r6 = r6 - r4
            if (r6 >= r7) goto L_0x0038
            int r6 = r10.length     // Catch:{ Exception -> 0x006a, all -> 0x0067 }
            int r6 = r6 - r4
        L_0x002e:
            byte[] r6 = r9.doFinal(r10, r4, r6)     // Catch:{ Exception -> 0x006a, all -> 0x0067 }
            r1.write(r6)     // Catch:{ Exception -> 0x006a, all -> 0x0067 }
            int r4 = r4 + 128
            goto L_0x0023
        L_0x0038:
            r6 = r7
            goto L_0x002e
        L_0x003a:
            r1.flush()     // Catch:{ Exception -> 0x006a, all -> 0x0067 }
            byte[] r2 = r1.toByteArray()     // Catch:{ Exception -> 0x006a, all -> 0x0067 }
            r1.close()     // Catch:{ IOException -> 0x0047, all -> 0x006d }
            r0 = r1
        L_0x0045:
            monitor-exit(r8)
            return r2
        L_0x0047:
            r6 = move-exception
            r0 = r1
            goto L_0x0045
        L_0x004a:
            r3 = move-exception
        L_0x004b:
            r6 = 0
            b = r6     // Catch:{ all -> 0x005b }
            java.lang.String r6 = "LogRSAUtil"
            android.util.Log.w(r6, r3)     // Catch:{ all -> 0x005b }
            if (r0 == 0) goto L_0x0045
            r0.close()     // Catch:{ IOException -> 0x0059 }
            goto L_0x0045
        L_0x0059:
            r6 = move-exception
            goto L_0x0045
        L_0x005b:
            r6 = move-exception
        L_0x005c:
            if (r0 == 0) goto L_0x0061
            r0.close()     // Catch:{ IOException -> 0x0065 }
        L_0x0061:
            throw r6     // Catch:{ all -> 0x0062 }
        L_0x0062:
            r6 = move-exception
        L_0x0063:
            monitor-exit(r8)
            throw r6
        L_0x0065:
            r7 = move-exception
            goto L_0x0061
        L_0x0067:
            r6 = move-exception
            r0 = r1
            goto L_0x005c
        L_0x006a:
            r3 = move-exception
            r0 = r1
            goto L_0x004b
        L_0x006d:
            r6 = move-exception
            r0 = r1
            goto L_0x0063
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.util.RSAUtil.decrypt(byte[], java.lang.String):byte[]");
    }
}
