package com.amap.location.b.f;

import android.util.Base64;
import com.amap.location.security.Core;

/* compiled from: AESUtil */
public class a {
    public static byte[] a(String str, byte[] bArr) throws Exception {
        return Core.cole(bArr, a(str), a());
    }

    public static byte[] a(String str) throws Exception {
        if (str == null) {
            str = "";
        }
        StringBuffer stringBuffer = new StringBuffer(16);
        stringBuffer.append(str);
        while (stringBuffer.length() < 16) {
            stringBuffer.append("0");
        }
        if (stringBuffer.length() > 16) {
            stringBuffer.setLength(16);
        }
        return b(stringBuffer.toString());
    }

    private static byte[] a() {
        String[] split = new StringBuffer("16,16,18,77,15,911,121,77,121,911,38,77,911,99,86,67,611,96,48,77,84,911,38,67,021,301,86,67,611,98,48,77,511,77,48,97,511,58,48,97,511,84,501,87,511,96,48,77,221,911,38,77,121,37,86,67,25,301,86,67,021,96,86,67,021,701,86,67,35,56,86,67,611,37,221,87").reverse().toString().split(",");
        byte[] bArr = new byte[split.length];
        for (int i = 0; i < split.length; i++) {
            bArr[i] = Byte.parseByte(split[i]);
        }
        String[] split2 = new StringBuffer(new String(Base64.decode(bArr, 2))).reverse().toString().split(",");
        byte[] bArr2 = new byte[split2.length];
        for (int i2 = 0; i2 < split2.length; i2++) {
            bArr2[i2] = Byte.parseByte(split2[i2]);
        }
        return bArr2;
    }

    /* JADX WARNING: type inference failed for: r2v2, types: [int] */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] b(@android.support.annotation.NonNull java.lang.String r4) throws java.security.NoSuchAlgorithmException {
        /*
            java.lang.String r0 = "SHA1"
            java.security.MessageDigest r0 = java.security.MessageDigest.getInstance(r0)
            byte[] r4 = r4.getBytes()
            r0.update(r4)
            byte[] r4 = r0.digest()
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            java.lang.String r1 = ""
            r0.<init>(r1)
            r1 = 0
        L_0x0019:
            int r2 = r4.length
            r3 = 16
            if (r1 >= r2) goto L_0x0035
            byte r2 = r4[r1]
            if (r2 >= 0) goto L_0x0024
            int r2 = r2 + 256
        L_0x0024:
            if (r2 >= r3) goto L_0x002b
            java.lang.String r3 = "0"
            r0.append(r3)
        L_0x002b:
            java.lang.String r2 = java.lang.Integer.toHexString(r2)
            r0.append(r2)
            int r1 = r1 + 1
            goto L_0x0019
        L_0x0035:
            int r4 = r0.length()
            if (r4 <= r3) goto L_0x003e
            r0.setLength(r3)
        L_0x003e:
            java.lang.String r4 = r0.toString()
            byte[] r4 = r4.getBytes()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.b.f.a.b(java.lang.String):byte[]");
    }
}
