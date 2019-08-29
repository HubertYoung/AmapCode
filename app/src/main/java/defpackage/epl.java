package defpackage;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Global;
import android.provider.Settings.System;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* renamed from: epl reason: default package */
/* compiled from: Utils */
public final class epl {
    private static int a;
    private static String b;
    private static final SimpleDateFormat c = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
    private static final SimpleDateFormat d = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US);

    public static boolean a(Context context, String str) {
        return VERSION.SDK_INT < 23 || context.checkSelfPermission(str) == 0;
    }

    public static boolean a(Context context) {
        try {
            return VERSION.SDK_INT < 17 ? System.getInt(context.getContentResolver(), "airplane_mode_on", 0) == 1 : Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 0;
        } catch (Exception unused) {
            return false;
        }
    }

    public static int a() {
        if (a == 0) {
            try {
                a = VERSION.SDK_INT;
            } catch (Exception unused) {
            }
        }
        return a;
    }

    public static String b() {
        if (b == null) {
            try {
                b = Build.MODEL;
            } catch (Exception unused) {
                b = "";
            }
        }
        return b != null ? b : "";
    }

    public static String a(long j) {
        return c.format(Long.valueOf(j));
    }

    public static long c() {
        return System.currentTimeMillis();
    }
}
