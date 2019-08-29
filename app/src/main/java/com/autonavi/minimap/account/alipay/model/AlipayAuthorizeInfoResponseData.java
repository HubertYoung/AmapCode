package com.autonavi.minimap.account.alipay.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class AlipayAuthorizeInfoResponseData implements Serializable {
    public String status = null;
    public String type = null;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.type = jSONObject.optString("type");
            this.status = jSONObject.optString("status");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", this.type);
        jSONObject.put("status", this.status);
        return jSONObject;
    }
}
