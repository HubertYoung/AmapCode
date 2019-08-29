package org.altbeacon.beacon.b;

/* compiled from: LogManager */
public final class d {
    private static e a = f.a();
    private static boolean b = false;

    public static boolean a() {
        return b;
    }

    public static void a(String tag, String message, Object... args) {
        a.a(tag, message, args);
    }

    public static void b(String tag, String message, Object... args) {
        a.b(tag, message, args);
    }

    public static void c(String tag, String message, Object... args) {
        a.c(tag, message, args);
    }

    public static void a(Throwable t, String tag, String message, Object... args) {
        a.a(t, tag, message, args);
    }

    public static void d(String tag, String message, Object... args) {
        a.d(tag, message, args);
    }

    public static void b(Throwable t, String tag, String message, Object... args) {
        a.b(t, tag, message, args);
    }
}
