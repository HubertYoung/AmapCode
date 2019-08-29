package com.alibaba.sdk.trade.container.utils;

import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;

public class AlibcComponentLog {
    public static void i(String str, String str2) {
        AlibcLogger.i(str, str2);
    }

    public static void d(String str, String str2) {
        AlibcLogger.d(str, str2);
    }

    public static void w(String str, String str2) {
        AlibcLogger.w(str, str2);
    }

    public static void e(String str, String str2, Throwable th) {
        AlibcLogger.e(str, str2);
    }

    public static void e(String str, String str2) {
        AlibcLogger.e(str, str2);
    }
}
