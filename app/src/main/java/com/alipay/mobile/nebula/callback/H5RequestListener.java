package com.alipay.mobile.nebula.callback;

import org.json.JSONObject;

public interface H5RequestListener {
    void onRequest(String str, String str2, String str3, boolean z, JSONObject jSONObject);

    void onResponse(String str, String str2, String str3, boolean z, JSONObject jSONObject);
}
