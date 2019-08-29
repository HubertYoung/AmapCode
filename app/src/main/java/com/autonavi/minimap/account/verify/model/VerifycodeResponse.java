package com.autonavi.minimap.account.verify.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class VerifycodeResponse extends dkm implements Serializable {
    public int errorcount = 0;
    public JSONObject order_conf = null;
    public String status = null;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            this.status = jSONObject.optString("status");
            this.errorcount = jSONObject.optInt("errorcount");
            this.order_conf = jSONObject.optJSONObject("order_conf");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        json.put("status", this.status);
        json.put("errorcount", this.errorcount);
        json.put("order_conf", this.order_conf);
        return json;
    }
}
