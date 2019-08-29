package com.ta.audid.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build.VERSION;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;

public class AppInfoUtils {
    public static int getTargetSdkVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.targetSdkVersion;
        } catch (Exception e) {
            UtdidLogger.e("", e, new Object[0]);
            return 0;
        }
    }

    public static String getAppPackageName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        return packageInfo != null ? packageInfo.packageName : "";
    }

    public static String getAppVersionName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        return packageInfo != null ? packageInfo.versionName : "";
    }

    private static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384);
        } catch (Exception e) {
            UtdidLogger.e("", e, new Object[0]);
            return null;
        }
    }

    public static String getCurProcessName(Context context) {
        try {
            int myPid = Process.myPid();
            for (RunningAppProcessInfo next : ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses()) {
                if (next.pid == myPid) {
                    return next.processName;
                }
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static boolean isMainProcess(Context context) {
        String curProcessName = getCurProcessName(context);
        String appPackageName = getAppPackageName(context);
        if (TextUtils.isEmpty(curProcessName) || TextUtils.isEmpty(appPackageName) || curProcessName.equals(appPackageName)) {
            return true;
        }
        return false;
    }

    public static boolean isMainLooper() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static Boolean isBelowMVersion() {
        if (VERSION.SDK_INT < 23) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
