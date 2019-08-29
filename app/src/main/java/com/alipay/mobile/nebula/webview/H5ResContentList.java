package com.alipay.mobile.nebula.webview;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5ResContentList {
    private static final String DEFAULT_CACHE_TIME = "604800";
    private static final String TAG = "H5ResContentList";
    private static H5ResContentList instance;
    private static Boolean isEnable;
    private Map<String, byte[]> resourceMap = new ConcurrentHashMap();

    private H5ResContentList() {
    }

    public static synchronized H5ResContentList getInstance() {
        H5ResContentList h5ResContentList;
        synchronized (H5ResContentList.class) {
            try {
                if (instance == null) {
                    instance = new H5ResContentList();
                }
                h5ResContentList = instance;
            }
        }
        return h5ResContentList;
    }

    public synchronized void add(String url, byte[] data) {
        if (!TextUtils.isEmpty(url)) {
            H5Log.d(TAG, "add resource : " + url);
            this.resourceMap.put(url, data);
        }
    }

    public synchronized byte[] get(String url) {
        byte[] bArr;
        if (TextUtils.isEmpty(url)) {
            bArr = null;
        } else {
            bArr = this.resourceMap.get(url);
        }
        return bArr;
    }

    public synchronized void remove(String url) {
        if (!TextUtils.isEmpty(url)) {
            H5Log.d(TAG, "remove resource : " + url);
            this.resourceMap.remove(url);
        }
    }

    public synchronized boolean contains(String url) {
        boolean containsKey;
        if (TextUtils.isEmpty(url)) {
            containsKey = false;
        } else {
            containsKey = this.resourceMap.containsKey(url);
        }
        return containsKey;
    }

    public synchronized void clear() {
        this.resourceMap.clear();
    }

    public static boolean enableResHttpCache() {
        H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (provider == null) {
            return false;
        }
        String config = provider.getConfigWithProcessCache("h5_enableResHttpCache");
        if (TextUtils.isEmpty(config)) {
            return false;
        }
        if (isEnable != null) {
            return isEnable.booleanValue();
        }
        JSONObject jsonObject = H5Utils.parseObject(config);
        if (jsonObject == null || jsonObject.isEmpty()) {
            isEnable = Boolean.valueOf(false);
            return false;
        } else if (!"YES".equalsIgnoreCase(H5Utils.getString(jsonObject, (String) "enable")) || !isUC()) {
            isEnable = Boolean.valueOf(false);
            return false;
        } else {
            isEnable = Boolean.valueOf(true);
            return true;
        }
    }

    private static boolean isUC() {
        H5Service h5Service = H5ServiceUtils.getH5Service();
        if (h5Service != null) {
            H5Page h5Page = h5Service.getTopH5Page();
            if (!(h5Page == null || h5Page.getWebView() == null || h5Page.getWebView().getType() != WebViewType.THIRD_PARTY)) {
                return true;
            }
        }
        return false;
    }

    public static String getCacheTime() {
        H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (provider == null) {
            return DEFAULT_CACHE_TIME;
        }
        String config = provider.getConfigWithProcessCache("h5_enableResHttpCache");
        if (TextUtils.isEmpty(config)) {
            return DEFAULT_CACHE_TIME;
        }
        JSONObject jsonObject = H5Utils.parseObject(config);
        if (jsonObject == null || jsonObject.isEmpty()) {
            return DEFAULT_CACHE_TIME;
        }
        String cacheTime = H5Utils.getString(jsonObject, (String) "cacheTime");
        if (TextUtils.isEmpty(cacheTime)) {
            return DEFAULT_CACHE_TIME;
        }
        return cacheTime;
    }
}
