package com.mpaas.nebula.provider;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider.OnConfigChangeListener;
import com.alipay.mobile.nebula.util.H5DomainUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.mpaas.nebula.NebulaBiz;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5ConfigProviderImpl implements H5ConfigProvider {
    public static final String TAG = "H5ConfigProvider";
    private static Map<String, String> a = new ConcurrentHashMap();
    private static Boolean b = null;
    private static Map<String, JSONArray> c = new ConcurrentHashMap();
    private static Map<String, JSONObject> d = new ConcurrentHashMap();
    private static Map<String, OnConfigChangeListener> e = new ConcurrentHashMap();

    public String getConfig(String configName) {
        if (H5Utils.isInTinyProcess()) {
            return getConfigWithProcessCache(configName);
        }
        return NebulaBiz.getConfig(configName);
    }

    public JSONObject getConfigJSONObject(String key) {
        a();
        return H5Utils.parseObject(getConfig(key));
    }

    public JSONArray getConfigJSONArray(String key) {
        a();
        return H5Utils.parseArray(getConfig(key));
    }

    public String getConfigWithNotifyChange(String key, OnConfigChangeListener listener) {
        return getConfig(key);
    }

    public String getConfigWithProcessCache(String key) {
        try {
            if (a.containsKey(key)) {
                String value = a.get(key);
                H5Log.d(TAG, "getConfig from configCache " + key + Token.SEPARATOR + value);
                return value;
            }
            String value2 = NebulaBiz.getConfig(key);
            if (!TextUtils.isEmpty(value2)) {
                a.put(key, value2);
                return value2;
            }
            a.put(key, "");
            return value2;
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
            return null;
        }
    }

    public void clearProcessCache() {
        if (a != null) {
            a.clear();
        }
    }

    public void putConfigCache(Map map) {
        a.clear();
        a.putAll(map);
    }

    public boolean permitLocation(String url) {
        return a(url);
    }

    private boolean a(String url) {
        return H5DomainUtil.isSomeDomainInternal(H5DomainUtil.getNewDomainSuffix(url), getConfig("h5_LocationDialogPermitWhiteList"));
    }

    public boolean isAliDomains(String url) {
        return H5DomainUtil.isSomeDomainInternal(H5DomainUtil.getNewDomainSuffix(url), getConfig(H5ConfigProvider.KEY_SSO_ALI_WHITE_LIST_DOMAINS));
    }

    public boolean isPartnerDomains(String url) {
        return H5DomainUtil.isSomeDomainInternal(H5DomainUtil.getNewDomainSuffix(url), getConfig(H5ConfigProvider.KEY_SSO_PARTNER_WHITE_LIST_DOMAINS));
    }

    public boolean isAlipayDomains(String url) {
        return H5DomainUtil.isSomeDomainInternal(H5DomainUtil.getNewDomainSuffix(url), getConfig(H5ConfigProvider.KEY_SSO_ALIPAY_WHITE_LIST_DOMAINS));
    }

    public boolean isSeriousAliDomains(String url) {
        return H5DomainUtil.isSomeDomainInternal(H5DomainUtil.getNewDomainSuffix(url), getConfig(H5ConfigProvider.KEY_SSO_SERIOUS_ALI_WHITE_LIST_DOMAINS));
    }

    public boolean isRpcDomains(String url) {
        return H5DomainUtil.isSomeDomainInternal(H5DomainUtil.getNewDomainSuffix(url), getConfig(H5ConfigProvider.KEY_SSO_RPC_WHITE_LIST_DOMAINS));
    }

    private void a() {
        if (b == null) {
            b = Boolean.valueOf(!BQCCameraParam.VALUE_NO.equalsIgnoreCase(getConfig("h5_enableConfigCache")));
        }
    }
}
