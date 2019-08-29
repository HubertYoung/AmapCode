package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import com.alipay.mobile.common.logging.api.LoggerFactory;

public class NativeLog {
    private static final boolean DEBUG = true;
    private static final boolean USE_ALIPAY_LOG = true;
    private static final boolean USE_SYSTEM_LOG = false;

    public static void Debug(String tag, String msg) {
        LoggerFactory.getTraceLogger().info(tag, msg);
    }
}
