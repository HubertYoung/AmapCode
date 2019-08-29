package defpackage;

/* renamed from: j reason: default package */
/* compiled from: AwcnConfig */
public final class j {
    private static volatile boolean a = false;
    private static volatile boolean b = true;
    private static volatile boolean c = true;
    private static volatile boolean d = true;
    private static volatile boolean e = false;
    private static volatile boolean f = false;
    private static volatile long g = 43200000;
    private static volatile boolean h = true;
    private static volatile boolean i = true;
    private static boolean j = true;

    public static boolean a() {
        return a;
    }

    public static void b() {
        a = true;
    }

    public static boolean c() {
        return b;
    }

    public static boolean d() {
        return c;
    }

    public static void e() {
        c = false;
    }

    public static boolean f() {
        return d;
    }

    public static boolean g() {
        return e;
    }

    public static boolean h() {
        return f;
    }

    public static boolean i() {
        return h;
    }

    public static void a(boolean z) {
        h = z;
    }

    public static boolean j() {
        return i;
    }

    public static long k() {
        return g;
    }

    public static boolean l() {
        return j;
    }
}
