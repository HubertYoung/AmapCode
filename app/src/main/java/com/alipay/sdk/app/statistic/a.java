package com.alipay.sdk.app.statistic;

import android.content.Context;

public final class a {
    public static final String a = "alipay_cashier_statistic_record";
    private static c b;

    public static void a(Context context) {
        if (b == null) {
            b = new c(context);
        }
    }

    public static void a(Context context, String str) {
        new Thread(new b(context, str)).start();
    }

    public static synchronized void b(Context context, String str) {
        synchronized (a.class) {
            if (b != null) {
                a(context, b.a(str));
                b = null;
            }
        }
    }

    public static void a(String str, Throwable th) {
        if (b != null && th.getClass() != null) {
            b.a(str, th.getClass().getSimpleName(), th);
        }
    }

    private static void a(String str, String str2, Throwable th, String str3) {
        if (b != null) {
            b.a(str, str2, c.a(th), str3);
        }
    }

    public static void a(String str, String str2, Throwable th) {
        if (b != null) {
            b.a(str, str2, th);
        }
    }

    public static void a(String str, String str2, String str3) {
        if (b != null) {
            b.a(str, str2, str3);
        }
    }

    private static void a(Context context, String str, String str2, String str3) {
        if (context != null) {
            try {
                c cVar = new c(context);
                cVar.a(str, str2, str3);
                a(context, cVar.a((String) ""));
            } catch (Throwable unused) {
            }
        }
    }
}
