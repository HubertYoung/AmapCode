package com.ut.mini.exposure;

import com.alibaba.analytics.utils.Logger;

class ExpLogger {
    static boolean enableLog = false;

    ExpLogger() {
    }

    public static void d() {
        if (enableLog) {
            Logger.d();
        }
    }

    public static void d(String str, Object... objArr) {
        if (enableLog) {
            Logger.d(str, objArr);
        }
    }

    public static void w(String str, Object... objArr) {
        if (enableLog) {
            Logger.w(str, objArr);
        }
    }

    public static void e(String str, Object... objArr) {
        if (enableLog) {
            Logger.e(str, objArr);
        }
    }

    public static void e(String str, Throwable th, Object... objArr) {
        if (enableLog) {
            Logger.e(str, th, objArr);
        }
    }
}
