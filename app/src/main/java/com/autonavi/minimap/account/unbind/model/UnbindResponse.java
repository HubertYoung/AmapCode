package com.autonavi.minimap.account.unbind.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class UnbindResponse extends dkm implements Serializable {
    public String credit = null;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            this.credit = jSONObject.optString("credit");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        json.put("credit", this.credit);
        return json;
    }
}
