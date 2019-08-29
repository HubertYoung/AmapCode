package com.autonavi.miniapp.plugin.lbs;

import android.app.Application;
import android.content.Context;
import android.os.Binder;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.support.v4.content.ContextCompat;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

public class LBSCommonUtil {
    private static final String GPS_SWITCH_CHECK_CONFIG_KEY = "gps_switch_check_config_key";
    private static final String TAG = "LBSCommonUtil";

    private static int getLocationPermission() {
        boolean isGpsSwitchOPen = isGpsSwitchOPen();
        boolean isAppPermissionOPen = isAppPermissionOPen();
        TraceLogger traceLogger = LoggerFactory.getTraceLogger();
        StringBuilder sb = new StringBuilder("gpsSwitchOpen=");
        sb.append(isGpsSwitchOPen);
        sb.append(",appSwitchOPen=");
        sb.append(isAppPermissionOPen);
        traceLogger.info(TAG, sb.toString());
        char c = 0;
        char c2 = isGpsSwitchOPen ? (char) 16 : 0;
        if (isGpsSwitchOPen && isAppPermissionOPen) {
            c = 256;
        }
        return (c | c2) | isAppPermissionOPen ? 1 : 0;
    }

    public static boolean hasLocationPermission() {
        return (getLocationPermission() >> 8) == 1;
    }

    public static boolean isAppPermissionOPen() {
        try {
            Application applicationContext = LauncherApplicationAgent.getInstance().getApplicationContext();
            if (applicationContext == null) {
                return false;
            }
            if (VERSION.SDK_INT < 23) {
                return isLessThanMarshmallowHasLocation(applicationContext);
            }
            if (ContextCompat.checkSelfPermission(applicationContext, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error((String) TAG, th);
        }
    }

    private static boolean isLessThanMarshmallowHasLocation(Context context) {
        if (VERSION.SDK_INT >= 19) {
            return isPermissionGranted(context, 0);
        }
        return true;
    }

    private static boolean isPermissionGranted(Context context, int i) {
        try {
            Object systemService = context.getSystemService("appops");
            if (systemService == null) {
                return false;
            }
            Method method = systemService.getClass().getMethod("checkOp", new Class[]{Integer.TYPE, Integer.TYPE, String.class});
            if (method == null) {
                return false;
            }
            if (((Integer) method.invoke(systemService, new Object[]{Integer.valueOf(i), Integer.valueOf(Binder.getCallingUid()), context.getPackageName()})).intValue() == 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isGpsSwitchOPen() {
        Application applicationContext = LauncherApplicationAgent.getInstance().getApplicationContext();
        TraceLogger traceLogger = LoggerFactory.getTraceLogger();
        StringBuilder sb = new StringBuilder("Build.VERSION.SDK_INT=");
        sb.append(VERSION.SDK_INT);
        traceLogger.info(TAG, sb.toString());
        if (VERSION.SDK_INT >= 24) {
            try {
                if (Secure.getInt(applicationContext.getContentResolver(), "location_mode", 0) != 0) {
                    return true;
                }
                return false;
            } catch (Throwable th) {
                LoggerFactory.getTraceLogger().error((String) TAG, "isGpsSwitchOPen, error,msg=".concat(String.valueOf(th)));
            }
        }
        return true;
    }

    public static double formatDouble(double d, int i) {
        return Double.parseDouble(new DecimalFormat(getPattern(i)).format(d));
    }

    private static String getPattern(int i) {
        StringBuilder sb = new StringBuilder("#.");
        while (i > 0) {
            sb.append("0");
            i--;
        }
        return sb.toString();
    }
}
