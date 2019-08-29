package com.alipay.mobile.nebulacore.util;

import android.os.Bundle;
import android.text.TextUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5IntentUtil {
    private static Map<String, Bundle> a = new ConcurrentHashMap();

    public static void putParam(String appId, Bundle bundle) {
        a.put(appId, bundle);
    }

    public static Bundle removeParam(String appId) {
        if (!TextUtils.isEmpty(appId)) {
            return a.remove(appId);
        }
        return null;
    }
}
