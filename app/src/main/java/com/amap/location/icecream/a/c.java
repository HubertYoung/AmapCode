package com.amap.location.icecream.a;

import android.content.Context;
import android.content.SharedPreferences;

/* compiled from: SPUtil */
public class c {
    private static SharedPreferences a;

    private static void c(Context context) {
        if (a == null) {
            a = context.getSharedPreferences("location_icecream", 0);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(9:2|3|4|5|6|7|8|(1:10)|11) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0013 */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0019  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.lang.String a(android.content.Context r4) {
        /*
            java.lang.Class<com.amap.location.icecream.a.c> r0 = com.amap.location.icecream.a.c.class
            monitor-enter(r0)
            c(r4)     // Catch:{ all -> 0x001d }
            java.lang.String r4 = ""
            android.content.SharedPreferences r1 = a     // Catch:{ Throwable -> 0x0013 }
            java.lang.String r2 = "app_black_list"
            java.lang.String r3 = ""
            java.lang.String r1 = r1.getString(r2, r3)     // Catch:{ Throwable -> 0x0013 }
            r4 = r1
        L_0x0013:
            boolean r1 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x001d }
            if (r1 == 0) goto L_0x001b
            java.lang.String r4 = ""
        L_0x001b:
            monitor-exit(r0)
            return r4
        L_0x001d:
            r4 = move-exception
            monitor-exit(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.icecream.a.c.a(android.content.Context):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0065, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void a(android.content.Context r3, java.lang.String r4, java.lang.String r5) {
        /*
            java.lang.Class<com.amap.location.icecream.a.c> r0 = com.amap.location.icecream.a.c.class
            monitor-enter(r0)
            c(r3)     // Catch:{ all -> 0x0068 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0066 }
            r3.<init>()     // Catch:{ Throwable -> 0x0066 }
            r3.append(r4)     // Catch:{ Throwable -> 0x0066 }
            java.lang.String r4 = "_"
            r3.append(r4)     // Catch:{ Throwable -> 0x0066 }
            r3.append(r5)     // Catch:{ Throwable -> 0x0066 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0066 }
            android.content.SharedPreferences r4 = a     // Catch:{ Throwable -> 0x0066 }
            java.lang.String r1 = "app_black_list"
            java.lang.String r2 = ""
            java.lang.String r4 = r4.getString(r1, r2)     // Catch:{ Throwable -> 0x0066 }
            boolean r1 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x0066 }
            if (r1 == 0) goto L_0x003b
            android.content.SharedPreferences r4 = a     // Catch:{ Throwable -> 0x0066 }
            android.content.SharedPreferences$Editor r4 = r4.edit()     // Catch:{ Throwable -> 0x0066 }
            java.lang.String r5 = "app_black_list"
            android.content.SharedPreferences$Editor r3 = r4.putString(r5, r3)     // Catch:{ Throwable -> 0x0066 }
            r3.apply()     // Catch:{ Throwable -> 0x0066 }
            monitor-exit(r0)
            return
        L_0x003b:
            boolean r3 = r4.contains(r3)     // Catch:{ Throwable -> 0x0066 }
            if (r3 != 0) goto L_0x0064
            android.content.SharedPreferences r3 = a     // Catch:{ Throwable -> 0x0066 }
            android.content.SharedPreferences$Editor r3 = r3.edit()     // Catch:{ Throwable -> 0x0066 }
            java.lang.String r1 = "app_black_list"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0066 }
            r2.<init>()     // Catch:{ Throwable -> 0x0066 }
            r2.append(r4)     // Catch:{ Throwable -> 0x0066 }
            java.lang.String r4 = "&"
            r2.append(r4)     // Catch:{ Throwable -> 0x0066 }
            r2.append(r5)     // Catch:{ Throwable -> 0x0066 }
            java.lang.String r4 = r2.toString()     // Catch:{ Throwable -> 0x0066 }
            android.content.SharedPreferences$Editor r3 = r3.putString(r1, r4)     // Catch:{ Throwable -> 0x0066 }
            r3.apply()     // Catch:{ Throwable -> 0x0066 }
        L_0x0064:
            monitor-exit(r0)
            return
        L_0x0066:
            monitor-exit(r0)
            return
        L_0x0068:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.icecream.a.c.a(android.content.Context, java.lang.String, java.lang.String):void");
    }

    public static synchronized void b(Context context) {
        synchronized (c.class) {
            c(context);
            try {
                a.edit().putString("app_black_list", "").apply();
            } catch (Throwable unused) {
            }
        }
    }
}
