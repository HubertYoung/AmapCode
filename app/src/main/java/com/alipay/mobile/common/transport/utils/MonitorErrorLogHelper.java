package com.alipay.mobile.common.transport.utils;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.ExceptionID;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;

public final class MonitorErrorLogHelper {
    public static final int D = 2;
    public static final int E = 5;
    public static final int I = 3;
    public static final int V = 1;
    public static final int W = 4;
    private static int a = -1;

    public static final void log(String tag, Throwable e) {
        LoggerFactory.getMonitorLogger().exception(ExceptionID.MONITORPOINT_CONNECTERR, e);
        LoggerFactory.getTraceLogger().error(tag, e);
    }

    public static final void logExt(int level, String tag, Throwable e) {
        if (a == -1) {
            a = TransportConfigureManager.getInstance().getIntValue(TransportConfigureItem.ERROR_LOG_LEVEL);
        }
        LogCatUtil.debug("MonitorErrorLogHelper", "CONFIG_LOG_LEVEL=[" + a + "]");
        if (level >= a) {
            log(tag, e);
        }
    }

    public static final void verbose(String tag, Throwable e) {
        logExt(1, tag, e);
    }

    public static final void debug(String tag, Throwable e) {
        logExt(2, tag, e);
    }

    public static final void info(String tag, Throwable e) {
        logExt(3, tag, e);
    }

    public static final void warn(String tag, Throwable e) {
        logExt(4, tag, e);
    }

    public static final void error(String tag, Throwable e) {
        logExt(5, tag, e);
    }
}
