package com.ali.auth.third.core.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Rsa {
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private static PublicKey a(String str, String str2) throws NoSuchAlgorithmException, Exception {
        return KeyFactory.getInstance(str).generatePublic(new X509EncodedKeySpec(Base64.decode(str2)));
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x005d A[SYNTHETIC, Splitter:B:26:0x005d] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x006b A[SYNTHETIC, Splitter:B:35:0x006b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String decrypt(java.lang.String r5, java.lang.String r6) {
        /*
            r0 = 0
            java.security.spec.PKCS8EncodedKeySpec r1 = new java.security.spec.PKCS8EncodedKeySpec     // Catch:{ Exception -> 0x0056, all -> 0x0054 }
            byte[] r6 = com.ali.auth.third.core.util.Base64.decode(r6)     // Catch:{ Exception -> 0x0056, all -> 0x0054 }
            r1.<init>(r6)     // Catch:{ Exception -> 0x0056, all -> 0x0054 }
            java.lang.String r6 = "RSA"
            java.security.KeyFactory r6 = java.security.KeyFactory.getInstance(r6)     // Catch:{ Exception -> 0x0056, all -> 0x0054 }
            java.security.PrivateKey r6 = r6.generatePrivate(r1)     // Catch:{ Exception -> 0x0056, all -> 0x0054 }
            java.lang.String r1 = "RSA/ECB/PKCS1Padding"
            javax.crypto.Cipher r1 = javax.crypto.Cipher.getInstance(r1)     // Catch:{ Exception -> 0x0056, all -> 0x0054 }
            r2 = 2
            r1.init(r2, r6)     // Catch:{ Exception -> 0x0056, all -> 0x0054 }
            byte[] r5 = com.ali.auth.third.core.util.Base64.decode(r5)     // Catch:{ Exception -> 0x0056, all -> 0x0054 }
            int r6 = r1.getBlockSize()     // Catch:{ Exception -> 0x0056, all -> 0x0054 }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0056, all -> 0x0054 }
            r2.<init>()     // Catch:{ Exception -> 0x0056, all -> 0x0054 }
            r3 = 0
        L_0x002c:
            int r4 = r5.length     // Catch:{ Exception -> 0x0052 }
            if (r3 >= r4) goto L_0x0040
            int r4 = r5.length     // Catch:{ Exception -> 0x0052 }
            int r4 = r4 - r3
            if (r4 >= r6) goto L_0x0036
            int r4 = r5.length     // Catch:{ Exception -> 0x0052 }
            int r4 = r4 - r3
            goto L_0x0037
        L_0x0036:
            r4 = r6
        L_0x0037:
            byte[] r4 = r1.doFinal(r5, r3, r4)     // Catch:{ Exception -> 0x0052 }
            r2.write(r4)     // Catch:{ Exception -> 0x0052 }
            int r3 = r3 + r6
            goto L_0x002c
        L_0x0040:
            java.lang.String r5 = new java.lang.String     // Catch:{ Exception -> 0x0052 }
            byte[] r6 = r2.toByteArray()     // Catch:{ Exception -> 0x0052 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x0052 }
            r2.close()     // Catch:{ IOException -> 0x004d }
            return r5
        L_0x004d:
            r6 = move-exception
            r6.printStackTrace()
            return r5
        L_0x0052:
            r5 = move-exception
            goto L_0x0058
        L_0x0054:
            r5 = move-exception
            goto L_0x0069
        L_0x0056:
            r5 = move-exception
            r2 = r0
        L_0x0058:
            r5.printStackTrace()     // Catch:{ all -> 0x0067 }
            if (r2 == 0) goto L_0x0065
            r2.close()     // Catch:{ IOException -> 0x0061 }
            goto L_0x0065
        L_0x0061:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0065:
            r5 = r0
            return r5
        L_0x0067:
            r5 = move-exception
            r0 = r2
        L_0x0069:
            if (r0 == 0) goto L_0x0073
            r0.close()     // Catch:{ IOException -> 0x006f }
            goto L_0x0073
        L_0x006f:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0073:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.auth.third.core.util.Rsa.decrypt(java.lang.String, java.lang.String):java.lang.String");
    }

    public static boolean doCheck(String str, String str2, String str3) {
        try {
            PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str3)));
            Signature instance = Signature.getInstance("SHA1WithRSA");
            instance.initVerify(generatePublic);
            instance.update(str.getBytes("utf-8"));
            return instance.verify(Base64.decode(str2));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0056 A[SYNTHETIC, Splitter:B:26:0x0056] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0064 A[SYNTHETIC, Splitter:B:35:0x0064] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String encrypt(java.lang.String r5, java.lang.String r6) {
        /*
            r0 = 0
            java.lang.String r1 = "RSA"
            java.security.PublicKey r6 = a(r1, r6)     // Catch:{ Exception -> 0x004f, all -> 0x004d }
            java.lang.String r1 = "RSA/ECB/PKCS1Padding"
            javax.crypto.Cipher r1 = javax.crypto.Cipher.getInstance(r1)     // Catch:{ Exception -> 0x004f, all -> 0x004d }
            r2 = 1
            r1.init(r2, r6)     // Catch:{ Exception -> 0x004f, all -> 0x004d }
            java.lang.String r6 = "UTF-8"
            byte[] r5 = r5.getBytes(r6)     // Catch:{ Exception -> 0x004f, all -> 0x004d }
            int r6 = r1.getBlockSize()     // Catch:{ Exception -> 0x004f, all -> 0x004d }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x004f, all -> 0x004d }
            r2.<init>()     // Catch:{ Exception -> 0x004f, all -> 0x004d }
            r3 = 0
        L_0x0021:
            int r4 = r5.length     // Catch:{ Exception -> 0x004b }
            if (r3 >= r4) goto L_0x0035
            int r4 = r5.length     // Catch:{ Exception -> 0x004b }
            int r4 = r4 - r3
            if (r4 >= r6) goto L_0x002b
            int r4 = r5.length     // Catch:{ Exception -> 0x004b }
            int r4 = r4 - r3
            goto L_0x002c
        L_0x002b:
            r4 = r6
        L_0x002c:
            byte[] r4 = r1.doFinal(r5, r3, r4)     // Catch:{ Exception -> 0x004b }
            r2.write(r4)     // Catch:{ Exception -> 0x004b }
            int r3 = r3 + r6
            goto L_0x0021
        L_0x0035:
            java.lang.String r5 = new java.lang.String     // Catch:{ Exception -> 0x004b }
            byte[] r6 = r2.toByteArray()     // Catch:{ Exception -> 0x004b }
            java.lang.String r6 = com.ali.auth.third.core.util.Base64.encode(r6)     // Catch:{ Exception -> 0x004b }
            r5.<init>(r6)     // Catch:{ Exception -> 0x004b }
            r2.close()     // Catch:{ IOException -> 0x0046 }
            return r5
        L_0x0046:
            r6 = move-exception
            r6.printStackTrace()
            return r5
        L_0x004b:
            r5 = move-exception
            goto L_0x0051
        L_0x004d:
            r5 = move-exception
            goto L_0x0062
        L_0x004f:
            r5 = move-exception
            r2 = r0
        L_0x0051:
            r5.printStackTrace()     // Catch:{ all -> 0x0060 }
            if (r2 == 0) goto L_0x005e
            r2.close()     // Catch:{ IOException -> 0x005a }
            goto L_0x005e
        L_0x005a:
            r5 = move-exception
            r5.printStackTrace()
        L_0x005e:
            r5 = r0
            return r5
        L_0x0060:
            r5 = move-exception
            r0 = r2
        L_0x0062:
            if (r0 == 0) goto L_0x006c
            r0.close()     // Catch:{ IOException -> 0x0068 }
            goto L_0x006c
        L_0x0068:
            r6 = move-exception
            r6.printStackTrace()
        L_0x006c:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.auth.third.core.util.Rsa.encrypt(java.lang.String, java.lang.String):java.lang.String");
    }

    public static String sign(String str, String str2) {
        try {
            PrivateKey generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(str2)));
            Signature instance = Signature.getInstance("SHA1WithRSA");
            instance.initSign(generatePrivate);
            instance.update(str.getBytes("utf-8"));
            return Base64.encode(instance.sign());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
