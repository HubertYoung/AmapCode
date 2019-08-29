package com.autonavi.minimap.account.insurance.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class InsuranceTokenResponse extends dkm implements Serializable {
    public String autoToken = null;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            this.autoToken = jSONObject.optString("auto_token");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        json.put("auto_token", this.autoToken);
        return json;
    }
}
