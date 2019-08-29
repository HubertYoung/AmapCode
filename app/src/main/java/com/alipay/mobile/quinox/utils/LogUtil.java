package com.alipay.mobile.quinox.utils;

public class LogUtil {
    private static boolean BOOTSTRAP_LOG_ENABLE = true;

    public static void d(String str, String str2) {
    }

    public static void d(String str, String str2, Throwable th) {
    }

    public static void e(String str, String str2) {
    }

    public static void e(String str, String str2, Throwable th) {
    }

    public static void i(String str, String str2) {
    }

    public static void i(String str, String str2, Throwable th) {
    }

    public static void v(String str, String str2) {
    }

    public static void v(String str, String str2, Throwable th) {
    }

    public static void w(String str, String str2) {
    }

    public static void w(String str, String str2, Throwable th) {
    }

    public static void w(String str, Throwable th) {
    }

    public static boolean isDebug() {
        return BOOTSTRAP_LOG_ENABLE;
    }

    public static void setDebug(boolean z) {
        BOOTSTRAP_LOG_ENABLE = z;
    }
}
