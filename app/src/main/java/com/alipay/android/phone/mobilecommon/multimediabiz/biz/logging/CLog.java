package com.alipay.android.phone.mobilecommon.multimediabiz.biz.logging;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class CLog {
    private static long a = 0;
    private static int b = 99;

    private static int a() {
        if (System.currentTimeMillis() - a > 600000) {
            a = System.currentTimeMillis();
            b = ConfigManager.getInstance().getLogLevel(99);
        }
        return b;
    }

    private static boolean a(int logLevel) {
        return logLevel >= a();
    }

    private static String a(String format, Object... args) {
        String str = format;
        if (args != null && args.length > 0) {
            try {
                str = String.format(format, args);
            } catch (Exception e) {
                str = format;
            }
        }
        return "[" + Thread.currentThread().getName() + "] " + str;
    }

    public static void v(String tag, String format, Object... args) {
        if (a(0)) {
            LoggerFactory.getTraceLogger().verbose(tag, a(format, args));
        }
    }

    public static void d(String tag, String format, Object... args) {
        if (a(1)) {
            LoggerFactory.getTraceLogger().debug(tag, a(format, args));
        }
    }

    public static void i(String tag, String format, Object... args) {
        if (a(2)) {
            LoggerFactory.getTraceLogger().info(tag, a(format, args));
        }
    }

    public static void w(String tag, String format, Object... args) {
        if (a(3)) {
            LoggerFactory.getTraceLogger().warn(tag, a(format, args));
        }
    }

    public static void w(String tag, Throwable throwable) {
        if (a(3)) {
            LoggerFactory.getTraceLogger().warn(tag, throwable);
        }
    }

    public static void e(String tag, String format, Object... args) {
        if (a(4)) {
            LoggerFactory.getTraceLogger().error(tag, a(format, args));
        }
    }

    public static void e(String tag, Throwable throwable, String format, Object... args) {
        if (a(4)) {
            LoggerFactory.getTraceLogger().error(tag, a(format, args), throwable);
        }
    }

    public static void e(String tag, Throwable throwable) {
        if (a(4)) {
            LoggerFactory.getTraceLogger().error(tag, throwable);
        }
    }
}
