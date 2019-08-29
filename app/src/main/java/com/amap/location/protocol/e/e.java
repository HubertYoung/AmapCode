package com.amap.location.protocol.e;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import java.util.UUID;

/* compiled from: Utils */
public class e {
    public static int a(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return 0;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return 0;
            }
            if (activeNetworkInfo.getType() == 1) {
                return 4;
            }
            if (activeNetworkInfo.getType() == 0) {
                return b(context);
            }
            return 0;
        } catch (Throwable unused) {
        }
    }

    private static int b(Context context) {
        try {
            switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    return 1;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                    return 2;
                case 13:
                    return 3;
                default:
                    return 0;
            }
        } catch (Throwable unused) {
            return 0;
        }
    }

    public static long a() {
        return System.currentTimeMillis();
    }

    public static String b() {
        return UUID.randomUUID().toString();
    }
}
