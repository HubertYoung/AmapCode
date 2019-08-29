package defpackage;

import java.lang.reflect.Method;

/* renamed from: aht reason: default package */
/* compiled from: SystemPropertiesHelper */
public final class aht {
    private static aht a;
    private static final Object b = new Object();
    private Class c;
    private Method d;

    public static aht a() {
        if (a == null) {
            synchronized (b) {
                try {
                    if (a == null) {
                        a = new aht();
                    }
                }
            }
        }
        return a;
    }

    private aht() {
        if (this.c == null) {
            try {
                this.c = Class.forName("android.os.SystemProperties");
            } catch (Exception e) {
                e.printStackTrace();
                this.c = null;
                this.d = null;
            }
        }
        if (this.c != null && this.d == null) {
            try {
                this.d = this.c.getMethod("get", new Class[]{String.class});
            } catch (Exception e2) {
                e2.printStackTrace();
                this.c = null;
                this.d = null;
            }
        }
    }

    public final String a(String str) {
        if (this.d == null) {
            return "";
        }
        try {
            Object invoke = this.d.invoke(null, new Object[]{str});
            if (invoke != null) {
                return invoke.toString();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
