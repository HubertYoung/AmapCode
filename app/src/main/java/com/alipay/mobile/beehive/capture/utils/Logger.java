package com.alipay.mobile.beehive.capture.utils;

import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class Logger {
    private static final String APP = "CaptureApp";

    public static void verbose(String tag, String content) {
        LoggerFactory.getTraceLogger().verbose(buildTag(APP, tag), content);
    }

    public static void debug(String tag, String content) {
        LoggerFactory.getTraceLogger().debug(buildTag(APP, tag), content);
    }

    public static void info(String tag, String content) {
        LoggerFactory.getTraceLogger().info(buildTag(APP, tag), content);
    }

    public static void error(String tag, String content) {
        LoggerFactory.getTraceLogger().error(buildTag(APP, tag), content);
    }

    public static void error(String tag, String content, Throwable throwable) {
        LoggerFactory.getTraceLogger().error(buildTag(APP, tag), content, throwable);
    }

    public static void error(String tag, Throwable throwable) {
        LoggerFactory.getTraceLogger().error(buildTag(APP, tag), throwable);
    }

    public static void warn(String tag, String content) {
        LoggerFactory.getTraceLogger().warn(buildTag(APP, tag), content);
    }

    public static void v(String tag, String content) {
        Log.v(buildTag(APP, tag), content);
    }

    public static void d(String tag, String content) {
        Log.d(buildTag(APP, tag), content);
    }

    public static void i(String tag, String content) {
        Log.i(buildTag(APP, tag), content);
    }

    public static void e(String tag, String content) {
        Log.e(buildTag(APP, tag), content);
    }

    public static void w(String tag, String content) {
        Log.w(buildTag(APP, tag), content);
    }

    private static String buildTag(String app, String tag) {
        return "[" + app + "," + tag + "]";
    }
}
