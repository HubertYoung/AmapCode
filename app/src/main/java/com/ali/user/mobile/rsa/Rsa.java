package com.ali.user.mobile.rsa;

import android.content.Context;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.rpc.vo.mobilegw.RSAPKeyResult;

public class Rsa {
    private static RSAHandler a;
    private static RSAPKeyResult b;

    public static RSAPKeyResult a(Context context) {
        if (a == null) {
            if (AliuserLoginContext.c() != null) {
                a = AliuserLoginContext.c();
            } else {
                a = new AliuserRSAHandler(context);
            }
        }
        StringBuilder sb = new StringBuilder("sRsaHandler:");
        sb.append(a);
        AliUserLog.c("RSAUtil", sb.toString());
        if (b == null) {
            b = a.a();
        }
        return b;
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x006c A[SYNTHETIC, Splitter:B:25:0x006c] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x007c A[SYNTHETIC, Splitter:B:33:0x007c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r5, java.lang.String r6) {
        /*
            r0 = 0
            java.lang.String r1 = "RSA"
            byte[] r6 = com.ali.user.mobile.util.Base64.a(r6)     // Catch:{ Exception -> 0x005e, all -> 0x005c }
            java.security.spec.X509EncodedKeySpec r2 = new java.security.spec.X509EncodedKeySpec     // Catch:{ Exception -> 0x005e, all -> 0x005c }
            r2.<init>(r6)     // Catch:{ Exception -> 0x005e, all -> 0x005c }
            java.security.KeyFactory r6 = java.security.KeyFactory.getInstance(r1)     // Catch:{ Exception -> 0x005e, all -> 0x005c }
            java.security.PublicKey r6 = r6.generatePublic(r2)     // Catch:{ Exception -> 0x005e, all -> 0x005c }
            java.lang.String r1 = "RSA/ECB/PKCS1Padding"
            javax.crypto.Cipher r1 = javax.crypto.Cipher.getInstance(r1)     // Catch:{ Exception -> 0x005e, all -> 0x005c }
            r2 = 1
            r1.init(r2, r6)     // Catch:{ Exception -> 0x005e, all -> 0x005c }
            java.lang.String r6 = "UTF-8"
            byte[] r5 = r5.getBytes(r6)     // Catch:{ Exception -> 0x005e, all -> 0x005c }
            int r6 = r1.getBlockSize()     // Catch:{ Exception -> 0x005e, all -> 0x005c }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x005e, all -> 0x005c }
            r2.<init>()     // Catch:{ Exception -> 0x005e, all -> 0x005c }
            r3 = 0
        L_0x002e:
            int r4 = r5.length     // Catch:{ Exception -> 0x005a }
            if (r3 >= r4) goto L_0x0042
            int r4 = r5.length     // Catch:{ Exception -> 0x005a }
            int r4 = r4 - r3
            if (r4 >= r6) goto L_0x0038
            int r4 = r5.length     // Catch:{ Exception -> 0x005a }
            int r4 = r4 - r3
            goto L_0x0039
        L_0x0038:
            r4 = r6
        L_0x0039:
            byte[] r4 = r1.doFinal(r5, r3, r4)     // Catch:{ Exception -> 0x005a }
            r2.write(r4)     // Catch:{ Exception -> 0x005a }
            int r3 = r3 + r6
            goto L_0x002e
        L_0x0042:
            java.lang.String r5 = new java.lang.String     // Catch:{ Exception -> 0x005a }
            byte[] r6 = r2.toByteArray()     // Catch:{ Exception -> 0x005a }
            java.lang.String r6 = com.ali.user.mobile.util.Base64.a(r6)     // Catch:{ Exception -> 0x005a }
            r5.<init>(r6)     // Catch:{ Exception -> 0x005a }
            r2.close()     // Catch:{ IOException -> 0x0053 }
            goto L_0x0077
        L_0x0053:
            r6 = move-exception
            java.lang.String r0 = "Aliuser.Rsa"
            com.ali.user.mobile.log.AliUserLog.b(r0, r6)
            goto L_0x0077
        L_0x005a:
            r5 = move-exception
            goto L_0x0060
        L_0x005c:
            r5 = move-exception
            goto L_0x007a
        L_0x005e:
            r5 = move-exception
            r2 = r0
        L_0x0060:
            java.lang.String r6 = "Rsa"
            java.lang.String r1 = "encrypt exception"
            com.ali.user.mobile.log.AliUserLog.a(r6, r1, r5)     // Catch:{ all -> 0x0078 }
            com.ali.user.mobile.log.LogAgent.a(r5)     // Catch:{ all -> 0x0078 }
            if (r2 == 0) goto L_0x0076
            r2.close()     // Catch:{ IOException -> 0x0070 }
            goto L_0x0076
        L_0x0070:
            r5 = move-exception
            java.lang.String r6 = "Aliuser.Rsa"
            com.ali.user.mobile.log.AliUserLog.b(r6, r5)
        L_0x0076:
            r5 = r0
        L_0x0077:
            return r5
        L_0x0078:
            r5 = move-exception
            r0 = r2
        L_0x007a:
            if (r0 == 0) goto L_0x0086
            r0.close()     // Catch:{ IOException -> 0x0080 }
            goto L_0x0086
        L_0x0080:
            r6 = move-exception
            java.lang.String r0 = "Aliuser.Rsa"
            com.ali.user.mobile.log.AliUserLog.b(r0, r6)
        L_0x0086:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.rsa.Rsa.a(java.lang.String, java.lang.String):java.lang.String");
    }
}
