package com.alipay.android.nebulaapp;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider.OnConfigChangeListener;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5ConfigProviderImpl implements H5ConfigProvider {
    public void clearProcessCache() {
    }

    public JSONArray getConfigJSONArray(String str) {
        return null;
    }

    public JSONObject getConfigJSONObject(String str) {
        return null;
    }

    public String getConfigWithNotifyChange(String str, OnConfigChangeListener onConfigChangeListener) {
        return null;
    }

    public String getConfigWithProcessCache(String str) {
        return null;
    }

    public boolean isAliDomains(String str) {
        return false;
    }

    public boolean isAlipayDomains(String str) {
        return false;
    }

    public boolean isPartnerDomains(String str) {
        return false;
    }

    public boolean isRpcDomains(String str) {
        return false;
    }

    public boolean isSeriousAliDomains(String str) {
        return false;
    }

    public boolean permitLocation(String str) {
        return false;
    }

    public String getConfig(String str) {
        if (TextUtils.equals(str, H5Utils.KEY_H5_WEBVIEW_CONFIG)) {
            return "{\"h5_enableExternalWebView\":\"YES\",\"h5_externalWebViewUsageRule\":{},\"h5_externalWebViewSdkVersion\":{\"min\":11,\"max\":27}}";
        }
        if (TextUtils.equals(str, H5Utils.KEY_H5_FORCE_UC)) {
            return "{\"globalFlag\":true}";
        }
        return null;
    }
}
