package com.alipay.android.phone.wallet.tinytracker;

import com.alipay.mobile.common.logging.api.LoggerFactory;

public class SpmLogCator {
    public static void verbose(String tag, String var2) {
        LoggerFactory.getTraceLogger().verbose(a(tag), var2);
    }

    public static void debug(String tag, String var2) {
        LoggerFactory.getTraceLogger().debug(a(tag), var2);
    }

    public static void info(String tag, String var2) {
        LoggerFactory.getTraceLogger().info(a(tag), var2);
    }

    public static void warn(String tag, String var2) {
        LoggerFactory.getTraceLogger().warn(a(tag), var2);
    }

    public static void warn(String tag, Throwable var2) {
        LoggerFactory.getTraceLogger().warn(a(tag), var2);
    }

    public static void warn(String tag, String var2, Throwable var3) {
        LoggerFactory.getTraceLogger().warn(a(tag), var2, var3);
    }

    public static void error(String tag, String var2) {
        LoggerFactory.getTraceLogger().error(a(tag), var2);
    }

    public static void error(String tag, Throwable var2) {
        LoggerFactory.getTraceLogger().error(a(tag), var2);
    }

    public static void error(String tag, String var2, Throwable var3) {
        LoggerFactory.getTraceLogger().error(a(tag), var2, var3);
    }

    public static void print(String tag, String var2) {
        LoggerFactory.getTraceLogger().print(a(tag), var2);
    }

    public static void print(String tag, Throwable var2) {
        LoggerFactory.getTraceLogger().print(a(tag), var2);
    }

    private static String a(String tag) {
        return "SpmLogCator." + tag;
    }
}
