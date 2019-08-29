package com.alipay.mobile.tinyappcustom.c;

import com.alipay.mobile.common.logging.api.LoggerFactory;

/* compiled from: Logger */
public final class b {
    public static void a(String tag, String msg) {
        LoggerFactory.getTraceLogger().debug(tag, msg);
    }

    public static void a(String tag, String msg, Throwable t) {
        LoggerFactory.getTraceLogger().warn(tag, msg, t);
    }

    public static void b(String tag, String msg) {
        LoggerFactory.getTraceLogger().error(tag, msg);
    }
}
