package com.alipay.mobile.nebula.provider;

import com.alibaba.fastjson.JSONObject;

public interface H5TraceProvider {
    void event(String str, String str2, JSONObject jSONObject);

    void sessionBegin(String str, String str2, JSONObject jSONObject);

    void sessionEnd(String str, String str2, JSONObject jSONObject);
}
