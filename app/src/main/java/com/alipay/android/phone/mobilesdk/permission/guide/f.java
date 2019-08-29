package com.alipay.android.phone.mobilesdk.permission.guide;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.ReflectUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.mpaas.nebula.plugin.H5ServicePlugin;

/* compiled from: Reflect */
public final class f {
    public static boolean a() {
        Object shortCutService = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface("com.alipay.mobile.framework.service.ext.ShortCutService");
        try {
            boolean shortcut = ((Boolean) ReflectUtil.invokeMethod(shortCutService.getClass(), "isSupportInstallDesktopShortCut", null, shortCutService, null)).booleanValue();
            LoggerFactory.getTraceLogger().info("Permissions", "ShortCutService.isSupportInstallDesktopShortCut(): " + shortcut);
            return shortcut;
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
            return false;
        }
    }

    public static String a(String key) {
        Object configService = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface("com.alipay.mobile.base.config.ConfigService");
        try {
            String v = (String) ReflectUtil.invokeMethod(configService.getClass(), H5ServicePlugin.GET_CONFIG, new Class[]{String.class}, configService, new Object[]{key});
            LoggerFactory.getTraceLogger().info("Permissions", "ConfigService.getConfigValue(key=" + key + "), value=" + v);
            if (TextUtils.isEmpty(v)) {
                return "";
            }
            return v;
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
            return "";
        }
    }

    public static int b() {
        int notificationPermission = PermissionStatus.NOT_SURE.getValue();
        try {
            Object monitorContext = ReflectUtil.invokeMethod("com.alipay.mobile.monitor.api.MonitorFactory", "getMonitorContext", null, null, null);
            return ((Integer) ReflectUtil.invokeMethod(monitorContext.getClass(), "notificationWhitelistStatus", null, monitorContext, null)).intValue();
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
            return notificationPermission;
        }
    }

    public static int c() {
        int selfStartingPermission = PermissionStatus.NOT_SURE.getValue();
        try {
            Object monitorContext = ReflectUtil.invokeMethod("com.alipay.mobile.monitor.api.MonitorFactory", "getMonitorContext", null, null, null);
            return ((Integer) ReflectUtil.invokeMethod(monitorContext.getClass(), "autoStartWhitelistStatus", null, monitorContext, null)).intValue();
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
            return selfStartingPermission;
        }
    }

    public static int d() {
        int keepAlive = PermissionStatus.NOT_SURE.getValue();
        try {
            Object monitorContext = ReflectUtil.invokeMethod("com.alipay.mobile.monitor.api.MonitorFactory", "getMonitorContext", null, null, null);
            return ((Integer) ReflectUtil.invokeMethod(monitorContext.getClass(), "keepAliveWhitelistStatus", null, monitorContext, null)).intValue();
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
            return keepAlive;
        }
    }

    public static int a(Context context) {
        int healthPermission = PermissionStatus.NOT_SURE.getValue();
        try {
            Object instance = ReflectUtil.invokeMethod("com.alipay.mobile.healthcommon.stepcounter.APMainStepManager", "getInstance", new Class[]{Context.class}, null, new Object[]{context});
            return ((Integer) ReflectUtil.invokeMethod(instance.getClass(), "motionPermissionStatus", null, instance, null)).intValue();
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
            return healthPermission;
        }
    }
}
