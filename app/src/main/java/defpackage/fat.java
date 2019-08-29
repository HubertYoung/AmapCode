package defpackage;

import android.content.Context;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;

/* renamed from: fat reason: default package */
/* compiled from: LogUtil */
public final class fat {
    public static final fas a = new far();
    private static boolean b = fbd.b((String) "persist.sys.log.ctrl", (String) BQCCameraParam.VALUE_NO).equals("yes");
    private static boolean c;

    public static boolean a() {
        return b;
    }

    public static void a(boolean z) {
        b = z;
        c = z;
    }

    public static int a(String str, String str2) {
        return a.a(str, str2);
    }

    public static int a(String str, String str2, Throwable th) {
        return a.a(str, str2, th);
    }

    public static int b(String str, String str2) {
        return a.b(str, str2);
    }

    public static int c(String str, String str2) {
        return a.c(str, str2);
    }

    public static int d(String str, String str2) {
        return a.d(str, str2);
    }

    public static int b(String str, String str2, Throwable th) {
        return a.b(str, str2, th);
    }

    public static int e(String str, String str2) {
        return a.e(str, str2);
    }

    public static String a(Throwable th) {
        return a.a(th);
    }

    public static void a(Context context, String str) {
        a.a(context, str);
    }

    public static void b(Context context, String str) {
        a.b(context, str);
    }

    public static void c(Context context, String str) {
        a.c(context, str);
    }
}
