package com.ali.user.mobile.info;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.ali.user.mobile.log.AliUserLog;
import com.taobao.accs.utl.UtilityImpl;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public final class NetWorkInfo {
    private static NetWorkInfo a;
    private final WifiManager b;

    private NetWorkInfo(Context context) {
        this.b = (WifiManager) context.getSystemService("wifi");
    }

    public static NetWorkInfo a(Context context) {
        synchronized (NetWorkInfo.class) {
            try {
                if (a == null) {
                    a = new NetWorkInfo(context);
                }
            }
        }
        return a;
    }

    public final String a() {
        WifiInfo connectionInfo = this.b.getConnectionInfo();
        if (connectionInfo == null) {
            return "";
        }
        return connectionInfo.getMacAddress();
    }

    public final String b() {
        WifiInfo connectionInfo = this.b.getConnectionInfo();
        if (connectionInfo == null) {
            return "";
        }
        return connectionInfo.getSSID();
    }

    public static String c() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (true) {
                    if (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement = inetAddresses.nextElement();
                        if (!nextElement.isLoopbackAddress()) {
                            return nextElement.getHostAddress().toString();
                        }
                    }
                }
            }
        } catch (Exception e) {
            AliUserLog.b((String) "WifiPreference IpAddress", e.toString());
        }
        return null;
    }

    public static String b(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        char c = 0;
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            int type = activeNetworkInfo.getType();
            if (type != 1) {
                if (type == 0 && activeNetworkInfo != null) {
                    switch (activeNetworkInfo.getSubtype()) {
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                        case 11:
                            c = 1;
                            break;
                        case 3:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                        case 12:
                        case 14:
                        case 15:
                            c = 2;
                            break;
                        case 13:
                            c = 4;
                            break;
                    }
                }
            } else {
                c = 3;
            }
        }
        switch (c) {
            case 1:
                return UtilityImpl.NET_TYPE_2G;
            case 2:
                return UtilityImpl.NET_TYPE_3G;
            case 3:
                return "wifi";
            case 4:
                return UtilityImpl.NET_TYPE_4G;
            default:
                return "";
        }
    }
}
