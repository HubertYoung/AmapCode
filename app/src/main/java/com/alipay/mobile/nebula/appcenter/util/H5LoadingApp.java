package com.alipay.mobile.nebula.appcenter.util;

import android.text.TextUtils;
import com.alipay.mobile.nebula.util.H5Log;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5LoadingApp {
    private static final String TAG = "H5LoadingApp";
    private static Map<String, String> loadingReadyVersion = new ConcurrentHashMap();

    public static void put(String appId, String version) {
        if (!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(version)) {
            loadingReadyVersion.put(appId, version);
        }
    }

    public static void remove(String appId) {
        if (!TextUtils.isEmpty(appId)) {
            loadingReadyVersion.remove(appId);
        }
    }

    public static boolean contain(String appId, String version) {
        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(version) || !TextUtils.equals(version, loadingReadyVersion.get(appId))) {
            return false;
        }
        H5Log.d(TAG, "appId " + appId + " version:" + version + " is on loading not delete");
        return true;
    }
}
