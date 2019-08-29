package com.alipay.android.phone.mobilesdk.socketcraft.platform.logcat;

import com.alipay.android.phone.mobilesdk.socketcraft.platform.PlatformUtil;

public class SCLogCatFactory {
    private static SCLogCatInterface SOSCKT_CRAFT_LOG = null;

    public static void setLogImpl(SCLogCatInterface nbNetLog) {
        SOSCKT_CRAFT_LOG = nbNetLog;
    }

    public static SCLogCatInterface getSCLog() {
        if (SOSCKT_CRAFT_LOG != null) {
            return SOSCKT_CRAFT_LOG;
        }
        synchronized (SCLogCatFactory.class) {
            try {
                if (SOSCKT_CRAFT_LOG != null) {
                    SCLogCatInterface sCLogCatInterface = SOSCKT_CRAFT_LOG;
                    return sCLogCatInterface;
                }
                if (!PlatformUtil.isAndroidPlatform()) {
                    SOSCKT_CRAFT_LOG = PlatformUtil.createJavaLogImpl();
                } else if (PlatformUtil.isAndroidMPaaSPlatform()) {
                    SOSCKT_CRAFT_LOG = PlatformUtil.createAndroidMPaaSLogImpl();
                } else {
                    SOSCKT_CRAFT_LOG = PlatformUtil.createAndroidLogImpl();
                }
                SCLogCatInterface sCLogCatInterface2 = SOSCKT_CRAFT_LOG;
                return sCLogCatInterface2;
            }
        }
    }
}
