package com.alipay.mobile.nebulauc.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5DomainUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5ConfigUtil {
    public static final String TAG = "UCConfigUtil";

    public static String getConfig(String configName) {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            return h5ConfigProvider.getConfigWithProcessCache(configName);
        }
        H5Log.e((String) TAG, (String) "h5ConfigProvider == null");
        return null;
    }

    public static JSONObject getConfigJSONObject(String key) {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            return h5ConfigProvider.getConfigJSONObject(key);
        }
        H5Log.e((String) TAG, (String) "h5ConfigProvider == null");
        return null;
    }

    public static JSONArray getConfigJSONArray(String key) {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            return h5ConfigProvider.getConfigJSONArray(key);
        }
        H5Log.e((String) TAG, (String) "h5ConfigProvider == null");
        return null;
    }

    public static boolean isAliDomains(String url) {
        return H5DomainUtil.isSomeDomainInternal(H5DomainUtil.getNewDomainSuffix(url), getConfig(H5ConfigProvider.KEY_SSO_ALI_WHITE_LIST_DOMAINS));
    }

    public static boolean isAlipayDomains(String url) {
        return H5DomainUtil.isSomeDomainInternal(H5DomainUtil.getNewDomainSuffix(url), getConfig(H5ConfigProvider.KEY_SSO_ALIPAY_WHITE_LIST_DOMAINS));
    }
}
