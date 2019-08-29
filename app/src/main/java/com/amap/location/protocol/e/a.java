package com.amap.location.protocol.e;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.SparseArray;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Iterator;

/* compiled from: DeviceUtils */
public class a {
    public static String a = "";
    private static final SparseArray<String> b;
    private static String c = "";
    private static String d = "";
    private static String e = "";
    private static String f = "";
    private static int g;

    static {
        SparseArray<String> sparseArray = new SparseArray<>();
        b = sparseArray;
        sparseArray.append(0, "UNKWN");
        b.append(1, "GPRS");
        b.append(2, "EDGE");
        b.append(3, "UMTS");
        b.append(4, "CDMA");
        b.append(5, "EVDO_0");
        b.append(6, "EVDO_A");
        b.append(7, "1xRTT");
        b.append(8, "HSDPA");
        b.append(9, "HSUPA");
        b.append(10, "HSPA");
        b.append(11, "IDEN");
        b.append(12, "EVDO_B");
        b.append(13, "LTE");
        b.append(14, "EHRPD");
        b.append(15, "HSPAP");
        b.append(16, "GSM");
        b.append(17, "TD_SCDMA");
        b.append(18, "IWLAN");
    }

    public static boolean a(int i) {
        StringBuilder sb = new StringBuilder(",");
        sb.append(i);
        sb.append(",");
        return ",111,123,134,199,202,204,206,208,212,213,214,216,218,219,220,222,225,226,228,230,231,232,234,235,238,240,242,244,246,247,248,250,255,257,259,260,262,266,268,270,272,274,276,278,280,282,283,284,286,288,289,290,292,293,294,295,297,302,308,310,311,312,313,314,315,316,310,330,332,334,338,340,342,344,346,348,350,352,354,356,358,360,362,363,364,365,366,368,370,372,374,376,400,401,402,404,405,406,410,412,413,414,415,416,417,418,419,420,421,422,424,425,426,427,428,429,430,431,432,434,436,437,438,440,441,450,452,454,455,456,457,466,467,470,472,502,505,510,514,515,520,525,528,530,534,535,536,537,539,540,541,542,543,544,545,546,547,548,549,550,551,552,553,555,560,598,602,603,604,605,606,607,608,609,610,611,612,613,614,615,616,617,618,619,620,621,622,623,624,625,626,627,628,629,630,631,632,633,634,635,636,637,638,639,640,641,642,643,645,646,647,648,649,650,651,652,653,654,655,657,659,665,702,704,706,708,710,712,714,716,722,724,730,732,734,736,738,740,742,744,746,748,750,850,901,".contains(sb.toString());
    }

    public static String a() {
        String str = Build.MODEL;
        return str == null ? "" : str;
    }

    public static String b() {
        if (TextUtils.isEmpty(c)) {
            StringBuilder sb = new StringBuilder("android");
            sb.append(VERSION.RELEASE);
            c = sb.toString();
        }
        return c;
    }

    public static String a(Context context) {
        if (TextUtils.isEmpty(d)) {
            try {
                String packageName = context.getPackageName();
                PackageInfo packageInfo = null;
                try {
                    packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                StringBuilder sb = new StringBuilder();
                if (packageInfo != null) {
                    if (packageInfo.applicationInfo != null) {
                        CharSequence loadLabel = packageInfo.applicationInfo.loadLabel(context.getPackageManager());
                        if (loadLabel != null) {
                            sb.append(loadLabel.toString());
                        }
                    }
                    if (!TextUtils.isEmpty(packageInfo.versionName)) {
                        sb.append(packageInfo.versionName);
                    }
                }
                sb.append(",");
                sb.append(packageName);
                d = sb.toString();
            } catch (Exception unused) {
                d = "";
            }
        }
        return d;
    }

    public static String b(Context context) {
        if (TextUtils.isEmpty(e) || "00:00:00:00:00:00".equals(e)) {
            try {
                if (VERSION.SDK_INT >= 23) {
                    Iterator<T> it = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        NetworkInterface networkInterface = (NetworkInterface) it.next();
                        if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
                            byte[] hardwareAddress = networkInterface.getHardwareAddress();
                            if (hardwareAddress != null) {
                                StringBuilder sb = new StringBuilder();
                                for (byte valueOf : hardwareAddress) {
                                    sb.append(String.format("%02X:", new Object[]{Byte.valueOf(valueOf)}));
                                }
                                if (sb.length() > 0) {
                                    sb.deleteCharAt(sb.length() - 1);
                                }
                                e = sb.toString();
                            }
                        }
                    }
                } else {
                    WifiInfo c2 = com.amap.location.g.d.a.a(context).c();
                    if (c2 != null) {
                        e = c2.getMacAddress();
                    }
                }
            } catch (Exception unused) {
            }
        }
        if (TextUtils.isEmpty(e)) {
            e = "00:00:00:00:00:00";
        }
        return e;
    }

    public static String c(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            g = 0;
            f = "UNKWN";
        } else if (activeNetworkInfo.getType() == 1) {
            g = 2;
            f = "";
        } else {
            g = 1;
            try {
                f = b.get(((TelephonyManager) context.getSystemService("phone")).getNetworkType());
            } catch (Exception e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
                f = "UNKWN";
            }
        }
        return f;
    }

    public static int c() {
        return g;
    }
}
