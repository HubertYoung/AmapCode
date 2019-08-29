package com.xiaomi.channel.commonutils.android;

import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.reflect.a;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class f {
    private static int a = 0;
    private static int b = -1;
    private static int c = -1;
    private static Map<String, h> d;

    public static h a(String str) {
        h c2 = c(str);
        return c2 == null ? h.Global : c2;
    }

    public static synchronized boolean a() {
        synchronized (f.class) {
            try {
                return c() == 1;
            }
        }
    }

    private static String b(String str) {
        try {
            return (String) a.a((String) "android.os.SystemProperties", (String) "get", str, "");
        } catch (Exception e) {
            b.a((Throwable) e);
        } catch (Throwable unused) {
        }
        return null;
    }

    public static synchronized boolean b() {
        boolean z;
        synchronized (f.class) {
            z = c() == 2;
        }
        return z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0027 A[Catch:{ Throwable -> 0x002c }] */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0028 A[Catch:{ Throwable -> 0x002c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized int c() {
        /*
            java.lang.Class<com.xiaomi.channel.commonutils.android.f> r0 = com.xiaomi.channel.commonutils.android.f.class
            monitor-enter(r0)
            int r1 = a     // Catch:{ all -> 0x004b }
            if (r1 != 0) goto L_0x0047
            r1 = 0
            java.lang.String r2 = "ro.miui.ui.version.code"
            java.lang.String r2 = b(r2)     // Catch:{ Throwable -> 0x002c }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x002c }
            r3 = 1
            if (r2 == 0) goto L_0x0024
            java.lang.String r2 = "ro.miui.ui.version.name"
            java.lang.String r2 = b(r2)     // Catch:{ Throwable -> 0x002c }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x002c }
            if (r2 != 0) goto L_0x0022
            goto L_0x0024
        L_0x0022:
            r2 = 0
            goto L_0x0025
        L_0x0024:
            r2 = 1
        L_0x0025:
            if (r2 == 0) goto L_0x0028
            goto L_0x0029
        L_0x0028:
            r3 = 2
        L_0x0029:
            a = r3     // Catch:{ Throwable -> 0x002c }
            goto L_0x0034
        L_0x002c:
            r2 = move-exception
            java.lang.String r3 = "get isMIUI failed"
            com.xiaomi.channel.commonutils.logger.b.a(r3, r2)     // Catch:{ all -> 0x004b }
            a = r1     // Catch:{ all -> 0x004b }
        L_0x0034:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x004b }
            java.lang.String r2 = "isMIUI's value is: "
            r1.<init>(r2)     // Catch:{ all -> 0x004b }
            int r2 = a     // Catch:{ all -> 0x004b }
            r1.append(r2)     // Catch:{ all -> 0x004b }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x004b }
            com.xiaomi.channel.commonutils.logger.b.b(r1)     // Catch:{ all -> 0x004b }
        L_0x0047:
            int r1 = a     // Catch:{ all -> 0x004b }
            monitor-exit(r0)
            return r1
        L_0x004b:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.channel.commonutils.android.f.c():int");
    }

    private static h c(String str) {
        f();
        return d.get(str.toUpperCase());
    }

    public static String d() {
        String a2 = i.a("ro.miui.region", "");
        if (TextUtils.isEmpty(a2)) {
            a2 = i.a("ro.product.locale.region", "");
        }
        if (TextUtils.isEmpty(a2)) {
            a2 = i.a("persist.sys.country", "");
        }
        return TextUtils.isEmpty(a2) ? Locale.getDefault().getCountry() : a2;
    }

    public static boolean e() {
        if (c < 0) {
            if (h.Europe.name().equalsIgnoreCase(a(d()).name())) {
                c = 0;
            } else {
                c = 1;
            }
        }
        return c > 0;
    }

    private static void f() {
        if (d == null) {
            HashMap hashMap = new HashMap();
            d = hashMap;
            hashMap.put("CN", h.China);
            d.put("FI", h.Europe);
            d.put("SE", h.Europe);
            d.put("NO", h.Europe);
            d.put("FO", h.Europe);
            d.put("EE", h.Europe);
            d.put("LV", h.Europe);
            d.put("LT", h.Europe);
            d.put("BY", h.Europe);
            d.put("MD", h.Europe);
            d.put("UA", h.Europe);
            d.put("PL", h.Europe);
            d.put("CZ", h.Europe);
            d.put("SK", h.Europe);
            d.put("HU", h.Europe);
            d.put("DE", h.Europe);
            d.put("AT", h.Europe);
            d.put("CH", h.Europe);
            d.put("LI", h.Europe);
            d.put("GB", h.Europe);
            d.put("IE", h.Europe);
            d.put("NL", h.Europe);
            d.put("BE", h.Europe);
            d.put("LU", h.Europe);
            d.put("FR", h.Europe);
            d.put("RO", h.Europe);
            d.put("BG", h.Europe);
            d.put("RS", h.Europe);
            d.put("MK", h.Europe);
            d.put("AL", h.Europe);
            d.put("GR", h.Europe);
            d.put("SI", h.Europe);
            d.put("HR", h.Europe);
            d.put("IT", h.Europe);
            d.put("SM", h.Europe);
            d.put("MT", h.Europe);
            d.put("ES", h.Europe);
            d.put("PT", h.Europe);
            d.put("AD", h.Europe);
            d.put("CY", h.Europe);
            d.put("DK", h.Europe);
        }
    }
}
