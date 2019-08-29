package com.alipay.mobile.common.transport.utils;

import com.alipay.mobile.common.netsdkextdependapi.logger.LoggerManager;
import com.alipay.mobile.common.netsdkextdependapi.logger.LoggerManagerFactory;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;

public final class LogCatUtil {
    public static final void info(String tag, String log) {
        a().info("mynet_" + tag, log);
    }

    public static final void verbose(String tag, String log) {
        a().verbose("mynet_" + tag, log);
    }

    public static final void debug(String tag, String log) {
        a().debug("mynet_" + tag, log);
    }

    public static final void warn(String tag, String log) {
        a().warn("mynet_" + tag, log);
    }

    public static final void warn(String tag, Throwable throwable) {
        a().warn("mynet_" + tag, throwable);
    }

    public static final void warn(String tag, String msg, Throwable throwable) {
        a().warn("mynet_" + tag, msg, throwable);
    }

    public static final void error(String tag, String log) {
        a().error("mynet_" + tag, log);
    }

    public static final void error(String tag, Throwable throwable) {
        a().error("mynet_" + tag, throwable);
    }

    public static final void error(String tag, String log, Throwable throwable) {
        a().error("mynet_" + tag, log, throwable);
    }

    public static final void printInfo(String tag, String msg) {
        a().printInfo("mynet_" + tag, msg);
    }

    public static final void printError(String tag, Throwable e) {
        a().printError("mynet_" + tag, e);
    }

    public static final void debugOrLose(String tag, String log) {
        if (TransportConfigureManager.getInstance().getIntValue(TransportConfigureItem.LOG_PRIO_SWITCH) > 3) {
            a().debug("mynet_" + tag, log);
        } else {
            a().printInfo("mynet_" + tag, log);
        }
    }

    private static LoggerManager a() {
        return (LoggerManager) LoggerManagerFactory.getInstance().getDefaultBean();
    }
}
