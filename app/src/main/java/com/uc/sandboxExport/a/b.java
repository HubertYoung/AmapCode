package com.uc.sandboxExport.a;

import android.os.Build.VERSION;
import java.lang.reflect.Method;

/* compiled from: ProGuard */
public final class b {
    private static Class<?> a;
    private static Method b;
    private static Method c;
    private static Method d;
    private static Method e;
    private static Method f;
    private static Method g;
    private static Method h;

    static {
        b = null;
        c = null;
        d = null;
        e = null;
        f = null;
        g = null;
        h = null;
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            a = cls;
            b = cls.getMethod("get", new Class[]{String.class});
            c = a.getMethod("get", new Class[]{String.class, String.class});
            d = a.getMethod("getInt", new Class[]{String.class, Integer.TYPE});
            e = a.getMethod("getLong", new Class[]{String.class, Long.TYPE});
            f = a.getMethod("getBoolean", new Class[]{String.class, Boolean.TYPE});
            g = a.getMethod("set", new Class[]{String.class, String.class});
        } catch (Throwable th) {
        }
        try {
            h = a.getMethod("addChangeCallback", new Class[]{Runnable.class});
        } catch (Throwable th2) {
            new StringBuilder("Can not found SystemProperties.addChangeCallback in API ").append(VERSION.SDK_INT);
        }
    }

    public static boolean a(String str) {
        if (!(a == null || f == null)) {
            try {
                return ((Boolean) f.invoke(null, new Object[]{str, Boolean.valueOf(false)})).booleanValue();
            } catch (Throwable th) {
            }
        }
        return false;
    }
}
