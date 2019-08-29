package com.tencent.mm.opensdk.utils;

public class Log {
    private static ILog logImpl;

    public static void d(String str, String str2) {
        if (logImpl != null) {
            logImpl.d(str, str2);
        }
    }

    public static void e(String str, String str2) {
        if (logImpl != null) {
            logImpl.e(str, str2);
        }
    }

    public static void i(String str, String str2) {
        if (logImpl != null) {
            logImpl.i(str, str2);
        }
    }

    public static void setLogImpl(ILog iLog) {
        logImpl = iLog;
    }

    public static void v(String str, String str2) {
        if (logImpl != null) {
            logImpl.v(str, str2);
        }
    }

    public static void w(String str, String str2) {
        if (logImpl != null) {
            logImpl.w(str, str2);
        }
    }
}
