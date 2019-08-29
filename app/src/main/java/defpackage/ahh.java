package defpackage;

/* renamed from: ahh reason: default package */
/* compiled from: Strings */
public final class ahh {
    public static double a(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Throwable th) {
            th.printStackTrace();
            return 0.0d;
        }
    }

    public static int b(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Throwable th) {
            th.printStackTrace();
            return 0;
        }
    }

    public static long c(String str) {
        try {
            return Long.parseLong(str);
        } catch (Throwable th) {
            th.printStackTrace();
            return 0;
        }
    }
}
