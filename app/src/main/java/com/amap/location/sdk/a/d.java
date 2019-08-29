package com.amap.location.sdk.a;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;

/* compiled from: Base64Util */
public class d {
    private static char[] a = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static byte[] b = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};

    public static String a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = bArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            int i2 = i + 1;
            byte b2 = bArr[i] & 255;
            if (i2 == length) {
                stringBuffer.append(a[b2 >>> 2]);
                stringBuffer.append(a[(b2 & 3) << 4]);
                stringBuffer.append("==");
                break;
            }
            int i3 = i2 + 1;
            byte b3 = bArr[i2] & 255;
            if (i3 == length) {
                stringBuffer.append(a[b2 >>> 2]);
                stringBuffer.append(a[((b2 & 3) << 4) | ((b3 & 240) >>> 4)]);
                stringBuffer.append(a[(b3 & 15) << 2]);
                stringBuffer.append("=");
                break;
            }
            int i4 = i3 + 1;
            byte b4 = bArr[i3] & 255;
            stringBuffer.append(a[b2 >>> 2]);
            stringBuffer.append(a[((b2 & 3) << 4) | ((b3 & 240) >>> 4)]);
            stringBuffer.append(a[((b3 & 15) << 2) | ((b4 & 192) >>> 6)]);
            stringBuffer.append(a[b4 & 63]);
            i = i4;
        }
        return stringBuffer.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0088 A[LOOP:0: B:1:0x000d->B:32:0x0088, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0094 A[EDGE_INSN: B:36:0x0094->B:33:0x0094 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0094 A[EDGE_INSN: B:38:0x0094->B:33:0x0094 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0094 A[EDGE_INSN: B:39:0x0094->B:33:0x0094 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0094 A[EDGE_INSN: B:41:0x0094->B:33:0x0094 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0021 A[LOOP:2: B:7:0x0021->B:10:0x002e, LOOP_START, PHI: r4 
      PHI: (r4v1 int) = (r4v0 int), (r4v9 int) binds: [B:6:0x001f, B:10:0x002e] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] a(java.lang.String r8) throws java.lang.Exception {
        /*
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.String r1 = "US-ASCII"
            byte[] r8 = r8.getBytes(r1)
            int r1 = r8.length
            r2 = 0
        L_0x000d:
            if (r2 >= r1) goto L_0x0094
        L_0x000f:
            byte[] r3 = b
            int r4 = r2 + 1
            byte r2 = r8[r2]
            byte r2 = r3[r2]
            r3 = -1
            if (r4 >= r1) goto L_0x001f
            if (r2 == r3) goto L_0x001d
            goto L_0x001f
        L_0x001d:
            r2 = r4
            goto L_0x000f
        L_0x001f:
            if (r2 == r3) goto L_0x0094
        L_0x0021:
            byte[] r5 = b
            int r6 = r4 + 1
            byte r4 = r8[r4]
            byte r4 = r5[r4]
            if (r6 >= r1) goto L_0x0030
            if (r4 == r3) goto L_0x002e
            goto L_0x0030
        L_0x002e:
            r4 = r6
            goto L_0x0021
        L_0x0030:
            if (r4 == r3) goto L_0x0094
            int r2 = r2 << 2
            r5 = r4 & 48
            int r5 = r5 >>> 4
            r2 = r2 | r5
            char r2 = (char) r2
            r0.append(r2)
        L_0x003d:
            int r2 = r6 + 1
            byte r5 = r8[r6]
            r6 = 61
            if (r5 != r6) goto L_0x0050
            java.lang.String r8 = r0.toString()
            java.lang.String r0 = "iso8859-1"
            byte[] r8 = r8.getBytes(r0)
            return r8
        L_0x0050:
            byte[] r7 = b
            byte r5 = r7[r5]
            if (r2 >= r1) goto L_0x005b
            if (r5 == r3) goto L_0x0059
            goto L_0x005b
        L_0x0059:
            r6 = r2
            goto L_0x003d
        L_0x005b:
            if (r5 == r3) goto L_0x0094
            r4 = r4 & 15
            int r4 = r4 << 4
            r7 = r5 & 60
            int r7 = r7 >>> 2
            r4 = r4 | r7
            char r4 = (char) r4
            r0.append(r4)
        L_0x006a:
            int r4 = r2 + 1
            byte r2 = r8[r2]
            if (r2 != r6) goto L_0x007b
            java.lang.String r8 = r0.toString()
            java.lang.String r0 = "iso8859-1"
            byte[] r8 = r8.getBytes(r0)
            return r8
        L_0x007b:
            byte[] r7 = b
            byte r2 = r7[r2]
            if (r4 >= r1) goto L_0x0086
            if (r2 == r3) goto L_0x0084
            goto L_0x0086
        L_0x0084:
            r2 = r4
            goto L_0x006a
        L_0x0086:
            if (r2 == r3) goto L_0x0094
            r3 = r5 & 3
            int r3 = r3 << 6
            r2 = r2 | r3
            char r2 = (char) r2
            r0.append(r2)
            r2 = r4
            goto L_0x000d
        L_0x0094:
            java.lang.String r8 = r0.toString()
            java.lang.String r0 = "iso8859-1"
            byte[] r8 = r8.getBytes(r0)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.sdk.a.d.a(java.lang.String):byte[]");
    }
}
