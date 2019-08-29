package com.alipay.multimedia.js.utils;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class Logger {
    public Logger() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static void verbose(String tag, String msg) {
        LoggerFactory.getTraceLogger().verbose(tag, msg);
    }

    public static void debug(String tag, String msg) {
        LoggerFactory.getTraceLogger().debug(tag, msg);
    }

    public static void info(String tag, String msg) {
        LoggerFactory.getTraceLogger().info(tag, msg);
    }

    public static void warn(String tag, String msg) {
        LoggerFactory.getTraceLogger().warn(tag, msg);
    }

    public static void warn(String tag, Throwable t) {
        LoggerFactory.getTraceLogger().warn(tag, t);
    }

    public static void warn(String tag, String msg, Throwable t) {
        LoggerFactory.getTraceLogger().warn(tag, msg, t);
    }

    public static void error(String tag, String msg) {
        LoggerFactory.getTraceLogger().error(tag, msg);
    }

    public static void error(String tag, Throwable t) {
        LoggerFactory.getTraceLogger().error(tag, t);
    }

    public static void error(String tag, String msg, Throwable t) {
        LoggerFactory.getTraceLogger().error(tag, msg, t);
    }

    public static void print(String tag, String msg) {
        LoggerFactory.getTraceLogger().print(tag, msg);
    }

    public static void print(String tag, Throwable t) {
        LoggerFactory.getTraceLogger().print(tag, t);
    }
}
