package com.alipay.mobile.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.transportext.biz.util.NetInfoHelper;
import java.util.HashMap;
import java.util.Map.Entry;

public class ConnectionUtil {
    public static final String TYPE_3GNET = "3gnet";
    public static final String TYPE_3GWAP = "3gwap";
    public static final String TYPE_CMNET = "cmnet";
    public static final String TYPE_CMWAP = "cmwap";
    public static final String TYPE_CTNET = "ctnet";
    public static final String TYPE_CTWAP = "ctwap";
    public static final String TYPE_UNINET = "uninet";
    public static final String TYPE_UNIWAP = "uniwap";
    public static final String TYPE_WIFI = "wifi";
    private static final HashMap<String, String> a;

    public ConnectionUtil() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    static {
        HashMap<String, String> hashMap = new HashMap<>();
        a = hashMap;
        hashMap.clear();
        a.put("wifi", "0");
        a.put("cmwap", "1");
        a.put("cmnet", "2");
        a.put("uniwap", "3");
        a.put("uninet", "4");
        a.put("ctnet", "5");
        a.put("ctwap", "6");
        a.put("3gnet", "7");
        a.put("3gwap", "8");
    }

    public static String getConnTypeName(int type) {
        return getConnTypeName(String.valueOf(type));
    }

    public static String getConnTypeName(String type) {
        for (Entry netTypeEntity : a.entrySet()) {
            if (TextUtils.equals(type, (CharSequence) netTypeEntity.getValue())) {
                return (String) netTypeEntity.getValue();
            }
        }
        return "";
    }

    public static int getConnType(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
            if (cm == null) {
                return 0;
            }
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null || !info.isAvailable()) {
                return 0;
            }
            String typeName = info.getTypeName();
            if (typeName.equalsIgnoreCase("WIFI")) {
                return 0;
            }
            if (typeName.equalsIgnoreCase("MOBILE")) {
                return Integer.valueOf(a.get(info.getExtraInfo().toLowerCase())).intValue();
            } else if (!typeName.contains("777")) {
                return -1;
            } else {
                if (isWapAPN()) {
                    return 6;
                }
                return 5;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean isWapAPN() {
        String wap_proxy_ip = getWapIP();
        return (wap_proxy_ip == null || wap_proxy_ip.equals("") || getWapPort() == -1) ? false : true;
    }

    public static String getWapIP() {
        String wapIp = Proxy.getDefaultHost();
        return wapIp == null ? NetInfoHelper.CMWAP_PROXY_HOST : wapIp;
    }

    public static int getWapPort() {
        int port = Proxy.getDefaultPort();
        if (port == -1) {
            return 80;
        }
        return port;
    }

    public static int getNetworkType(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getNetworkType();
    }
}
