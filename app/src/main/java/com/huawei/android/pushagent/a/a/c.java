package com.huawei.android.pushagent.a.a;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.uc.webview.export.extension.UCCore;

public class c {
    private static String a = "";
    private static String b = "hwpush";
    private static String c = "PushLog";
    private static c d;

    private c() {
    }

    public static synchronized c a() {
        c cVar;
        synchronized (c.class) {
            if (d == null) {
                d = new c();
            }
            cVar = d;
        }
        return cVar;
    }

    public static String a(Throwable th) {
        return Log.getStackTraceString(th);
    }

    private synchronized void a(int i, String str, String str2, Throwable th, int i2) {
        String str3;
        try {
            if (a(i)) {
                StringBuilder sb = new StringBuilder("[");
                sb.append(Thread.currentThread().getName());
                sb.append("-");
                sb.append(Thread.currentThread().getId());
                sb.append("]");
                sb.append(str2);
                String sb2 = sb.toString();
                StackTraceElement[] stackTrace = new Throwable().getStackTrace();
                if (stackTrace.length > i2) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(sb2);
                    sb3.append("(");
                    sb3.append(a);
                    sb3.append("/");
                    sb3.append(stackTrace[i2].getFileName());
                    sb3.append(":");
                    sb3.append(stackTrace[i2].getLineNumber());
                    sb3.append(")");
                    str3 = sb3.toString();
                } else {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(sb2);
                    sb4.append("(");
                    sb4.append(a);
                    sb4.append("/unknown source)");
                    str3 = sb4.toString();
                }
                if (th != null) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(str3);
                    sb5.append(10);
                    sb5.append(a(th));
                    str3 = sb5.toString();
                }
                Log.println(i, c, str3);
            }
        } catch (Exception e) {
            new StringBuilder("call writeLog cause:").append(e.toString());
        }
    }

    public static synchronized void a(Context context) {
        synchronized (c.class) {
            if (d == null) {
                a();
            }
            if (TextUtils.isEmpty(a)) {
                String packageName = context.getPackageName();
                if (packageName != null) {
                    String[] split = packageName.split("\\.");
                    if (split.length > 0) {
                        a = split[split.length - 1];
                    }
                }
                c = b(context);
            }
        }
    }

    public static void a(String str, String str2) {
        a().a(3, str, str2, null, 2);
    }

    public static void a(String str, String str2, Throwable th) {
        a().a(3, str, str2, th, 2);
    }

    public static void a(String str, String str2, Object... objArr) {
        try {
            a().a(3, str, String.format(str2, objArr), null, 2);
        } catch (Exception e) {
            new StringBuilder("call writeLog cause:").append(e.toString());
        }
    }

    private static boolean a(int i) {
        return Log.isLoggable(b, i);
    }

    public static String b(Context context) {
        String str;
        String str2;
        String str3 = "PushLogSC2816";
        if (context == null) {
            return str3;
        }
        if ("com.huawei.android.pushagent".equals(context.getPackageName())) {
            str = "SC";
            str2 = UCCore.OPTION_HARDWARE_ACCELERATED;
        } else if ("android".equals(context.getPackageName())) {
            str = "SC";
            str2 = "";
        } else {
            if (!TextUtils.isEmpty(a)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str3);
                sb.append("_");
                sb.append(a);
                str3 = sb.toString();
            }
            return str3;
        }
        return str3.replace(str, str2);
    }

    public static void b(String str, String str2) {
        a().a(4, str, str2, null, 2);
    }

    public static void b(String str, String str2, Throwable th) {
        a().a(4, str, str2, th, 2);
    }

    public static void b(String str, String str2, Object... objArr) {
        try {
            a().a(2, str, String.format(str2, objArr), null, 2);
        } catch (Exception e) {
            new StringBuilder("call writeLog cause:").append(e.toString());
        }
    }

    public static void c(String str, String str2) {
        a().a(5, str, str2, null, 2);
    }

    public static void c(String str, String str2, Throwable th) {
        a().a(6, str, str2, th, 2);
    }

    public static void d(String str, String str2) {
        a().a(6, str, str2, null, 2);
    }

    public static void d(String str, String str2, Throwable th) {
        a().a(2, str, str2, th, 2);
    }

    public static void e(String str, String str2) {
        a().a(2, str, str2, null, 2);
    }
}
