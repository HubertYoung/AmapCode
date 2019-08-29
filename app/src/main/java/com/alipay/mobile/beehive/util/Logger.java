package com.alipay.mobile.beehive.util;

import com.alipay.mobile.common.logging.api.LoggerFactory;

public class Logger {
    public static final void d(String tag, String msg) {
        LoggerFactory.getTraceLogger().debug(tag, msg);
    }

    public static final void i(String tag, String msg) {
        LoggerFactory.getTraceLogger().debug(tag, msg);
    }

    public static final void w(String tag, String msg) {
        LoggerFactory.getTraceLogger().warn(tag, msg);
    }

    public static final void w(String tag, Throwable t) {
        LoggerFactory.getTraceLogger().warn(tag, t);
    }

    public static final void e(String tag, String msg) {
        LoggerFactory.getTraceLogger().error(tag, msg);
    }

    public static final void e(String tag, String msg, Throwable t) {
        LoggerFactory.getTraceLogger().error(tag, msg, t);
    }
}
