package com.alipay.mobile.nebulacore.appcenter.parse;

import android.os.Bundle;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5ContentPackagePool {
    public static final String TAG = "H5ContentPackagePool";
    private static Map<String, H5ContentPackage> a = new ConcurrentHashMap();

    public static void preparePackage(Bundle params, boolean needLock) {
        String sessionId = H5Utils.getString(params, (String) "sessionId");
        H5Log.d(TAG, "prepare package " + sessionId);
        if (a.containsKey(sessionId)) {
            H5Log.w(TAG, "package " + sessionId + " already exists.");
            return;
        }
        H5ContentPackage contentPackage = new H5ContentPackage(params, false);
        a.put(sessionId, contentPackage);
        contentPackage.prepareContent(needLock);
    }

    public static void clearPackage(String sessionId) {
        if (!a.containsKey(sessionId)) {
            H5Log.d(TAG, "clearPackage packagePool not contain " + sessionId);
            return;
        }
        H5Log.d(TAG, "consumePackage " + sessionId);
        a.remove(sessionId);
    }

    public static H5ContentPackage getPackage(String sessionId) {
        if (!a.containsKey(sessionId)) {
            H5Log.d(TAG, "getPackage packagePool not contain " + sessionId);
            return null;
        }
        H5Log.d(TAG, "getPackage " + sessionId);
        return a.get(sessionId);
    }
}
