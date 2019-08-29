package com.alipay.mobile.common.utils;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public final class LogCatUtil {
    public LogCatUtil() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static final void info(String tag, String log) {
        LoggerFactory.getTraceLogger().info(tag, log);
    }

    public static final void verbose(String tag, String log) {
        LoggerFactory.getTraceLogger().verbose(tag, log);
    }

    public static final void debug(String tag, String log) {
        LoggerFactory.getTraceLogger().debug(tag, log);
    }

    public static final void warn(String tag, String log) {
        LoggerFactory.getTraceLogger().warn(tag, log);
    }

    public static final void warn(String tag, Throwable throwable) {
        LoggerFactory.getTraceLogger().warn(tag, throwable);
    }

    public static final void error(String tag, String log) {
        LoggerFactory.getTraceLogger().error(tag, log);
    }

    public static final void error(String tag, Throwable throwable) {
        LoggerFactory.getTraceLogger().error(tag, throwable);
    }

    public static final void error(String tag, String log, Throwable throwable) {
        LoggerFactory.getTraceLogger().error(tag, log, throwable);
    }

    public static final void printInfo(String tag, String msg) {
        LoggerFactory.getTraceLogger().print(tag, msg);
    }

    public static final void printError(String tag, Throwable e) {
        LoggerFactory.getTraceLogger().print(tag, e);
    }
}
