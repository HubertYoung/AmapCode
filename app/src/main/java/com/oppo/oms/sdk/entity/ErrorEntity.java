package com.oppo.oms.sdk.entity;

import org.json.JSONObject;

public class ErrorEntity {
    public String code;
    public String msg;

    public String toString() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("code", this.code);
            jSONObject.put("msg", this.msg);
            return jSONObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return super.toString();
        }
    }
}
