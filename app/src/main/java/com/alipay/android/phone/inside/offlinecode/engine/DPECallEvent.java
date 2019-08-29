package com.alipay.android.phone.inside.offlinecode.engine;

import org.json.JSONObject;

public class DPECallEvent {
    private String methodName;
    private JSONObject param;
    private String result;

    public DPECallEvent(String str, JSONObject jSONObject) {
        this.methodName = str;
        this.param = jSONObject;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public JSONObject getParam() {
        return this.param;
    }

    public void setResult(String str) {
        this.result = str;
    }

    public String getResult() {
        return this.result;
    }
}
