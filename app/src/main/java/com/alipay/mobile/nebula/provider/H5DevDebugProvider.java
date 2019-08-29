package com.alipay.mobile.nebula.provider;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.util.batched.BatchedScheduler;

public interface H5DevDebugProvider {
    void consoleLog(String str, String str2, String str3, String str4);

    void eventLog(String str, String str2, String str3);

    BatchedScheduler<JSONObject> getScheduler();

    void jsApiLog(String str, String str2, String str3, String str4, String str5);

    void netWorkLog(String str, String str2, JSONObject jSONObject);

    void onRelease();

    void pageLog(String str, String str2, String str3, String str4, String str5, JSONObject jSONObject);

    void screenshot(String str, String str2);

    void setPageUrl(String str, String str2);

    void setPkgInfo(String str, JSONObject jSONObject);

    void setSessionId(String str, String str2);

    void setTitle(String str, String str2);

    void setUserAgent(String str, String str2);

    void welcome(String str, String str2, String str3, String str4);
}
