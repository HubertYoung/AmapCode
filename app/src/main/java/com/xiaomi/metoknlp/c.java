package com.xiaomi.metoknlp;

import com.xiaomi.metoknlp.a.b;
import com.xiaomi.metoknlp.a.f;
import java.util.HashMap;
import java.util.Map;

public final class c {
    private static String a;

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0045, code lost:
        if (a == null) goto L_0x0047;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a() {
        /*
            java.lang.String r0 = com.xiaomi.metoknlp.a.e.b()
            java.lang.String r1 = com.xiaomi.metoknlp.a.e.c()
            java.lang.String r2 = com.xiaomi.metoknlp.a.e.g()
            int r3 = com.xiaomi.metoknlp.a.e.e()
            int r4 = com.xiaomi.metoknlp.a.e.f()
            r5 = 0
            if (r0 == 0) goto L_0x0071
            boolean r6 = r0.isEmpty()
            if (r6 != 0) goto L_0x0071
            if (r1 == 0) goto L_0x0071
            boolean r6 = r1.isEmpty()
            if (r6 == 0) goto L_0x0026
            return r5
        L_0x0026:
            java.lang.String r6 = a
            if (r6 != 0) goto L_0x0048
            java.lang.String r6 = com.xiaomi.metoknlp.a.e.a()
            if (r6 == 0) goto L_0x0047
            boolean r7 = r6.isEmpty()
            if (r7 == 0) goto L_0x0037
            return r5
        L_0x0037:
            java.lang.String r6 = com.xiaomi.metoknlp.a.e.a(r6)
            if (r6 == 0) goto L_0x0043
            java.lang.String r6 = com.xiaomi.metoknlp.a.e.a(r6)
            a = r6
        L_0x0043:
            java.lang.String r6 = a
            if (r6 != 0) goto L_0x0048
        L_0x0047:
            return r5
        L_0x0048:
            if (r3 < 0) goto L_0x004c
            if (r4 >= 0) goto L_0x0050
        L_0x004c:
            r3 = 999(0x3e7, float:1.4E-42)
            r4 = 99
        L_0x0050:
            java.lang.String r5 = "%s__%s__%d__%d__%s"
            r6 = 5
            java.lang.Object[] r6 = new java.lang.Object[r6]
            r7 = 0
            r6[r7] = r0
            r0 = 1
            r6[r0] = r1
            r0 = 2
            java.lang.Integer r1 = java.lang.Integer.valueOf(r3)
            r6[r0] = r1
            r0 = 3
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)
            r6[r0] = r1
            r0 = 4
            r6[r0] = r2
            java.lang.String r0 = java.lang.String.format(r5, r6)
            return r0
        L_0x0071:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.metoknlp.c.a():java.lang.String");
    }

    public static String a(String str, String str2) {
        String str3;
        String a2 = f.a();
        String a3 = a();
        if (a3 == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer(a2);
        stringBuffer.append("/base/profile/metoknlpsdk/");
        stringBuffer.append(str);
        stringBuffer.append("/");
        stringBuffer.append(a3);
        stringBuffer.append("__");
        stringBuffer.append(str2);
        String stringBuffer2 = stringBuffer.toString();
        Map b = b();
        try {
            str3 = b.a(stringBuffer2, b);
        } catch (Exception unused) {
            str3 = null;
        }
        b.clear();
        return str3;
    }

    private static Map b() {
        String a2 = a();
        HashMap hashMap = new HashMap();
        hashMap.put("CCPVER", a);
        hashMap.put("CCPINF", a2);
        return hashMap;
    }
}
