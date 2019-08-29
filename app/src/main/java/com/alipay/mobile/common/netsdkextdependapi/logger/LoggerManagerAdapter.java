package com.alipay.mobile.common.netsdkextdependapi.logger;

import android.util.Log;

public class LoggerManagerAdapter implements LoggerManager {
    public void info(String tag, String log) {
        Log.i(tag, log);
    }

    public void verbose(String tag, String log) {
        Log.v(tag, log);
    }

    public void debug(String tag, String log) {
        Log.d(tag, log);
    }

    public void warn(String tag, String log) {
        Log.w(tag, log);
    }

    public void warn(String tag, Throwable throwable) {
        Log.w(tag, "", throwable);
    }

    public void warn(String tag, String msg, Throwable throwable) {
        Log.w(tag, msg, throwable);
    }

    public void error(String tag, String log) {
        Log.e(tag, log);
    }

    public void error(String tag, Throwable throwable) {
        Log.e(tag, "", throwable);
    }

    public void error(String tag, String log, Throwable throwable) {
        Log.e(tag, log, throwable);
    }

    public void printInfo(String tag, String msg) {
        Log.i(tag, msg);
    }

    public void printError(String tag, Throwable e) {
        Log.e(tag, "", e);
    }
}
