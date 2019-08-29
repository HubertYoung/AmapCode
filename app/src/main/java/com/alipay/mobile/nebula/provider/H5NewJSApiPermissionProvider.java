package com.alipay.mobile.nebula.provider;

import com.alibaba.fastjson.JSONObject;

public interface H5NewJSApiPermissionProvider {
    String getDynamicRouteByUrl(String str);

    String handleDynamicRouteByGroupId(String str);

    String handleDynamicRouteByUrl(String str);

    int hasPermissionByGroupId(String str, String str2, JSONObject jSONObject);

    int hasPermissionByUrl(String str, String str2, JSONObject jSONObject);

    boolean ifExpiredByGroupId(String str);

    boolean ifExpiredByUrl(String str);

    boolean ifLevelNoneExpired();

    void onReceiveLogin();

    void setLevelNoneCacheTime(int i);
}
