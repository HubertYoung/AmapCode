package com.alipay.mobile.common.transport.iprank.utils;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.http.HttpContextExtend;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkUtils;

public class IpRankUtil {
    public static final String TAG = "IPR_IpRankUtil";
    private static double a = 6378.137d;

    public static String getLatLng(Context context) {
        try {
            if (MiscUtils.isPushProcess(context)) {
                return "";
            }
            String lat = HttpContextExtend.getInstance().getLatitude();
            String lng = HttpContextExtend.getInstance().getLongitude();
            if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(lng)) {
                LogCatUtil.debug(TAG, "getLatLng return null");
                return null;
            }
            LogCatUtil.debug(TAG, "latlng: " + lat + ";" + lng);
            return lat + ";" + lng;
        } catch (Throwable th) {
            LogCatUtil.warn((String) TAG, (String) "getLatLng Throwable");
            return "";
        }
    }

    public static double getDistance(String lbs1, String lbs2) {
        try {
            String[] locations1 = lbs1.split(";");
            double lat1 = Double.parseDouble(locations1[0]);
            double lng1 = Double.parseDouble(locations1[1]);
            String[] locations2 = lbs2.split(";");
            return getDistance(lat1, lng1, Double.parseDouble(locations2[0]), Double.parseDouble(locations2[1]));
        } catch (Throwable ex) {
            LogCatUtil.error(TAG, "getDistance Throwable", ex);
            return -1.0d;
        }
    }

    private static double a(double d) {
        return (3.141592653589793d * d) / 180.0d;
    }

    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = a(lat1);
        double radLat2 = a(lat2);
        return 2.0d * Math.asin(Math.sqrt(Math.pow(Math.sin((radLat1 - radLat2) / 2.0d), 2.0d) + (Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin((a(lng1) - a(lng2)) / 2.0d), 2.0d)))) * a;
    }

    public static int getNetType(Context context) {
        if (context != null) {
            return NetworkUtils.getNetworkType(context);
        }
        LogCatUtil.debug(TAG, "getNetworkType context is null");
        return -1;
    }
}
