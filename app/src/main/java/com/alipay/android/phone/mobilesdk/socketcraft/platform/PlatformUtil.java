package com.alipay.android.phone.mobilesdk.socketcraft.platform;

import com.alipay.android.phone.mobilesdk.socketcraft.platform.logcat.SCLogCatInterface;
import com.alipay.android.phone.mobilesdk.socketcraft.platform.monitor.MonitorPrinter;
import com.alipay.mobile.common.socketcraft.build.BuildConfig;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PlatformUtil {
    private static Class ANDROID_BUILD_VERSION = null;
    private static final String ANDROID_LOG_IMPL = "com.alipay.android.phone.mobilesdk.socketcraft.integrated.logcat.AndroidSCLogCatImpl";
    private static final String JAVA_LOG_IMPL = "com.alipay.android.phone.mobilesdk.socketcraft.platform.logcat.JavaSCLogCatImpl";
    private static final String LOG_IMPL_PACKAGE = "com.alipay.android.phone.mobilesdk.socketcraft.integrated.logcat.";
    private static Class MPAAS_LOG_CAT = null;
    private static final String MPAAS_LOG_IMPL = "com.alipay.android.phone.mobilesdk.socketcraft.integrated.logcat.MPaaSSCLogCatImpl";
    private static final String MPAAS_MONITOR_PRINTER_CLASS = "com.alipay.android.phone.mobilesdk.socketcraft.integrated.monitor.MPaaSMonitorPrinter";
    private static final Logger log = Logger.getLogger(PlatformUtil.class.getName());

    static {
        Logger.getLogger(BuildConfig.APPLICATION_ID).setLevel(Level.ALL);
    }

    public static final boolean isAndroidPlatform() {
        if (ANDROID_BUILD_VERSION != null) {
            return true;
        }
        log.info("enter isAndroidPlatform");
        try {
            Class androidBuildClass = Class.forName("android.os.Build$VERSION");
            if (androidBuildClass != null && ((Integer) androidBuildClass.getField("SDK_INT").get(androidBuildClass)).intValue() > 0) {
                return true;
            }
        } catch (Throwable e) {
            log.log(Level.INFO, String.format("isAndroidPlatform err: %s", new Object[]{e.getMessage()}));
        }
        return false;
    }

    public static final boolean isAndroidMPaaSPlatform() {
        if (!isAndroidPlatform()) {
            return false;
        }
        if (MPAAS_LOG_CAT != null) {
            return true;
        }
        log.info("enter isAndroidMPaaSPlatform");
        try {
            MPAAS_LOG_CAT = Class.forName("com.alipay.mobile.common.transport.utils.LogCatUtil");
            return true;
        } catch (Throwable e) {
            log.log(Level.SEVERE, "isAndroidMPaaSPlatform err", e);
            return false;
        }
    }

    public static final SCLogCatInterface createJavaLogImpl() {
        try {
            log.info("enter createJavaLogImpl");
            return (SCLogCatInterface) Class.forName(JAVA_LOG_IMPL).newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static final SCLogCatInterface createAndroidLogImpl() {
        try {
            log.info("enter SCLogCatInterface");
            return (SCLogCatInterface) Class.forName(ANDROID_LOG_IMPL).newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static final SCLogCatInterface createAndroidMPaaSLogImpl() {
        try {
            log.info("enter createAndroidMPaaSLogImpl");
            return (SCLogCatInterface) Class.forName(MPAAS_LOG_IMPL).newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static final MonitorPrinter createMPaaSMonitorPrinter() {
        try {
            log.info("enter createMPaaSMonitorPrinter");
            return (MonitorPrinter) Class.forName(MPAAS_MONITOR_PRINTER_CLASS).newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
