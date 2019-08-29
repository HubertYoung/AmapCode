package com.alipay.mobile.beehive.compositeui.banner.model;

import com.alipay.mobile.common.logging.api.LoggerFactory;

public class BannerLog {
    private static boolean mSwitch = true;
    private static String mTag = "Bannerview";

    public static void printStackTraceAndMore(Throwable e) {
    }

    public static void i(String msg) {
        if (mSwitch && msg != null) {
            LoggerFactory.getTraceLogger().info(mTag, msg);
        }
    }

    public static void e(String msg) {
        if (mSwitch && msg != null) {
            LoggerFactory.getTraceLogger().error(mTag, msg);
        }
    }

    public static void e(Throwable tr) {
        e(tr == null ? "" : tr.getMessage());
    }

    public static void e(String msg, Throwable tr) {
        if (mSwitch && msg != null) {
            LoggerFactory.getTraceLogger().error(mTag, msg, tr);
        }
    }

    public static void d(String msg) {
        if (mSwitch && msg != null) {
            LoggerFactory.getTraceLogger().info(mTag, msg);
        }
    }

    public static void v(String msg) {
        if (mSwitch && msg != null) {
            LoggerFactory.getTraceLogger().verbose(mTag, msg);
        }
    }

    public static void w(String msg) {
        if (mSwitch && msg != null) {
            LoggerFactory.getTraceLogger().warn(mTag, msg);
        }
    }

    public static void w(Throwable tr) {
        String log = tr.toString();
        for (int i = 0; i < tr.getStackTrace().length; i++) {
            log = log + tr.getStackTrace()[i] + "\n";
        }
        LoggerFactory.getTraceLogger().warn(mTag, log);
    }

    public static boolean isSwitch() {
        return mSwitch;
    }
}
