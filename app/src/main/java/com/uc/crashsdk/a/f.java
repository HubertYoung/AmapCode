package com.uc.crashsdk.a;

import com.uc.crashsdk.a;
import com.uc.crashsdk.p;

/* compiled from: ProGuard */
public final class f {
    private static boolean a = true;
    /* access modifiers changed from: private */
    public static final Object b = new Object();
    private static boolean c = false;
    private static String d = "hsdk";
    private static String e = "alid ";
    /* access modifiers changed from: private */
    public static String f = null;

    public static void a() {
        i.a(0, new g(), 55000);
    }

    public static String b() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("inv");
            sb.append(e);
            sb.append("cras");
            sb.append(d);
            return sb.toString();
        } catch (Throwable th) {
            a.a(th, true);
            return "";
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0063 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean b(boolean r11) {
        /*
            r10 = 3
            r9 = 2
            r1 = 1
            r2 = 0
            boolean r0 = c
            if (r0 == 0) goto L_0x0009
        L_0x0008:
            return r2
        L_0x0009:
            if (r11 != 0) goto L_0x0067
            r0 = r1
        L_0x000c:
            c = r0
            java.lang.String r0 = com.uc.crashsdk.a.b
            java.lang.String r3 = "2.0"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0020
            r0 = 536870912(0x20000000, float:1.0842022E-19)
            boolean r0 = com.uc.crashsdk.b.b(r0)
            if (r0 != 0) goto L_0x0008
        L_0x0020:
            java.lang.String r6 = com.uc.crashsdk.b.h()
            java.lang.String r5 = com.uc.crashsdk.a.d.a(r6)
            r0 = 0
            r3 = 0
            boolean r7 = com.uc.crashsdk.a.h.b(r5)
            if (r7 == 0) goto L_0x00f5
            java.lang.String r7 = " "
            java.lang.String[] r7 = r5.split(r7)
            int r5 = r7.length
            if (r5 != r10) goto L_0x00f5
            r5 = r7[r2]
            r0 = r7[r1]
            long r3 = com.uc.crashsdk.a.h.c(r0)
            r0 = r7[r9]
            long r7 = com.uc.crashsdk.a.h.c(r0)
            int r0 = (int) r7
        L_0x0049:
            a = r1
            long r7 = java.lang.System.currentTimeMillis()
            long r3 = r7 - r3
            r7 = 259200000(0xf731400, double:1.280618154E-315)
            int r3 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r3 >= 0) goto L_0x007f
            java.lang.String r3 = "o"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x0069
            r3 = r2
        L_0x0061:
            if (r3 == 0) goto L_0x0065
            if (r11 == 0) goto L_0x0081
        L_0x0065:
            r2 = r1
            goto L_0x0008
        L_0x0067:
            r0 = r2
            goto L_0x000c
        L_0x0069:
            java.lang.String r3 = "2"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x0075
            a = r2
            r3 = r2
            goto L_0x0061
        L_0x0075:
            java.lang.String r3 = "1"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x007f
            a = r2
        L_0x007f:
            r3 = r1
            goto L_0x0061
        L_0x0081:
            int r3 = android.os.Process.myPid()
            if (r0 == r3) goto L_0x0008
            java.lang.String r0 = "per"
            f = r0
            java.lang.String r0 = h()
            if (r0 == 0) goto L_0x00e7
            java.lang.String r3 = "retcode="
            boolean r3 = r0.contains(r3)
            if (r3 == 0) goto L_0x00e7
            java.lang.String r3 = "retcode=0"
            boolean r0 = r0.contains(r3)
            if (r0 == 0) goto L_0x00cf
            a = r1
            java.lang.String r0 = "o"
            java.lang.String r3 = "aus"
            f = r3
        L_0x00a9:
            java.util.Locale r3 = java.util.Locale.US
            java.lang.String r4 = "%s %d %d"
            java.lang.Object[] r5 = new java.lang.Object[r10]
            r5[r2] = r0
            long r7 = java.lang.System.currentTimeMillis()
            java.lang.Long r0 = java.lang.Long.valueOf(r7)
            r5[r1] = r0
            int r0 = android.os.Process.myPid()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r5[r9] = r0
            java.lang.String r0 = java.lang.String.format(r3, r4, r5)
            com.uc.crashsdk.a.d.a(r6, r0)
            r2 = r1
            goto L_0x0008
        L_0x00cf:
            a = r2
            java.lang.String r0 = "1"
            boolean r0 = r0.equals(r5)
            if (r0 == 0) goto L_0x00e0
            java.lang.String r0 = "2"
            java.lang.String r3 = "auf2"
            f = r3
            goto L_0x00a9
        L_0x00e0:
            java.lang.String r0 = "1"
            java.lang.String r3 = "auf1"
            f = r3
            goto L_0x00a9
        L_0x00e7:
            if (r0 != 0) goto L_0x00ef
            java.lang.String r0 = "ner"
            f = r0
            goto L_0x0008
        L_0x00ef:
            java.lang.String r0 = "ser"
            f = r0
            goto L_0x0008
        L_0x00f5:
            r5 = r0
            r0 = r2
            goto L_0x0049
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.f.b(boolean):boolean");
    }

    public static byte[] c() {
        return new byte[]{6, 0, 23, 8};
    }

    private static String h() {
        byte[] bArr;
        byte[] bArr2;
        StringBuilder sb = new StringBuilder();
        a(sb, "platform", p.e());
        a(sb, "pkgname", a.a);
        a(sb, "process", com.uc.crashsdk.f.d());
        a(sb, "version", a.a());
        a(sb, "os", "android");
        String sb2 = sb.toString();
        byte[] bArr3 = new byte[16];
        e.a(bArr3, 0, k.d());
        e.a(bArr3, 4, e.c());
        e.a(bArr3, 8, c());
        e.a(bArr3, 12, a.c());
        try {
            bArr = e.a(sb2.getBytes(), bArr3, true);
        } catch (Throwable th) {
            a.a(th, false);
            bArr = null;
        }
        if (bArr == null) {
            return null;
        }
        byte[] a2 = e.a((String) "https://woodpecker.uc.cn/api/crashsdk/validate", bArr);
        if (a2 == null) {
            return null;
        }
        try {
            bArr2 = e.a(a2, bArr3, false);
        } catch (Throwable th2) {
            a.a(th2, false);
            bArr2 = null;
        }
        if (bArr2 != null) {
            return new String(bArr2);
        }
        return null;
    }

    private static StringBuilder a(StringBuilder sb, String str, String str2) {
        if (sb.length() > 0) {
            sb.append("`");
        }
        sb.append(str).append("=").append(str2);
        return sb;
    }

    public static boolean d() {
        try {
            b(true);
        } catch (Throwable th) {
        }
        return a;
    }
}
