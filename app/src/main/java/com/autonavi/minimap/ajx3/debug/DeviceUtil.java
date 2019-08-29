package com.autonavi.minimap.ajx3.debug;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

public class DeviceUtil {
    public static String getDiu(Context context) {
        try {
            if (ContextCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE") == 0) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    String deviceId = telephonyManager.getDeviceId();
                    if (deviceId == null) {
                        deviceId = "0";
                    }
                    return deviceId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo != null) {
                return packageInfo.versionName;
            }
        } catch (NameNotFoundException unused) {
        }
        return "";
    }
}
