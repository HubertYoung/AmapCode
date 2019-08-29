package com.alipay.mobile.nebula.appcenter.downloadImpl;

import android.text.TextUtils;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadCallback;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5DownLoadCallBackList {
    public static Map<String, List<H5DownloadCallback>> callbackData = new ConcurrentHashMap();

    public static void unRegisterCallbacks(String downloadUrl) {
        if (!TextUtils.isEmpty(downloadUrl)) {
            callbackData.remove(downloadUrl);
        }
    }

    public static void registerCallback(String downloadUrl, H5DownloadCallback callback) {
        List callbackList;
        if (downloadUrl != null && callback != null) {
            if (!callbackData.containsKey(downloadUrl)) {
                callbackList = new ArrayList();
            } else if (callbackData.get(downloadUrl) == null) {
                callbackList = new ArrayList();
            } else {
                callbackList = callbackData.get(downloadUrl);
            }
            callbackData.put(downloadUrl, callbackList);
            if (!callbackList.contains(callback)) {
                callbackList.add(callback);
            }
        }
    }

    public static boolean isDownloadTaskExists(String downloadUrl) {
        if (downloadUrl == null) {
            return false;
        }
        List list = callbackData.get(downloadUrl);
        if (list == null || list.size() <= 0) {
            return false;
        }
        return true;
    }
}
