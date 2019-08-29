package com.alipay.mobile.common.transportext.biz.util;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.Proxy;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import org.apache.http.HttpHost;

public class NetInfoHelper {
    public static final String CMWAP_PROXY_HOST = "10.0.0.172";
    public static final int CMWAP_PROXY_PORT = 80;
    private static final String LOGTAG = new StringBuilder(LogUtilAmnet.PRETAG).append(NetInfoHelper.class.getSimpleName()).toString();
    public static final String NET_TYPE_CMWAP = "cmwap";
    private static final String NET_TYPE_INTERNET = "internet";
    private static final String NET_TYPE_MOBILE = "mobile";
    private static final String NET_TYPE_UNKNOWN = "unknown";
    private static final String NET_TYPE_WIFI = "wifi";

    public static String getActiveNetType(Context context) {
        try {
            NetworkInfo info = NetworkUtils.getActiveNetworkInfo(context);
            if (info == null) {
                return null;
            }
            String typeName = info.getTypeName();
            if (typeName == null || typeName.length() <= 0) {
                return "unknown";
            }
            if ("wifi".equalsIgnoreCase(typeName)) {
                return "wifi";
            }
            if (NET_TYPE_INTERNET.equalsIgnoreCase(typeName)) {
                return NET_TYPE_INTERNET;
            }
            if (!"mobile".equalsIgnoreCase(typeName)) {
                return null;
            }
            String extra = info.getExtraInfo();
            if (extra == null || extra.length() <= 0) {
                return null;
            }
            return extra;
        } catch (Exception e) {
            LogUtilAmnet.e(LOGTAG, "getActiveNetType: [ Exception " + e + " ]");
            return null;
        }
    }

    public static boolean isWifiNetType(Context context) {
        try {
            if ("wifi".equals(getActiveNetType(context))) {
                return true;
            }
            return false;
        } catch (Exception e) {
            LogUtilAmnet.e(LOGTAG, "isWifiNetType: [ Exception " + e + " ]");
            return false;
        }
    }

    public static boolean isMobileNetType(Context context) {
        try {
            NetworkInfo info = NetworkUtils.getActiveNetworkInfo(context);
            if (info == null) {
                return false;
            }
            String typeName = info.getTypeName();
            if (typeName == null || typeName.length() <= 0 || !"mobile".equalsIgnoreCase(typeName)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            LogUtilAmnet.e(LOGTAG, "getActiveNetType: [ Exception " + e + " ]");
            return false;
        }
    }

    public static boolean isNetAvailable(Context context) {
        try {
            return NetworkUtils.isNetworkAvailable(context);
        } catch (Exception e) {
            LogUtilAmnet.e(LOGTAG, "isNetAvailable: [ Exception " + e + " ]");
            return false;
        }
    }

    public static HttpHost getProxy() {
        try {
            String proxyHost = Proxy.getDefaultHost();
            int proxyPort = Proxy.getDefaultPort();
            if (proxyHost == null || proxyHost.length() <= 0 || proxyPort <= 0) {
                return null;
            }
            return new HttpHost(proxyHost, proxyPort);
        } catch (Exception e) {
            LogUtilAmnet.e(LOGTAG, "getProxy: [ Exception " + e + " ]");
            return null;
        }
    }
}
