package com.alipay.android.phone.inside.offlinecode.rpc.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class ScardCenterRes {
    public boolean cleanCard;
    public String code;
    public Object indicator;
    public Object result;

    public JSONObject getJSONIndicator() {
        if (this.indicator == null) {
            return null;
        }
        return (JSONObject) this.indicator;
    }

    public String getIndicator() {
        if (this.indicator == null) {
            return "";
        }
        return this.indicator.toString();
    }

    public JSONObject getJSONResult() {
        return (JSONObject) this.result;
    }

    public JSONArray getJSONArrayResult() {
        return (JSONArray) this.result;
    }

    public boolean isCleanCard() {
        return this.cleanCard;
    }

    public String getResult() {
        if (this.result == null) {
            return "";
        }
        return this.result.toString();
    }
}
