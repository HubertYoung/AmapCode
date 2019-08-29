package com.alipay.alipaylogger;

import com.alipay.mobile.common.logging.api.LoggerFactory;

public final class Log {
    private static Logger a;

    public static void setLogger(Logger logger) {
        a = logger;
    }

    public static int v(String tag, String msg) {
        if (a != null) {
            return a.v(tag, msg);
        }
        LoggerFactory.getTraceLogger().verbose(tag, msg);
        return 0;
    }

    public static int d(String tag, String msg) {
        if (a != null) {
            return a.d(tag, msg);
        }
        LoggerFactory.getTraceLogger().debug(tag, msg);
        return 0;
    }

    public static int i(String tag, String msg) {
        if (a != null) {
            return a.i(tag, msg);
        }
        LoggerFactory.getTraceLogger().info(tag, msg);
        return 0;
    }

    public static int w(String tag, String msg) {
        if (a != null) {
            return a.w(tag, msg);
        }
        LoggerFactory.getTraceLogger().warn(tag, msg);
        return 0;
    }

    public static int e(String tag, String msg) {
        if (a != null) {
            return a.e(tag, msg);
        }
        LoggerFactory.getTraceLogger().error(tag, msg);
        return 0;
    }

    public static int e(String tag, String msg, Throwable tr) {
        if (a != null) {
            return a.e(tag, msg, tr);
        }
        LoggerFactory.getTraceLogger().error(tag, msg, tr);
        return 0;
    }
}
