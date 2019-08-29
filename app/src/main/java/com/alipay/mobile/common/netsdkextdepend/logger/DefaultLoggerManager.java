package com.alipay.mobile.common.netsdkextdepend.logger;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.netsdkextdependapi.logger.LoggerManagerAdapter;

public class DefaultLoggerManager extends LoggerManagerAdapter {
    public void info(String tag, String log) {
        LoggerFactory.getTraceLogger().info(tag, log);
    }

    public void verbose(String tag, String log) {
        LoggerFactory.getTraceLogger().verbose(tag, log);
    }

    public void debug(String tag, String log) {
        LoggerFactory.getTraceLogger().debug(tag, log);
    }

    public void warn(String tag, String log) {
        LoggerFactory.getTraceLogger().warn(tag, log);
    }

    public void warn(String tag, Throwable throwable) {
        LoggerFactory.getTraceLogger().warn(tag, throwable);
    }

    public void warn(String tag, String msg, Throwable throwable) {
        LoggerFactory.getTraceLogger().warn(tag, msg, throwable);
    }

    public void error(String tag, String log) {
        LoggerFactory.getTraceLogger().error(tag, log);
    }

    public void error(String tag, Throwable throwable) {
        LoggerFactory.getTraceLogger().error(tag, throwable);
    }

    public void error(String tag, String log, Throwable throwable) {
        LoggerFactory.getTraceLogger().error(tag, log, throwable);
    }

    public void printInfo(String tag, String msg) {
        LoggerFactory.getTraceLogger().print(tag, msg);
    }

    public void printError(String tag, Throwable e) {
        LoggerFactory.getTraceLogger().print(tag, e);
    }
}
