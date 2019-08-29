package com.autonavi.indoor.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetworkHelper {
    public static final int NETWORK_234G = 14;
    public static final int NETWORK_234GWIFI = 15;
    public static final int NETWORK_2G = 2;
    public static final int NETWORK_34GWIFI = 13;
    public static final int NETWORK_3G = 4;
    public static final int NETWORK_4G = 8;
    public static final int NETWORK_ALL = 15;
    public static final int NETWORK_MOBILE = 14;
    public static final int NETWORK_NONE = 0;
    public static final int NETWORK_WIFI = 1;
    public static int mNetworkType;

    public static boolean isDownloadNetworkOK(int i) {
        return (i & mNetworkType) != 0;
    }

    public static int getNetworkType() {
        return mNetworkType;
    }

    public static int getNetworkClass(int i) {
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return 2;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return 4;
            case 13:
                return 8;
            default:
                if (L.isLogging) {
                    L.d("unknown networkType:".concat(String.valueOf(i)));
                }
                return 0;
        }
    }

    public static int getNetworkType(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                if (L.isLogging) {
                    L.d((String) "manager.getActiveNetworkInfo return NULL");
                }
                mNetworkType = 0;
            } else if (activeNetworkInfo.getType() == 1) {
                mNetworkType = 1;
            } else {
                mNetworkType = getNetworkClass(((TelephonyManager) context.getSystemService("phone")).getNetworkType());
            }
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
        return mNetworkType;
    }
}
