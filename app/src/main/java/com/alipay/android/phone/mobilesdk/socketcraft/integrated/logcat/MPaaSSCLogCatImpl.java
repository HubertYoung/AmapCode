package com.alipay.android.phone.mobilesdk.socketcraft.integrated.logcat;

import com.alipay.android.phone.mobilesdk.socketcraft.platform.logcat.SCLogCatInterface;
import com.alipay.mobile.common.transport.utils.LogCatUtil;

public class MPaaSSCLogCatImpl implements SCLogCatInterface {
    public void info(String tag, String msg) {
        LogCatUtil.info(tag, msg);
    }

    public void verbose(String tag, String msg) {
        LogCatUtil.verbose(tag, msg);
    }

    public void debug(String tag, String msg) {
        LogCatUtil.debug(tag, msg);
    }

    public void warn(String tag, String msg) {
        LogCatUtil.warn(tag, msg);
    }

    public void warn(String tag, Throwable throwable) {
        LogCatUtil.warn(tag, throwable);
    }

    public void warn(String tag, String msg, Throwable throwable) {
        LogCatUtil.warn(tag, msg, throwable);
    }

    public void error(String tag, String msg) {
        LogCatUtil.error(tag, msg);
    }

    public void error(String tag, Throwable throwable) {
        LogCatUtil.error(tag, throwable);
    }

    public void error(String tag, String msg, Throwable throwable) {
        LogCatUtil.error(tag, msg, throwable);
    }

    public void printInfo(String tag, String msg) {
        LogCatUtil.printInfo(tag, msg);
    }

    public void printError(String tag, Throwable e) {
        LogCatUtil.printError(tag, e);
    }
}
