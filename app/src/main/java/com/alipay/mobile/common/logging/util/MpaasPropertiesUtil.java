package com.alipay.mobile.common.logging.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class MpaasPropertiesUtil {
    private static Map<String, String> a = new HashMap();

    private static Map<String, String> a(Context context) {
        Map map = b(context);
        a.putAll(map);
        return map;
    }

    private static Map<String, String> b(Context context) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("mpaas.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            if (properties.size() <= 0) {
                Map<String, String> emptyMap = Collections.emptyMap();
                try {
                    return emptyMap;
                } catch (IOException e) {
                    return emptyMap;
                }
            } else {
                Map map = new HashMap(properties.size());
                for (Entry entry : properties.entrySet()) {
                    map.put((String) entry.getKey(), (String) entry.getValue());
                }
                try {
                    inputStream.close();
                    return map;
                } catch (IOException e2) {
                    return map;
                }
            }
        } catch (IOException e3) {
            return Collections.emptyMap();
        } catch (Throwable th) {
            return Collections.emptyMap();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e4) {
            }
        }
    }

    public static String getAppKey(Context context) {
        return getKeyFromManifest(context, "appkey");
    }

    public static String getAppID(Context context) {
        return getKeyFromManifest(context, "ALIPUSH_APPID");
    }

    public static String getKeyFromManifest(Context context, String key) {
        if (a.containsKey(key)) {
            return a.get(key);
        }
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
        } catch (Throwable th) {
        }
        if (appInfo != null) {
            try {
                if (appInfo.metaData != null) {
                    String appKey = appInfo.metaData.getString(key);
                    a.put(key, appKey);
                    return appKey;
                }
            } catch (Throwable th2) {
            }
        }
        return null;
    }

    public static String getWorkSpaceId(Context context) {
        return getKeyFromMpaasProperties(context, "WorkspaceId");
    }

    public static String getMpaasapi(Context context) {
        return getKeyFromManifest(context, "mpaasapi");
    }

    public static String getKeyFromMpaasProperties(Context context, String key) {
        if (a.containsKey(key)) {
            return a.get(key);
        }
        String keyValue = null;
        try {
            Map mpaasProperties = a(context);
            if (mpaasProperties != null) {
                keyValue = mpaasProperties.get(key);
            }
            if (keyValue != null) {
                a.put(key, keyValue);
            }
        } catch (Throwable th) {
        }
        return keyValue;
    }

    public static String getProductId(Context context) {
        return getAppKey(context) + "-" + getWorkSpaceId(context);
    }
}
