package com.alipay.android.phone.mobilesdk.socketcraft.platform.logcat;

public final class SCLogCatUtil {
    public static final void info(String tag, String log) {
        getSCLog().info("WS_" + tag, log);
    }

    public static final void verbose(String tag, String log) {
        getSCLog().verbose("WS_" + tag, log);
    }

    public static final void debug(String tag, String log) {
        getSCLog().debug("WS_" + tag, log);
    }

    public static final void warn(String tag, String log) {
        getSCLog().warn("WS_" + tag, log);
    }

    public static final void warn(String tag, Throwable throwable) {
        getSCLog().warn("WS_" + tag, throwable);
    }

    public static final void warn(String tag, String msg, Throwable throwable) {
        getSCLog().warn("WS_" + tag, msg, throwable);
    }

    public static final void error(String tag, String log) {
        getSCLog().error("WS_" + tag, log);
    }

    public static final void error(String tag, Throwable throwable) {
        getSCLog().error("WS_" + tag, throwable);
    }

    public static final void error(String tag, String log, Throwable throwable) {
        getSCLog().error("WS_" + tag, log, throwable);
    }

    public static final void printInfo(String tag, String msg) {
        getSCLog().printInfo("WS_" + tag, msg);
    }

    public static final void printError(String tag, Throwable e) {
        getSCLog().printError("WS_" + tag, e);
    }

    private static SCLogCatInterface getSCLog() {
        return SCLogCatFactory.getSCLog();
    }
}
