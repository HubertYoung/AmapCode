package com.alipay.sdk.encrypt;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public final class d {
    private static final String a = "RSA";

    private static PublicKey b(String str, String str2) throws NoSuchAlgorithmException, Exception {
        return KeyFactory.getInstance(str).generatePublic(new X509EncodedKeySpec(a.a(str2)));
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0050 A[SYNTHETIC, Splitter:B:19:0x0050] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0057 A[SYNTHETIC, Splitter:B:27:0x0057] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] a(java.lang.String r5, java.lang.String r6) {
        /*
            r0 = 0
            java.lang.String r1 = "RSA"
            byte[] r6 = com.alipay.sdk.encrypt.a.a(r6)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            java.security.spec.X509EncodedKeySpec r2 = new java.security.spec.X509EncodedKeySpec     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            r2.<init>(r6)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            java.security.KeyFactory r6 = java.security.KeyFactory.getInstance(r1)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            java.security.PublicKey r6 = r6.generatePublic(r2)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            java.lang.String r1 = "RSA/ECB/PKCS1Padding"
            javax.crypto.Cipher r1 = javax.crypto.Cipher.getInstance(r1)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            r2 = 1
            r1.init(r2, r6)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            java.lang.String r6 = "UTF-8"
            byte[] r5 = r5.getBytes(r6)     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            int r6 = r1.getBlockSize()     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            r2.<init>()     // Catch:{ Exception -> 0x0054, all -> 0x004d }
            r3 = 0
        L_0x002e:
            int r4 = r5.length     // Catch:{ Exception -> 0x0055, all -> 0x004a }
            if (r3 >= r4) goto L_0x0042
            int r4 = r5.length     // Catch:{ Exception -> 0x0055, all -> 0x004a }
            int r4 = r4 - r3
            if (r4 >= r6) goto L_0x0038
            int r4 = r5.length     // Catch:{ Exception -> 0x0055, all -> 0x004a }
            int r4 = r4 - r3
            goto L_0x0039
        L_0x0038:
            r4 = r6
        L_0x0039:
            byte[] r4 = r1.doFinal(r5, r3, r4)     // Catch:{ Exception -> 0x0055, all -> 0x004a }
            r2.write(r4)     // Catch:{ Exception -> 0x0055, all -> 0x004a }
            int r3 = r3 + r6
            goto L_0x002e
        L_0x0042:
            byte[] r5 = r2.toByteArray()     // Catch:{ Exception -> 0x0055, all -> 0x004a }
            r2.close()     // Catch:{ IOException -> 0x005b }
            goto L_0x005b
        L_0x004a:
            r5 = move-exception
            r0 = r2
            goto L_0x004e
        L_0x004d:
            r5 = move-exception
        L_0x004e:
            if (r0 == 0) goto L_0x0053
            r0.close()     // Catch:{ IOException -> 0x0053 }
        L_0x0053:
            throw r5
        L_0x0054:
            r2 = r0
        L_0x0055:
            if (r2 == 0) goto L_0x005a
            r2.close()     // Catch:{ IOException -> 0x005a }
        L_0x005a:
            r5 = r0
        L_0x005b:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.encrypt.d.a(java.lang.String, java.lang.String):byte[]");
    }
}
