package com.alipay.inside.android.phone.mrpc.core.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import org.apache.http.HttpHost;

public class NetworkUtils {
    public static final int NETWORK_TYPE_2G = 1;
    public static final int NETWORK_TYPE_3G_4G = 2;
    public static final int NETWORK_TYPE_INVALID = 0;
    public static final int NETWORK_TYPE_LTE = 13;
    public static final int NETWORK_TYPE_WIFI = 3;

    private static boolean is3GMobileNetwork(NetworkInfo networkInfo) {
        if (networkInfo == null) {
            return false;
        }
        switch (networkInfo.getSubtype()) {
            case 0:
                return false;
            case 1:
                return false;
            case 2:
                return false;
            case 3:
                return true;
            case 4:
                return false;
            case 5:
                return true;
            case 6:
                return true;
            case 7:
                return false;
            case 8:
                return true;
            case 9:
                return true;
            case 10:
                return true;
            case 11:
                return false;
            case 13:
                return true;
            default:
                return false;
        }
    }

    public static int getNetworkType(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return 0;
        }
        int type = activeNetworkInfo.getType();
        if (type == 1) {
            return 3;
        }
        if (type == 0) {
            if (is3GMobileNetwork(activeNetworkInfo)) {
                return 2;
            }
            return 1;
        }
        return 0;
    }

    public static HttpHost getProxy(Context context) {
        NetworkInfo networkInfo;
        try {
            networkInfo = getActiveNetworkInfo(context);
        } catch (Throwable th) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("getProxy ex:");
            sb.append(th.toString());
            f.d("NetworkUtils", sb.toString());
            networkInfo = null;
        }
        if (networkInfo == null || !networkInfo.isAvailable()) {
            return null;
        }
        String defaultHost = Proxy.getDefaultHost();
        int defaultPort = Proxy.getDefaultPort();
        if (TextUtils.isEmpty(defaultHost) || defaultPort <= 0 || defaultPort >= 65535) {
            return null;
        }
        return new HttpHost(defaultHost, defaultPort);
    }

    public static NetworkInfo getActiveNetworkInfo(Context context) {
        try {
            return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Throwable th) {
            LoggerFactory.f().b("NetworkUtils", "getActiveNetworkInfo exception ", th);
            return null;
        }
    }

    public static int getNetType(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return -1;
        }
        return activeNetworkInfo.getType();
    }

    public static boolean isNetworkAvailable(Context context) {
        try {
            NetworkInfo activeNetworkInfo = getActiveNetworkInfo(context);
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnectedOrConnecting()) {
                return false;
            }
            return true;
        } catch (Throwable th) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("isNetworkAvailable e :");
            sb.append(th.toString());
            f.d("NetworkUtils", sb.toString());
            return false;
        }
    }
}
