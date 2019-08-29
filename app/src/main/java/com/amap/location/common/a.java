package com.amap.location.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.amap.location.common.f.h;
import com.amap.location.sdk.fusion.LocationParams;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;

/* compiled from: DeviceInfo */
public class a {
    public static C0013a a = null;
    public static b b = null;
    private static volatile String c = null;
    private static volatile String d = null;
    private static volatile String e = null;
    private static volatile String f = null;
    private static volatile String g = null;
    private static volatile String h = null;
    private static volatile boolean i = true;

    /* renamed from: com.amap.location.common.a$a reason: collision with other inner class name */
    /* compiled from: DeviceInfo */
    public interface C0013a {
        String a();
    }

    /* compiled from: DeviceInfo */
    public interface b {
        String a(Context context);
    }

    public static String a(Context context) {
        if (c == null && i) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService("phone");
                if (VERSION.SDK_INT >= 26) {
                    c = telephonyManager.getImei();
                } else {
                    c = telephonyManager.getDeviceId();
                }
            } catch (Throwable unused) {
            }
            if (c == null) {
                c = "";
            }
        }
        return c == null ? "" : c;
    }

    public static void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            c = str;
        }
    }

    public static String b(Context context) {
        if (d == null) {
            try {
                if (i) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("sp_common", 0);
                    String b2 = com.amap.location.common.f.a.b(sharedPreferences.getString("tid", null));
                    if (!TextUtils.isEmpty(b2) || b == null) {
                        d = b2;
                    } else {
                        String a2 = b.a(context);
                        if (!TextUtils.isEmpty(a2)) {
                            sharedPreferences.edit().putString("tid", com.amap.location.common.f.a.a(a2)).apply();
                            d = a2;
                        } else {
                            d = "";
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
        return d == null ? "" : d;
    }

    public static void a(Context context, String str) {
        if (!TextUtils.isEmpty(str) && !str.equals(d)) {
            d = str;
            if (context != null) {
                try {
                    context.getSharedPreferences("sp_common", 0).edit().putString("tid", com.amap.location.common.f.a.a(str)).apply();
                } catch (Exception unused) {
                }
            }
        }
    }

    public static String a() {
        if (TextUtils.isEmpty(e)) {
            try {
                if (a != null) {
                    e = a.a();
                }
            } catch (Exception unused) {
            }
        }
        return e == null ? "" : e;
    }

    public static void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            e = str;
        }
    }

    public static String c(Context context) {
        if (TextUtils.isEmpty(e)) {
            try {
                e = com.amap.location.common.f.a.b(context.getSharedPreferences("sp_common", 0).getString(LocationParams.PARA_COMMON_ADIU, null));
            } catch (Exception unused) {
            }
        }
        return e;
    }

    public static void b(Context context, String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                context.getSharedPreferences("sp_common", 0).edit().putString(LocationParams.PARA_COMMON_ADIU, com.amap.location.common.f.a.a(str)).apply();
            } catch (Exception unused) {
            }
            e = str;
        }
    }

    public static String d(Context context) {
        if (f == null) {
            try {
                f = ((TelephonyManager) context.getApplicationContext().getSystemService("phone")).getSubscriberId();
            } catch (Exception | SecurityException unused) {
            }
            if (f == null) {
                f = "";
            }
        }
        return f == null ? "" : f;
    }

    public static String b() {
        return Build.MODEL == null ? "" : Build.MODEL;
    }

    public static String c() {
        return Build.MANUFACTURER == null ? "" : Build.MANUFACTURER;
    }

    public static int d() {
        return VERSION.SDK_INT;
    }

    public static String e() {
        if (g == null) {
            try {
                String serial = VERSION.SDK_INT >= 26 ? Build.getSerial() : Build.SERIAL;
                g = serial;
                if (serial == null || g.equalsIgnoreCase("unknown")) {
                    g = "";
                }
            } catch (SecurityException unused) {
            }
        }
        return g == null ? "" : g;
    }

    public static long e(Context context) {
        return h.a(f(context));
    }

    public static String f(Context context) {
        if (!TextUtils.isEmpty(h)) {
            return h;
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager != null) {
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (connectionInfo != null) {
                    String macAddress = connectionInfo.getMacAddress();
                    if (VERSION.SDK_INT >= 23 && macAddress != null && macAddress.equals("02:00:00:00:00:00")) {
                        macAddress = f();
                    }
                    if (macAddress != null && macAddress.length() > 0) {
                        String replace = macAddress.replace(":", "");
                        if (replace != null && replace.length() > 0) {
                            h = replace;
                        }
                        return replace;
                    }
                }
            }
        } catch (Exception | SecurityException unused) {
        }
        return "";
    }

    private static String f() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces != null) {
                for (T t : Collections.list(networkInterfaces)) {
                    if (t.getName().equalsIgnoreCase("wlan0")) {
                        byte[] bArr = null;
                        if (VERSION.SDK_INT >= 9) {
                            bArr = t.getHardwareAddress();
                        }
                        if (bArr == null) {
                            return "";
                        }
                        StringBuilder sb = new StringBuilder();
                        for (byte valueOf : bArr) {
                            sb.append(String.format("%02X:", new Object[]{Byte.valueOf(valueOf)}));
                        }
                        if (sb.length() > 0) {
                            sb.deleteCharAt(sb.length() - 1);
                        }
                        return sb.toString();
                    }
                }
            }
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        }
        return "";
    }
}
