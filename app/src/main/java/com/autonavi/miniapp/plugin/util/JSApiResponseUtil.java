package com.autonavi.miniapp.plugin.util;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JSApiResponseUtil {
    private static final String DATA_KEY = "data";
    private static final String ERROR_KEY = "error";
    private static final String ERROR_MSG_KEY = "errorMessage";
    public static final String ERROR_MSG_SUCCESS = "success";
    private Object data;
    private int errorCode;
    private String errorMsg;

    public static JSApiResponseUtil createInstance() {
        return new JSApiResponseUtil();
    }

    public JSApiResponseUtil errorCode(int i) {
        this.errorCode = i;
        return this;
    }

    public JSApiResponseUtil errroMsg(String str) {
        this.errorMsg = str;
        return this;
    }

    public JSApiResponseUtil data(String str) {
        this.data = str;
        return this;
    }

    public JSApiResponseUtil data(JSONObject jSONObject) {
        if (jSONObject == null) {
            this.data = "";
        }
        this.data = JSON.toJSONString(jSONObject);
        return this;
    }

    public JSONObject createResponse() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "error", (Object) Integer.valueOf(this.errorCode));
        if (!TextUtils.isEmpty(this.errorMsg)) {
            jSONObject.put((String) "errorMessage", (Object) this.errorMsg);
        }
        if (this.data != null) {
            jSONObject.put((String) "data", this.data);
        }
        return jSONObject;
    }
}
