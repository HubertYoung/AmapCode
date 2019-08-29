package com.alipay.mobile.nebula.appcenter.wifidownload;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5WifiDownloadList {
    private static Map<String, String> map = null;

    public static synchronized void put(String appId, String version) {
        synchronized (H5WifiDownloadList.class) {
            if (map == null) {
                map = new ConcurrentHashMap();
            }
            map.put(appId, version);
        }
    }

    public static void remove(String appId) {
        if (map != null) {
            map.remove(appId);
        }
    }

    public static Map<String, String> getWifiDownloadMap() {
        return map;
    }
}
