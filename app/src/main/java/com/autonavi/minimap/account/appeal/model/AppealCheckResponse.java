package com.autonavi.minimap.account.appeal.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class AppealCheckResponse extends dkm implements Serializable {
    public String status = null;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            this.status = jSONObject.optString("status");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        json.put("status", this.status);
        return json;
    }
}
