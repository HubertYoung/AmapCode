package defpackage;

/* renamed from: cwi reason: default package */
/* compiled from: StrictRuntime */
public final class cwi {
    public static boolean a = false;

    public static void a(String str, String str2, Throwable th) {
        cwl.a(str, str2);
        if (a) {
            throw new RuntimeException(str2, th);
        }
    }

    public static void a(Throwable th) {
        if (a) {
            throw new RuntimeException(th);
        }
    }
}
