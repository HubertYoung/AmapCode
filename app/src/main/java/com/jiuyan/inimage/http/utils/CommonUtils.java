package com.jiuyan.inimage.http.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.autonavi.minimap.ajx3.util.Constants;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CommonUtils {
    public static final String NETWORKTYPE_WIFI_STRING = "WIFI";
    private static String aid;
    private static String model;
    private static int osv;
    private static int screenWidth;

    public CommonUtils() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static int getScreenWidth(Context context) {
        if (screenWidth == 0) {
            screenWidth = getDisplayMetrics(context.getApplicationContext()).widthPixels;
        }
        return screenWidth;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static String getDeviceModel() {
        if (TextUtils.isEmpty(model)) {
            model = Build.MODEL;
            try {
                model = URLEncoder.encode(model, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return model;
    }

    public static int getOSV() {
        if (osv == 0) {
            osv = VERSION.SDK_INT;
        }
        return osv;
    }

    public static String getAID(Context context) {
        if (!TextUtils.isEmpty(aid)) {
            return aid;
        }
        try {
            aid = Secure.getString(context.getContentResolver(), "android_id");
        } catch (Throwable th) {
        }
        return aid;
    }

    public static String getCurrentNetType(Context context) {
        if (!(context instanceof Application)) {
            context = context.getApplicationContext();
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return Constants.ANIMATOR_NONE;
        }
        if (networkInfo != null && networkInfo.getState() == State.CONNECTED) {
            return "WIFI";
        }
        switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "GPRS";
            case 2:
                return "EDGE";
            case 3:
                return "UMTS";
            case 4:
                return "CDMA";
            case 5:
                return "EVDO_0";
            case 6:
                return "EVDO_A";
            case 7:
                return "1xRTT";
            case 8:
                return "HSDPA";
            case 9:
                return "HSUPA";
            case 10:
                return "HSPA";
            case 11:
                return "IDEN";
            case 12:
                return "EVDO_B";
            case 13:
                return "LTE";
            case 14:
                return "EHRPD";
            case 15:
                return "HSPAP";
            default:
                return "UNKNOWN";
        }
    }

    public static String getMoreInfo(Context context) {
        if (!(context instanceof Application)) {
            context = context.getApplicationContext();
        }
        String networkOperator = ((TelephonyManager) context.getSystemService("phone")).getNetworkOperator();
        if (TextUtils.isEmpty(networkOperator) || networkOperator.length() <= 3) {
            return "";
        }
        String substring = networkOperator.substring(0, 3);
        return substring + "," + networkOperator.substring(3);
    }
}
