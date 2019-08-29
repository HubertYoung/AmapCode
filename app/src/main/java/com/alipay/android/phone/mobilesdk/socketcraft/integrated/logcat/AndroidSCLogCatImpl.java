package com.alipay.android.phone.mobilesdk.socketcraft.integrated.logcat;

import android.os.Process;
import android.util.Log;
import com.alipay.android.phone.mobilesdk.socketcraft.platform.logcat.SCLogCatInterface;

public class AndroidSCLogCatImpl implements SCLogCatInterface {
    public void info(String tag, String msg) {
        Log.i(tag, getPidAndTid() + msg);
    }

    public void verbose(String tag, String msg) {
        Log.v(tag, getPidAndTid() + msg);
    }

    public void debug(String tag, String msg) {
        Log.d(tag, getPidAndTid() + msg);
    }

    public void warn(String tag, String msg) {
        Log.w(tag, getPidAndTid() + msg);
    }

    public void warn(String tag, Throwable throwable) {
        Log.w(tag, throwable);
    }

    public void warn(String tag, String msg, Throwable throwable) {
        Log.w(tag, getPidAndTid() + msg, throwable);
    }

    public void error(String tag, String msg) {
        Log.e(tag, getPidAndTid() + msg);
    }

    public void error(String tag, Throwable throwable) {
        Log.e(tag, getPidAndTid(), throwable);
    }

    public void error(String tag, String msg, Throwable throwable) {
        Log.e(tag, getPidAndTid() + msg, throwable);
    }

    public void printInfo(String tag, String msg) {
        Log.i(tag, getPidAndTid() + msg);
    }

    public void printError(String tag, Throwable e) {
        Log.e(tag, getPidAndTid(), e);
    }

    private static final String getPidAndTid() {
        return " [" + Process.myPid() + ":" + Thread.currentThread().getId() + "] ";
    }
}
