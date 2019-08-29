package com.alipay.android.phone.inside.log.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class ApkUtil {
    private static String a;
    private static String b;
    private static String c;
    private static String d;

    public static String a() {
        return a;
    }

    public static String b() {
        return b;
    }

    public static String c() {
        return c;
    }

    public static String d() {
        return d;
    }

    public static void a(Context context) {
        if (context != null) {
            a = context.getPackageName();
            try {
                PackageManager packageManager = context.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(a, 128);
                b = packageInfo.versionName;
                c = Integer.toString(packageInfo.versionCode);
                d = packageManager.getApplicationLabel(packageManager.getApplicationInfo(context.getPackageName(), 0)).toString();
            } catch (Throwable unused) {
            }
        }
    }
}
