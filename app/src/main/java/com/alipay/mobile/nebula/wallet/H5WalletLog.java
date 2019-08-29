package com.alipay.mobile.nebula.wallet;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5WalletLog {
    public static void debug(String tag, String log) {
        if (H5Utils.isDebuggable(H5Utils.getContext())) {
            LoggerFactory.getTraceLogger().debug(tag, log);
        }
    }

    public static void e(String tag, String log, Throwable t) {
        LoggerFactory.getTraceLogger().error(tag, log, t);
    }

    public static void w(String tag, String log, Throwable throwable) {
        LoggerFactory.getTraceLogger().warn(tag, log, throwable);
    }

    public static void d(String tag, String log) {
        LoggerFactory.getTraceLogger().debug(tag, log);
    }
}
