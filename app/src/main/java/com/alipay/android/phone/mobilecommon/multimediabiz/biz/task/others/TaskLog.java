package com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.others;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;

public class TaskLog {
    public static void V(String tag, String format, Object... args) {
        Logger.V(tag, format, args);
    }

    public static void D(String tag, String format, Object... args) {
        Logger.D(tag, format, args);
    }

    public static void W(String tag, String format, Object... args) {
        Logger.W(tag, format, args);
    }

    public static void I(String tag, String format, Object... args) {
        Logger.I(tag, format, args);
    }

    public static void P(String tag, String format, Object... args) {
        Logger.P(tag, format, args);
    }

    public static void E(String tag, Throwable e, String format, Object... args) {
        Logger.E(tag, e, format, args);
    }
}
