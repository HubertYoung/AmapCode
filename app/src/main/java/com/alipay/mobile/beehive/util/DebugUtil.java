package com.alipay.mobile.beehive.util;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;

public class DebugUtil {
    public static boolean isDebug() {
        return LoggingUtil.isDebuggable(LauncherApplicationAgent.getInstance().getApplicationContext());
    }

    public static void log(String tag, String msg) {
        if (isDebug()) {
            LoggerFactory.getTraceLogger().info(tag, msg);
        }
    }

    public static void log(String tag, Object... msg) {
        if (msg != null && msg.length > 0 && isDebug()) {
            String s = "";
            for (Object v : msg) {
                s = s + MiscUtil.safeToString(v);
            }
            LoggerFactory.getTraceLogger().info(tag, s);
        }
    }
}
