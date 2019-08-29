package com.alipay.android.phone.mobilesdk.permission.guide.a;

import android.content.Context;
import android.content.SharedPreferences;
import com.alipay.mobile.common.logging.api.LoggerFactory;

/* compiled from: Configs */
public final class a {
    private static SharedPreferences g(Context context) {
        return context.getSharedPreferences("permission_configs", 0);
    }

    public static long a(Context context, String key) {
        return g(context).getLong(key, 0);
    }

    public static void a(Context context, String key, long value) {
        g(context).edit().putLong(key, value).apply();
    }

    public static boolean b(Context context, String key) {
        return g(context).getBoolean(key, true);
    }

    public static void c(Context context, String key) {
        g(context).edit().putBoolean(key, false).apply();
    }

    private static String a(Context context, String key, String defValue) {
        return g(context).getString(key, defValue);
    }

    private static void b(Context context, String key, String value) {
        g(context).edit().putString(key, value).apply();
    }

    public static void a(Context context) {
        g(context).edit().clear().apply();
        LoggerFactory.getTraceLogger().debug("Permissions", "Configs.clear() is called.");
    }

    public static long b(Context context) {
        return a(context, (String) "last_time_get_info_local");
    }

    public static void a(Context context, long lastTime) {
        LoggerFactory.getTraceLogger().warn((String) "Permissions", "putRpcLastTime_Local(lastTime=" + lastTime + ")");
        a(context, (String) "last_time_get_info_local", lastTime);
    }

    public static String c(Context context) {
        return a(context, (String) "last_time_get_info_server", (String) "0");
    }

    public static void d(Context context, String lastTime) {
        LoggerFactory.getTraceLogger().warn((String) "Permissions", "putRpcLastTime_Server(lastTime=" + lastTime + ")");
        b(context, "last_time_get_info_server", lastTime);
    }

    public static String d(Context context) {
        return a(context, (String) "product_version", (String) "");
    }

    public static void e(Context context, String productVersion) {
        b(context, "product_version", productVersion);
    }

    public static void f(Context context, String value) {
        b(context, "lastOsVersion", value);
    }

    public static String e(Context context) {
        return a(context, (String) "lastOsVersion", (String) "");
    }

    public static void g(Context context, String value) {
        b(context, "lastRomVersion", value);
    }

    public static String f(Context context) {
        return a(context, (String) "lastRomVersion", (String) "");
    }
}
