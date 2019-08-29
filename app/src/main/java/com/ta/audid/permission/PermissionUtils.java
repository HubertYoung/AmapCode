package com.ta.audid.permission;

import android.content.Context;
import android.os.Build.VERSION;
import com.ta.audid.utils.AppInfoUtils;

public class PermissionUtils {
    public static boolean checkStoragePermissionGranted(Context context) {
        return selfPermissionGranted(context, "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    public static boolean checkReadPhoneStatePermissionGranted(Context context) {
        return selfPermissionGranted(context, "android.permission.READ_PHONE_STATE");
    }

    public static boolean checkWifiStatePermissionGranted(Context context) {
        return selfPermissionGranted(context, "android.permission.ACCESS_WIFI_STATE");
    }

    public static boolean selfPermissionGranted(Context context, String str) {
        boolean z = false;
        if (context == null) {
            return false;
        }
        int targetSdkVersion = AppInfoUtils.getTargetSdkVersion(context);
        if (VERSION.SDK_INT < 23 ? PermissionChecker.checkSelfPermission(context, str) == 0 : !(targetSdkVersion < 23 ? PermissionChecker.checkSelfPermission(context, str) != 0 : context.checkSelfPermission(str) != 0)) {
            z = true;
        }
        return z;
    }
}
