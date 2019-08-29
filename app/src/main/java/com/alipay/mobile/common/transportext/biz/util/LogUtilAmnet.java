package com.alipay.mobile.common.transportext.biz.util;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;

public class LogUtilAmnet {
    public static final String PRETAG = "amnet_";
    private static volatile TraceLogger traceLogger;

    public static void v(String tag, String msg) {
        if (traceLogger == null) {
            checkTraceLogger();
        }
        traceLogger.verbose(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (traceLogger == null) {
            checkTraceLogger();
        }
        traceLogger.debug(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (traceLogger == null) {
            checkTraceLogger();
        }
        traceLogger.info(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (traceLogger == null) {
            checkTraceLogger();
        }
        traceLogger.warn(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (traceLogger == null) {
            checkTraceLogger();
        }
        traceLogger.error(tag, msg);
    }

    public static void e(String tag, String msg, Throwable e) {
        if (traceLogger == null) {
            checkTraceLogger();
        }
        traceLogger.error(tag, msg, e);
    }

    private static void checkTraceLogger() {
        traceLogger = LoggerFactory.getTraceLogger();
    }
}
