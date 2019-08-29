package com.alipay.sdk.packet;

import com.alipay.sdk.cons.a;
import com.alipay.sdk.encrypt.c;
import com.alipay.sdk.encrypt.d;
import com.alipay.sdk.util.l;
import java.util.Locale;

public final class e {
    private boolean a;
    private String b = l.d();

    public e(boolean z) {
        this.a = z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0053, code lost:
        r6 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0055, code lost:
        r4 = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0053 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:4:0x0009] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x005b A[SYNTHETIC, Splitter:B:25:0x005b] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0063 A[SYNTHETIC, Splitter:B:33:0x0063] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0069 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.alipay.sdk.packet.b a(com.alipay.sdk.packet.c r6) {
        /*
            r5 = this;
            r0 = 0
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x005f, all -> 0x0057 }
            byte[] r2 = r6.b     // Catch:{ Exception -> 0x005f, all -> 0x0057 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x005f, all -> 0x0057 }
            r2 = 5
            byte[] r3 = new byte[r2]     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            r1.read(r3)     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            java.lang.String r4 = new java.lang.String     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            r4.<init>(r3)     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            int r3 = java.lang.Integer.parseInt(r4)     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            byte[] r3 = new byte[r3]     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            r1.read(r3)     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            java.lang.String r4 = new java.lang.String     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            r4.<init>(r3)     // Catch:{ Exception -> 0x0055, all -> 0x0053 }
            byte[] r2 = new byte[r2]     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            r1.read(r2)     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            java.lang.String r3 = new java.lang.String     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            int r2 = java.lang.Integer.parseInt(r3)     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            if (r2 <= 0) goto L_0x004e
            byte[] r2 = new byte[r2]     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            r1.read(r2)     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            boolean r3 = r5.a     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            if (r3 == 0) goto L_0x0040
            java.lang.String r3 = r5.b     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            byte[] r2 = com.alipay.sdk.encrypt.e.b(r3, r2)     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
        L_0x0040:
            boolean r6 = r6.a     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            if (r6 == 0) goto L_0x0048
            byte[] r2 = com.alipay.sdk.encrypt.c.b(r2)     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
        L_0x0048:
            java.lang.String r6 = new java.lang.String     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            r6.<init>(r2)     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            goto L_0x004f
        L_0x004e:
            r6 = r0
        L_0x004f:
            r1.close()     // Catch:{ Exception -> 0x0067 }
            goto L_0x0067
        L_0x0053:
            r6 = move-exception
            goto L_0x0059
        L_0x0055:
            r4 = r0
            goto L_0x0061
        L_0x0057:
            r6 = move-exception
            r1 = r0
        L_0x0059:
            if (r1 == 0) goto L_0x005e
            r1.close()     // Catch:{ Exception -> 0x005e }
        L_0x005e:
            throw r6
        L_0x005f:
            r1 = r0
            r4 = r1
        L_0x0061:
            if (r1 == 0) goto L_0x0066
            r1.close()     // Catch:{ Exception -> 0x0066 }
        L_0x0066:
            r6 = r0
        L_0x0067:
            if (r4 != 0) goto L_0x006c
            if (r6 != 0) goto L_0x006c
            return r0
        L_0x006c:
            com.alipay.sdk.packet.b r0 = new com.alipay.sdk.packet.b
            r0.<init>(r4, r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.packet.e.a(com.alipay.sdk.packet.c):com.alipay.sdk.packet.b");
    }

    private static byte[] a(String str, String str2) {
        return d.a(str, str2);
    }

    private static byte[] a(String str, byte[] bArr) {
        return com.alipay.sdk.encrypt.e.a(str, bArr);
    }

    private static byte[] b(String str, byte[] bArr) {
        return com.alipay.sdk.encrypt.e.b(str, bArr);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(13:3|4|5|6|7|(3:11|9|8)|47|12|13|14|15|16|45) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0043 */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0053 A[SYNTHETIC, Splitter:B:25:0x0053] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0058 A[SYNTHETIC, Splitter:B:29:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0060 A[SYNTHETIC, Splitter:B:37:0x0060] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0065 A[SYNTHETIC, Splitter:B:41:0x0065] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] a(byte[]... r9) {
        /*
            int r0 = r9.length
            r1 = 0
            if (r0 != 0) goto L_0x0005
            return r1
        L_0x0005:
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x005c, all -> 0x004e }
            r0.<init>()     // Catch:{ Exception -> 0x005c, all -> 0x004e }
            java.io.DataOutputStream r2 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x004c, all -> 0x0049 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x004c, all -> 0x0049 }
            r3 = 0
            r4 = 0
        L_0x0011:
            int r5 = r9.length     // Catch:{ Exception -> 0x005e, all -> 0x0047 }
            if (r4 >= r5) goto L_0x0039
            r5 = r9[r4]     // Catch:{ Exception -> 0x005e, all -> 0x0047 }
            int r5 = r5.length     // Catch:{ Exception -> 0x005e, all -> 0x0047 }
            java.util.Locale r6 = java.util.Locale.getDefault()     // Catch:{ Exception -> 0x005e, all -> 0x0047 }
            java.lang.String r7 = "%05d"
            r8 = 1
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x005e, all -> 0x0047 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Exception -> 0x005e, all -> 0x0047 }
            r8[r3] = r5     // Catch:{ Exception -> 0x005e, all -> 0x0047 }
            java.lang.String r5 = java.lang.String.format(r6, r7, r8)     // Catch:{ Exception -> 0x005e, all -> 0x0047 }
            byte[] r5 = r5.getBytes()     // Catch:{ Exception -> 0x005e, all -> 0x0047 }
            r2.write(r5)     // Catch:{ Exception -> 0x005e, all -> 0x0047 }
            r5 = r9[r4]     // Catch:{ Exception -> 0x005e, all -> 0x0047 }
            r2.write(r5)     // Catch:{ Exception -> 0x005e, all -> 0x0047 }
            int r4 = r4 + 1
            goto L_0x0011
        L_0x0039:
            r2.flush()     // Catch:{ Exception -> 0x005e, all -> 0x0047 }
            byte[] r9 = r0.toByteArray()     // Catch:{ Exception -> 0x005e, all -> 0x0047 }
            r0.close()     // Catch:{ Exception -> 0x0043 }
        L_0x0043:
            r2.close()     // Catch:{ Exception -> 0x0069 }
            goto L_0x0069
        L_0x0047:
            r9 = move-exception
            goto L_0x0051
        L_0x0049:
            r9 = move-exception
            r2 = r1
            goto L_0x0051
        L_0x004c:
            r2 = r1
            goto L_0x005e
        L_0x004e:
            r9 = move-exception
            r0 = r1
            r2 = r0
        L_0x0051:
            if (r0 == 0) goto L_0x0056
            r0.close()     // Catch:{ Exception -> 0x0056 }
        L_0x0056:
            if (r2 == 0) goto L_0x005b
            r2.close()     // Catch:{ Exception -> 0x005b }
        L_0x005b:
            throw r9
        L_0x005c:
            r0 = r1
            r2 = r0
        L_0x005e:
            if (r0 == 0) goto L_0x0063
            r0.close()     // Catch:{ Exception -> 0x0063 }
        L_0x0063:
            if (r2 == 0) goto L_0x0068
            r2.close()     // Catch:{ Exception -> 0x0068 }
        L_0x0068:
            r9 = r1
        L_0x0069:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.packet.e.a(byte[][]):byte[]");
    }

    private static String a(int i) {
        return String.format(Locale.getDefault(), "%05d", new Object[]{Integer.valueOf(i)});
    }

    private static int a(String str) {
        return Integer.parseInt(str);
    }

    public final c a(b bVar, boolean z) {
        byte[] bArr;
        byte[] bytes = bVar.a.getBytes();
        byte[] bytes2 = bVar.b.getBytes();
        if (z) {
            try {
                bytes2 = c.a(bytes2);
            } catch (Exception unused) {
                z = false;
            }
        }
        if (this.a) {
            bArr = a(bytes, d.a(this.b, a.c), com.alipay.sdk.encrypt.e.a(this.b, bytes2));
        } else {
            bArr = a(bytes, bytes2);
        }
        return new c(z, bArr);
    }
}
