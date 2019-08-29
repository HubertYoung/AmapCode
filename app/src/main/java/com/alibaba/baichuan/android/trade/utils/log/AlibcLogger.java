package com.alibaba.baichuan.android.trade.utils.log;

import com.alibaba.baichuan.android.trade.AlibcContext;

public class AlibcLogger {
    public static void d(String str, String str2) {
        AlibcContext.isDebuggable();
    }

    public static void e(String str, String str2) {
        if (!AlibcContext.isDebuggable()) {
            boolean z = AlibcContext.showErrorInReleaseMode;
        }
    }

    public static void e(String str, String str2, Throwable th) {
        if (!AlibcContext.isDebuggable()) {
            boolean z = AlibcContext.showErrorInReleaseMode;
        }
    }

    public static void i(String str, String str2) {
        AlibcContext.isDebuggable();
    }

    public static void w(String str, String str2) {
        AlibcContext.isDebuggable();
    }
}
