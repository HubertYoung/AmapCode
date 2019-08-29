package com.sina.deviceidjnisdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;

public class DeviceInfo {
    @SuppressLint({"MissingPermission"})
    public static String getDeviceId(Context context) {
        String str;
        String str2 = null;
        if (isPermissionGranted(context, "android.permission.READ_PHONE_STATE")) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager == null) {
                    return null;
                }
                if (VERSION.SDK_INT >= 23) {
                    str = telephonyManager.getDeviceId(0);
                } else {
                    str = telephonyManager.getDeviceId();
                }
                if (str == null) {
                    str = "";
                }
                str2 = str;
            } catch (Exception e) {
                e.printStackTrace();
                str2 = "";
            }
        }
        return str2;
    }

    public static String getMacAddress(Context context) {
        String str = null;
        if (isPermissionGranted(context, "android.permission.ACCESS_WIFI_STATE")) {
            try {
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
                if (wifiManager == null) {
                    return null;
                }
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (connectionInfo == null) {
                    return null;
                }
                str = connectionInfo.getMacAddress();
            } catch (Exception unused) {
                str = "";
            }
        }
        return str;
    }

    public static String getImei(Context context) {
        return getDeviceId(context);
    }

    @SuppressLint({"MissingPermission"})
    public static String getImsi(Context context) {
        String str = "";
        if (isPermissionGranted(context, "android.permission.READ_PHONE_STATE")) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager == null) {
                return null;
            }
            str = telephonyManager.getSubscriberId();
        }
        if (str == null) {
            str = "";
        }
        return str;
    }

    private static boolean isPermissionGranted(Context context, String str) {
        return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
    }
}
