package defpackage;

import android.content.Context;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* renamed from: bz reason: default package */
/* compiled from: AmdcRuntimeInfo */
public final class bz {
    public static volatile double a;
    public static volatile double b;
    public static volatile String c;
    public static volatile String d;
    public static volatile String e;
    private static volatile Context f;
    private static volatile int g;
    private static volatile long h;
    private static cg i;
    private static Map<String, String> j;

    public static void a(int i2, int i3) {
        cl.b("awcn.AmdcRuntimeInfo", "set amdc limit", null, H5PermissionManager.level, Integer.valueOf(i2), "time", Integer.valueOf(i3));
        if (i2 >= 0 && i2 <= 3) {
            g = i2;
            h = System.currentTimeMillis() + (((long) i3) * 1000);
        }
    }

    public static int a() {
        if (g > 0 && System.currentTimeMillis() - h > 0) {
            h = 0;
            g = 0;
        }
        return g;
    }

    public static void a(Context context) {
        f = context;
    }

    public static void a(cg cgVar) {
        i = cgVar;
    }

    public static cg b() {
        return i;
    }

    public static void a(String str, String str2, String str3) {
        d = str;
        e = str2;
        c = str3;
    }

    public static synchronized void a(String str, String str2) {
        synchronized (bz.class) {
            if (j == null) {
                j = new HashMap();
            }
            j.put(str, str2);
        }
    }

    public static synchronized Map<String, String> c() {
        synchronized (bz.class) {
            if (j == null) {
                Map<String, String> map = Collections.EMPTY_MAP;
                return map;
            }
            HashMap hashMap = new HashMap(j);
            return hashMap;
        }
    }
}
