package defpackage;

import android.util.Log;

/* renamed from: bkj reason: default package */
/* compiled from: LogPrint */
final class bkj {
    private static final boolean a = b.a;

    static void a(Object obj) {
        if (a) {
            a(6, a(a()), obj, null);
        }
    }

    static void a(Object obj, Throwable th) {
        if (a) {
            a(6, a(a()), obj, th);
        }
    }

    static void b(Object obj) {
        if (a) {
            a(5, a(a()), obj, null);
        }
    }

    static void b(Object obj, Throwable th) {
        if (a) {
            a(5, a(a()), obj, th);
        }
    }

    private static StackTraceElement a() {
        return Thread.currentThread().getStackTrace()[4];
    }

    private static String a(StackTraceElement stackTraceElement) {
        String className = stackTraceElement.getClassName();
        String substring = className.substring(className.lastIndexOf(".") + 1);
        if (substring.length() <= 23) {
            return substring;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(substring.substring(0, 20));
        sb.append("...");
        return sb.toString();
    }

    private static void a(int i, String str, Object obj, Throwable th) {
        String str2;
        if (a && i >= 2) {
            try {
                Log.isLoggable(str, i);
                if (th != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(String.valueOf(obj));
                    sb.append(10);
                    sb.append(Log.getStackTraceString(th));
                    str2 = sb.toString();
                } else if (obj instanceof Throwable) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(String.valueOf(obj));
                    sb2.append(10);
                    sb2.append(Log.getStackTraceString((Throwable) obj));
                    str2 = sb2.toString();
                } else {
                    str2 = String.valueOf(obj);
                }
                Log.println(i, str, str2);
            } catch (Throwable unused) {
            }
        }
    }
}
