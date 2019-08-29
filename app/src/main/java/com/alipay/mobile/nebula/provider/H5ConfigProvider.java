package com.alipay.mobile.nebula.provider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface H5ConfigProvider {
    public static final String KEY_SSO_ALIPAY_WHITE_LIST_DOMAINS = "h5_AlipayWhiteList";
    public static final String KEY_SSO_ALI_WHITE_LIST_DOMAINS = "h5_AliWhiteList";
    public static final String KEY_SSO_PARTNER_WHITE_LIST_DOMAINS = "h5_PartnerWhiteList";
    public static final String KEY_SSO_RPC_WHITE_LIST_DOMAINS = "h5_rpcWhiteList";
    public static final String KEY_SSO_SERIOUS_ALI_WHITE_LIST_DOMAINS = "h5_SeriousAliWhiteList";

    public interface OnConfigChangeListener {
        void onChange(String str);
    }

    void clearProcessCache();

    String getConfig(String str);

    JSONArray getConfigJSONArray(String str);

    JSONObject getConfigJSONObject(String str);

    String getConfigWithNotifyChange(String str, OnConfigChangeListener onConfigChangeListener);

    String getConfigWithProcessCache(String str);

    boolean isAliDomains(String str);

    boolean isAlipayDomains(String str);

    boolean isPartnerDomains(String str);

    boolean isRpcDomains(String str);

    boolean isSeriousAliDomains(String str);

    boolean permitLocation(String str);
}
