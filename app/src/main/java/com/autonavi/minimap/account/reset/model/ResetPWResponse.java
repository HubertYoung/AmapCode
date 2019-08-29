package com.autonavi.minimap.account.reset.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class ResetPWResponse extends dkm implements Serializable {
    public int errCount = 0;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            this.errCount = jSONObject.optInt("errCount");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        json.put("errCount", this.errCount);
        return json;
    }
}
