package com.alipay.mobile.nebula.util;

import android.content.SharedPreferences;

public class H5SharedPreUtil {
    public static final String H5_APP_SCORE_INFO = "h5_app_score_info";
    public static final String H5_APP_SCORE_RPC_TIME = "h5_app_score_rpc_time";
    public static final String H5_SCORE_RPC_LIMIT = "h5_score_rpc_limit";
    private static final String NEBULA_SHARED_FILE = "nebula_shared_pre";
    private static String TAG = "SharedPreUtils";

    public static final void saveStringData(String key, String value) {
        try {
            getSharedPreferences().edit().putString(key, value).apply();
        } catch (Exception e) {
            H5Log.e(TAG, (Throwable) e);
        }
    }

    public static final void saveLongData(String key, long value) {
        try {
            getSharedPreferences().edit().putLong(key, value).apply();
        } catch (Exception e) {
            H5Log.e(TAG, (Throwable) e);
        }
    }

    public static final String getStringData(String key) {
        try {
            return getSharedPreferences().getString(key, "");
        } catch (Exception e) {
            H5Log.e(TAG, (Throwable) e);
            return "";
        }
    }

    public static final long getLongData(String key) {
        long j = -1;
        try {
            return getSharedPreferences().getLong(key, -1);
        } catch (Exception e) {
            H5Log.e(TAG, (Throwable) e);
            return j;
        }
    }

    public static final void removeData(String key) {
        try {
            getSharedPreferences().edit().remove(key).apply();
        } catch (Exception e) {
            H5Log.e(TAG, (Throwable) e);
        }
    }

    public static SharedPreferences getSharedPreferences() {
        return H5Utils.getContext().getSharedPreferences(NEBULA_SHARED_FILE, 0);
    }
}
