package com.alipay.mobile.common.logging.util.network;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.telephony.TelephonyManager;
import android.util.Log;
import org.apache.http.HttpHost;

public class NetworkUtils {
    public static final int ANDROID_NETWORK_CLASS_2_G = 1;
    public static final int ANDROID_NETWORK_CLASS_3_G = 2;
    public static final int ANDROID_NETWORK_CLASS_4_G = 3;
    public static final int NETWORK_TYPE_2G = 1;
    public static final int NETWORK_TYPE_3G = 2;
    public static final int NETWORK_TYPE_4G = 4;
    public static final int NETWORK_TYPE_EHRPD = 14;
    public static final int NETWORK_TYPE_HSPAP = 15;
    public static final int NETWORK_TYPE_INVALID = 0;
    public static final int NETWORK_TYPE_LTE = 13;
    public static final int NETWORK_TYPE_WIFI = 3;
    public static final String TAG = "LogNetworkUtils";

    public static boolean isWiFiMobileNetwork(Context context) {
        if (context != null && getNetworkType(context) == 3) {
            return true;
        }
        return false;
    }

    public static boolean is4GMobileNetwork(NetworkInfo activeNetInfo) {
        if (activeNetInfo != null && getMobileNetworkClass(activeNetInfo) == 4) {
            return true;
        }
        return false;
    }

    public static boolean is4GMobileNetwork(Context context) {
        if (context != null && getNetworkType(context) == 4) {
            return true;
        }
        return false;
    }

    public static boolean is3GMobileNetwork(Context context) {
        if (context != null && getNetworkType(context) == 2) {
            return true;
        }
        return false;
    }

    public static boolean is3GMobileNetwork(NetworkInfo activeNetInfo) {
        if (activeNetInfo != null && getMobileNetworkClass(activeNetInfo) == 2) {
            return true;
        }
        return false;
    }

    public static boolean is2GMobileNetwork(Context context) {
        if (context != null && getNetworkType(context) == 1) {
            return true;
        }
        return false;
    }

    public static int getNetworkType(Context context) {
        NetworkInfo activeNetInfo = getActiveNetworkInfo(context);
        if (activeNetInfo == null || !activeNetInfo.isConnected()) {
            return 0;
        }
        int type = activeNetInfo.getType();
        if (type == 1) {
            return 3;
        }
        if (type == 0) {
            return getMobileNetworkClass(activeNetInfo);
        }
        return 0;
    }

    public static HttpHost getProxy(Context context) {
        NetworkInfo ni = null;
        try {
            ni = getActiveNetworkInfo(context);
        } catch (Throwable ex) {
            Log.e("NetworkUtils", ex.getMessage());
        }
        if (ni == null || !ni.isAvailable()) {
            return null;
        }
        String proxyHost = Proxy.getDefaultHost();
        int port = Proxy.getDefaultPort();
        if (proxyHost != null) {
            return new HttpHost(proxyHost, port);
        }
        return null;
    }

    public static NetworkInfo getActiveNetworkInfo(Context context) {
        try {
            return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Throwable throwable) {
            Log.e(TAG, "getActiveNetworkInfo exception " + throwable.getMessage());
            return null;
        }
    }

    public static int getNetType(Context context) {
        NetworkInfo ni = getActiveNetworkInfo(context);
        if (ni == null) {
            return -1;
        }
        return ni.getType();
    }

    @TargetApi(3)
    public static boolean isNetworkAvailable(Context context) {
        try {
            NetworkInfo info = getActiveNetworkInfo(context);
            if (info == null || !info.isConnectedOrConnecting()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "isNetworkAvailable exception : " + e.toString());
            return false;
        }
    }

    @TargetApi(3)
    public static int getMobileNetworkClass(NetworkInfo networkInfo) {
        if (networkInfo == null) {
            return 0;
        }
        return getMobileNetworkClass(networkInfo.getSubtype());
    }

    public static int getMobileNetworkClass(int networkType) {
        try {
            Object[] objArr = {Integer.valueOf(networkType)};
            Integer networkClass = (Integer) TelephonyManager.class.getMethod("getNetworkClass", new Class[]{Integer.TYPE}).invoke(TelephonyManager.class, objArr);
            if (networkClass.intValue() == 1) {
                return 1;
            }
            if (networkClass.intValue() == 2) {
                return 2;
            }
            if (networkClass.intValue() == 3) {
                return 4;
            }
            return 0;
        } catch (Throwable e) {
            Log.e(TAG, "TelephonyManager#getNetworkClass exception: " + e.toString());
            switch (networkType) {
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
                case 18:
                    return 2;
                case 13:
                    return 4;
                default:
                    return 0;
            }
        }
    }
}
