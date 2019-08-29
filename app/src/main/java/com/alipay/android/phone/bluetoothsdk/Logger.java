package com.alipay.android.phone.bluetoothsdk;

import com.alipay.mobile.common.logging.api.LoggerFactory;

public class Logger {
    public static final String TAG = "[APBLE]";

    public static void d(String tag, String msg) {
        LoggerFactory.getTraceLogger().debug(new StringBuilder(TAG).append(tag).toString(), msg);
    }

    public static void e(String tag, String msg) {
        LoggerFactory.getTraceLogger().error(new StringBuilder(TAG).append(tag).toString(), msg);
    }
}
