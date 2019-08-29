package com.amap.api.service;

import android.content.Context;

/* compiled from: AmapAutoConfig */
public class b {
    private static boolean a = true;
    private static boolean b = true;
    private static String c = "amap_phone";

    public static void a(Context context) {
        if (context != null) {
            try {
                String packageName = context.getPackageName();
                if ("com.autonavi.amapauto".equalsIgnoreCase(packageName) || "com.autonavi.amapautolite".equalsIgnoreCase(packageName)) {
                    a = false;
                    b = false;
                    c = "amap_auto";
                }
            } catch (Throwable unused) {
            }
        }
    }

    public static boolean a() {
        return a;
    }

    public static boolean b() {
        return b;
    }

    public static String c() {
        return c;
    }
}
