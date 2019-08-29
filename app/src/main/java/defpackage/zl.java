package defpackage;

/* renamed from: zl reason: default package */
/* compiled from: ApmConfig */
public class zl {
    private static volatile zl a;

    private zl() {
    }

    public static zl a() {
        if (a == null) {
            synchronized (zl.class) {
                if (a == null) {
                    a = new zl();
                }
            }
        }
        return a;
    }

    public static boolean a(String str) {
        c d = aaf.d();
        if (d == null) {
            return false;
        }
        return d.a(str);
    }

    public static boolean b() {
        c d = aaf.d();
        if (d == null) {
            return false;
        }
        return d.a();
    }
}
