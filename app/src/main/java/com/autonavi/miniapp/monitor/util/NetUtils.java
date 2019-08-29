package com.autonavi.miniapp.monitor.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class NetUtils {
    private static final long NETWORK_TYPE_FUZZ_TIME = 1000;
    private static final String TAG = "MonitorNetUtils";
    public static final String TYPE_GSM = "GSM";
    public static final String TYPE_TDS_HSDPSA = "TDS-HSDPSA";
    public static final String TYPE_TDS_HSUPA = "TDS-HSUPA";
    public static final String TYPE_TD_CDMA = "TD-CDMA";
    public static final String TYPE_WIFI = "WIFI";
    private static long mPreviousNetworkTime;
    private static String mPreviousNetworkType;

    public static NetworkInfo getActiveNetworkInfo() {
        Context applicationContext = LoggerFactory.getLogContext().getApplicationContext();
        if (applicationContext == null) {
            return null;
        }
        try {
            return ((ConnectivityManager) applicationContext.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String getNetworkType() {
        return getNetworkType(getActiveNetworkInfo());
    }

    public static String getNetworkTypeOptimized() {
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(currentTimeMillis - mPreviousNetworkTime) > 1000) {
            String networkType = getNetworkType();
            if (networkType == null) {
                return null;
            }
            mPreviousNetworkType = networkType;
            mPreviousNetworkTime = currentTimeMillis;
        }
        return mPreviousNetworkType;
    }

    public static String getNetworkType(NetworkInfo networkInfo) {
        String str = null;
        if (networkInfo == null) {
            return null;
        }
        if (networkInfo.getType() == 1) {
            return "WIFI";
        }
        if ("WIFI".equalsIgnoreCase(networkInfo.getTypeName())) {
            return "WIFI";
        }
        String subtypeName = networkInfo.getSubtypeName();
        if (!TextUtils.isEmpty(subtypeName)) {
            str = subtypeName;
        } else if (networkInfo.getSubtype() == 16) {
            str = "GSM";
        } else if (networkInfo.getSubtype() == 17) {
            str = "TD-CDMA";
        } else if (networkInfo.getSubtype() == 18) {
            str = "TDS-HSDPSA";
        } else if (networkInfo.getSubtype() == 19) {
            str = "TDS-HSUPA";
        }
        if (str != null) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(MergeUtil.SEPARATOR_KV);
                sb.append(extraInfo);
                str = sb.toString();
            }
        }
        return str;
    }

    public static boolean isNetworkUseWifi() {
        return "WIFI".equals(getNetworkType());
    }

    public static boolean isNetworkConnected() {
        boolean z = false;
        try {
            NetworkInfo activeNetworkInfo = getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                z = true;
            }
            return z;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static String formatParamStringForGET(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        try {
            ArrayList arrayList = new ArrayList();
            for (Entry next : map.entrySet()) {
                arrayList.add(new abg((String) next.getKey(), (String) next.getValue()));
            }
            return aba.a((List<? extends abg>) arrayList, (String) "utf-8");
        } catch (Throwable unused) {
            return null;
        }
    }
}
