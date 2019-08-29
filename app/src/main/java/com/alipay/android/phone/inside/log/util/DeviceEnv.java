package com.alipay.android.phone.inside.log.util;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Process;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.HashMap;
import java.util.Map;

public class DeviceEnv {
    public static String a = "wifi_name";
    public static String b = "mac_address";
    private static NetConnectionType c = NetConnectionType.NETWORK_TYPE_16;
    private static long d;

    public static Map<String, String> b(Context context) {
        HashMap hashMap = new HashMap();
        try {
            WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
            hashMap.put(a, connectionInfo.getSSID());
            hashMap.put(b, connectionInfo.getMacAddress());
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        return hashMap;
    }

    public static String c(Context context) {
        try {
            return Integer.toString(((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getProcessMemoryInfo(new int[]{Process.myPid()})[0].getTotalPrivateDirty());
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            r4 = "";
            return "";
        }
    }

    public static String a() {
        return Integer.toString(Process.myPid());
    }

    public static String a(Context context) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - d >= 15000) {
                d = currentTimeMillis;
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.getType() == 0) {
                    c = NetConnectionType.getTypeByCode(activeNetworkInfo.getSubtype());
                } else if (activeNetworkInfo == null || activeNetworkInfo.getType() != 1) {
                    c = NetConnectionType.NETWORK_TYPE_16;
                } else {
                    c = NetConnectionType.WIFI;
                }
            }
            return c.getName();
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            r7 = "";
            return "";
        }
    }
}
