package defpackage;

import android.content.Context;
import android.os.Process;
import android.util.Log;

/* renamed from: far reason: default package */
/* compiled from: LogController */
public final class far implements fas {
    private static final String a;

    static {
        StringBuilder sb = new StringBuilder("(");
        sb.append(Process.myPid());
        sb.append(")");
        a = sb.toString();
    }

    public final int a(String str, String str2) {
        String concat = "VivoPush.".concat(String.valueOf(str));
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(str2);
        return Log.e(concat, sb.toString());
    }

    public final int a(String str, String str2, Throwable th) {
        String concat = "VivoPush.".concat(String.valueOf(str));
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(str2);
        return Log.e(concat, sb.toString(), th);
    }

    public final int b(String str, String str2) {
        String concat = "VivoPush.".concat(String.valueOf(str));
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(str2);
        return Log.w(concat, sb.toString());
    }

    public final int c(String str, String str2) {
        String concat = "VivoPush.".concat(String.valueOf(str));
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(str2);
        return Log.d(concat, sb.toString());
    }

    public final int d(String str, String str2) {
        if (!fat.a()) {
            return -1;
        }
        String concat = "VivoPush.".concat(String.valueOf(str));
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(str2);
        return Log.i(concat, sb.toString());
    }

    public final int b(String str, String str2, Throwable th) {
        if (!fat.a()) {
            return -1;
        }
        String concat = "VivoPush.".concat(String.valueOf(str));
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(str2);
        return Log.i(concat, sb.toString(), th);
    }

    public final int e(String str, String str2) {
        if (!fat.a()) {
            return -1;
        }
        String concat = "VivoPush.".concat(String.valueOf(str));
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(str2);
        return Log.v(concat, sb.toString());
    }

    public final String a(Throwable th) {
        return Log.getStackTraceString(th);
    }

    public final void a(Context context, String str) {
        if (fat.a()) {
            a(context, str, 0);
        }
    }

    public final void b(Context context, String str) {
        if (fat.a()) {
            a(context, str, 1);
        }
    }

    public final void c(Context context, String str) {
        if (fat.a()) {
            a(context, str, 2);
        }
    }

    private void a(Context context, String str, int i) {
        exn exn = new exn();
        exn.a = str;
        exn.b = i;
        if (i > 0) {
            d("LogController", str);
        }
        if (fbd.a(context)) {
            exn.c = true;
            for (String a2 : faw.c(context)) {
                a(context, exn, a2);
            }
            return;
        }
        exn.c = false;
        a(context, exn, context.getPackageName());
    }

    private static void a(Context context, exn exn, String str) {
        if (str.contains("test") || str.equals(faw.b(context))) {
            ewy.a(context, (fbh) exn, str);
        }
    }
}
