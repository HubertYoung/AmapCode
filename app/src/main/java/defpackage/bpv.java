package defpackage;

/* renamed from: bpv reason: default package */
/* compiled from: Logger */
public final class bpv {
    private static int a = 2;
    private static a b;

    /* renamed from: bpv$a */
    /* compiled from: Logger */
    public interface a {
        void a(String str, String str2);

        void b(String str, String str2);

        void c(String str, String str2);

        void d(String str, String str2);

        void e(String str, String str2);
    }

    public static synchronized void a(a aVar) {
        synchronized (bpv.class) {
            if (b == null) {
                b = aVar;
            }
        }
    }

    public static void a() {
        a = 4;
    }

    public static boolean a(int i) {
        return i >= a;
    }

    public static void a(String str, String str2) {
        if (a <= 2 && b != null) {
            b.a(str, str2);
        }
    }

    public static void b(String str, String str2) {
        if (a <= 3 && b != null) {
            b.b(str, str2);
        }
    }

    public static void c(String str, String str2) {
        if (a <= 4 && b != null) {
            b.c(str, str2);
        }
    }

    public static void d(String str, String str2) {
        if (a <= 5 && b != null) {
            b.d(str, str2);
        }
    }

    public static void e(String str, String str2) {
        if (a <= 6 && b != null) {
            b.e(str, str2);
        }
    }
}
