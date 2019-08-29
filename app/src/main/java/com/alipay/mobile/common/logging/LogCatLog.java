package com.alipay.mobile.common.logging;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class LogCatLog {
    public static void printStackTraceAndMore(Throwable e) {
        LoggerFactory.getTraceLogger().error((String) "StackTrace", e);
    }

    public static void i(String tag, String msg) {
        LoggerFactory.getTraceLogger().info(tag, msg);
    }

    public static void e(String tag, String msg) {
        LoggerFactory.getTraceLogger().error(tag, msg);
    }

    public static void e(String tag, Throwable tr) {
        LoggerFactory.getTraceLogger().error(tag, tr);
    }

    public static void e(String tag, String msg, Throwable tr) {
        LoggerFactory.getTraceLogger().error(tag, msg + "::" + getExceptionMsg(tr));
    }

    public static void d(String tag, String msg) {
        LoggerFactory.getTraceLogger().debug(tag, msg);
    }

    public static void v(String tag, String msg) {
        LoggerFactory.getTraceLogger().verbose(tag, msg);
    }

    public static void w(String tag, String msg) {
        LoggerFactory.getTraceLogger().warn(tag, msg);
    }

    public static void w(String tag, Throwable tr) {
        LoggerFactory.getTraceLogger().warn(tag, tr);
    }

    public static boolean isSwitch() {
        return false;
    }

    public static String getExceptionMsg(Throwable ex) {
        if (ex == null) {
            return "";
        }
        try {
            Writer info = new StringWriter();
            PrintWriter printWriter = new PrintWriter(info);
            Throwable cause = ex.getCause();
            if (cause == null) {
                ex.printStackTrace(printWriter);
            }
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            return info.toString();
        } catch (Throwable th) {
            return "";
        }
    }
}
