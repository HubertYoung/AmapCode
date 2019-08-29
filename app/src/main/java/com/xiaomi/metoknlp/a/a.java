package com.xiaomi.metoknlp.a;

import android.os.Build;
import java.lang.reflect.Field;

public class a {
    private static String a = "NLPBuild";
    private static boolean b = false;
    private static String c = Build.BRAND;
    private static String d = Build.TYPE;
    private static Class e;
    private static Field f;
    private static Field g;
    private static Field h;
    private static Field i;
    private static Field j;

    static {
        boolean z = true;
        try {
            Class<?> cls = Class.forName("miui.os.Build");
            e = cls;
            f = cls.getField("IS_CTS_BUILD");
            g = e.getField("IS_CTA_BUILD");
            h = e.getField("IS_ALPHA_BUILD");
            i = e.getField("IS_DEVELOPMENT_VERSION");
            j = e.getField("IS_STABLE_VERSION");
            z = false;
        } catch (ClassNotFoundException | Exception | NoSuchFieldException unused) {
        }
        if (z) {
            e = null;
            f = null;
            g = null;
            h = null;
            i = null;
            j = null;
        }
    }

    public static boolean a() {
        if (b) {
            new StringBuilder("brand=").append(c);
        }
        return c != null && c.equalsIgnoreCase("xiaomi");
    }

    public static String b() {
        StringBuilder sb = new StringBuilder("3rdROM-");
        sb.append(d);
        return sb.toString();
    }

    public static boolean c() {
        if (!(!a() || e == null || f == null)) {
            try {
                return f.getBoolean(e);
            } catch (IllegalAccessException unused) {
            }
        }
        return false;
    }

    public static boolean d() {
        if (!(!a() || e == null || h == null)) {
            try {
                return h.getBoolean(e);
            } catch (IllegalAccessException unused) {
            }
        }
        return false;
    }

    public static boolean e() {
        if (!(!a() || e == null || i == null)) {
            try {
                return i.getBoolean(e);
            } catch (IllegalAccessException unused) {
            }
        }
        return false;
    }

    public static boolean f() {
        if (!(!a() || e == null || j == null)) {
            try {
                return j.getBoolean(e);
            } catch (IllegalAccessException unused) {
            }
        }
        return false;
    }
}
