package com.alipay.mobile.antui.utils;

import android.content.Context;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleOS;
import java.lang.reflect.Method;

public class ConcaveUtils {
    public static boolean checkOppoConcave(Context context) {
        boolean hasNotchInScreen = context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
        if (!hasNotchInScreen) {
            return false;
        }
        boolean displayNotch = true;
        String pkgName = context.getPackageName();
        if (!TextUtils.isEmpty(pkgName)) {
            try {
                Class clazz = Class.forName("com.color.util.ColorDisplayCompatUtils");
                if (clazz != null) {
                    Object instance = clazz.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
                    Method method = clazz.getMethod("shouldNonImmersiveAdjustForPkg", new Class[]{String.class});
                    if (method != null) {
                        displayNotch = !((Boolean) method.invoke(instance, new Object[]{pkgName})).booleanValue();
                    }
                }
            } catch (Throwable e) {
                AuiLogger.error("checkOppoConcave", e.getMessage());
            }
        }
        if (!hasNotchInScreen || !displayNotch) {
            return false;
        }
        return true;
    }

    public static boolean checkVivoConcave() {
        try {
            Class clazz = Class.forName("android.util.FtFeature");
            Object obj = clazz.newInstance();
            return ((Boolean) clazz.getDeclaredMethod("isFeatureSupport", new Class[]{Integer.TYPE}).invoke(obj, new Object[]{Integer.valueOf(32)})).booleanValue();
        } catch (Throwable e) {
            AuiLogger.error("checkVivoConcave", e.getMessage());
            return false;
        }
    }

    public static boolean checkHuaweiConcave(Context context) {
        try {
            Class HwNotchSizeUtil = context.getClassLoader().loadClass("com.huawei.android.util.HwNotchSizeUtil");
            boolean hasNotchInScreen = ((Boolean) HwNotchSizeUtil.getMethod("hasNotchInScreen", new Class[0]).invoke(HwNotchSizeUtil, new Object[0])).booleanValue();
            boolean isDisplayNotch = isDisplayNotch(context);
            if (!hasNotchInScreen || !isDisplayNotch) {
                return false;
            }
            return true;
        } catch (Throwable e) {
            AuiLogger.error("checkHuaweiConcave", e.getMessage());
            return false;
        }
    }

    private static boolean isDisplayNotch(Context context) {
        if (Secure.getInt(context.getContentResolver(), AjxModuleOS.DISPLAY_NOTCH_STATUS, 0) == 0) {
            return true;
        }
        return false;
    }

    public static boolean checkXiaomiConcave() {
        String hasNotchInScreen = "0";
        try {
            Class c = Class.forName("android.os.SystemProperties");
            hasNotchInScreen = (String) c.getMethod("get", new Class[]{String.class, String.class}).invoke(c, new Object[]{"ro.miui.notch", "0"});
        } catch (Exception e) {
            AuiLogger.error("checkXiaomiConcave", e.getMessage());
        }
        return TextUtils.equals(hasNotchInScreen, "1");
    }
}
