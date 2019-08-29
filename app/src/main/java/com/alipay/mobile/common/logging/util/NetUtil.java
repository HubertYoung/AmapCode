package com.alipay.mobile.common.logging.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class NetUtil {
    public static final String TYPE_GSM = "GSM";
    public static final String TYPE_TDS_HSDPSA = "TDS-HSDPSA";
    public static final String TYPE_TDS_HSUPA = "TDS-HSUPA";
    public static final String TYPE_TD_CDMA = "TD-CDMA";
    public static final String TYPE_WIFI = "WIFI";
    private static long a;
    private static String b;
    private static long c;
    private static String d;

    public static NetworkInfo getActiveNetworkInfo(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Throwable e) {
            Log.w("LogNetUtil", e);
            return null;
        }
    }

    public static String getNetworkTypeOptimized(Context context) {
        long time = SystemClock.uptimeMillis();
        if (time - a > 5000) {
            b = getNetworkType(context);
            a = time;
        }
        return b;
    }

    public static String getNetworkTypeOptimizedStrict(Context context) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - c > 1000) {
            d = getNetworkType(context);
            c = currentTime;
        }
        return d;
    }

    public static String getNetworkType(Context context) {
        return getNetworkType(getActiveNetworkInfo(context));
    }

    public static String getNetworkType(NetworkInfo networkInfo) {
        if (networkInfo == null) {
            return null;
        }
        if ("WIFI".equalsIgnoreCase(networkInfo.getTypeName())) {
            return "WIFI";
        }
        String mobileType = null;
        if (!TextUtils.isEmpty(networkInfo.getSubtypeName())) {
            mobileType = networkInfo.getSubtypeName();
        } else if (networkInfo.getSubtype() == 16) {
            mobileType = "GSM";
        } else if (networkInfo.getSubtype() == 17) {
            mobileType = "TD-CDMA";
        } else if (networkInfo.getSubtype() == 18) {
            mobileType = "TDS-HSDPSA";
        } else if (networkInfo.getSubtype() == 19) {
            mobileType = "TDS-HSUPA";
        }
        if (mobileType == null || TextUtils.isEmpty(networkInfo.getExtraInfo())) {
            return mobileType;
        }
        return mobileType + MergeUtil.SEPARATOR_KV + networkInfo.getExtraInfo();
    }

    public static boolean isNetworkConnected(Context context) {
        try {
            NetworkInfo networkInfo = getActiveNetworkInfo(context);
            if (networkInfo == null || !networkInfo.isConnected()) {
                return false;
            }
            return true;
        } catch (Throwable t) {
            Log.w("LogNetUtil", t);
            return false;
        }
    }

    public static String formatParamStringForGET(Map<String, String> paramData) {
        String str = null;
        if (paramData == null || paramData.size() == 0) {
            return str;
        }
        try {
            List params = new ArrayList();
            for (Entry entry : paramData.entrySet()) {
                params.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
            }
            return URLEncodedUtils.format(params, "utf-8");
        } catch (Throwable t) {
            Log.e("LogNetUtil", "formatParamStringForGET", t);
            return str;
        }
    }
}
