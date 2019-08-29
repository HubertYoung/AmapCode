package com.alipay.mobile.common.nbnet.biz.platform;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PlatformUtil {
    private static final Logger a = Logger.getLogger(PlatformUtil.class.getName());
    private static Class b = null;
    private static Class c = null;

    private static boolean b() {
        if (b != null) {
            return true;
        }
        a.info("enter isAndroidPlatform");
        try {
            Class androidBuildClass = Class.forName("android.os.Build$VERSION");
            if (androidBuildClass != null && ((Integer) androidBuildClass.getField("SDK_INT").get(androidBuildClass)).intValue() > 0) {
                return true;
            }
        } catch (Throwable e) {
            a.log(Level.INFO, String.format("isAndroidPlatform err: %s", new Object[]{e.getMessage()}));
        }
        return false;
    }

    public static final boolean a() {
        if (!b()) {
            return false;
        }
        if (c != null) {
            return true;
        }
        a.info("enter isAndroidMPaaSPlatform");
        try {
            c = Class.forName("com.alipay.mobile.common.transport.utils.LogCatUtil");
            return true;
        } catch (Throwable e) {
            a.log(Level.SEVERE, "isAndroidMPaaSPlatform err", e);
            return false;
        }
    }
}
