package com.autonavi.minimap.account.trust_token.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class TokenData implements Serializable {
    public String token = null;
    public String url = null;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.token = jSONObject.optString("token");
            this.url = jSONObject.optString("url");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("token", this.token);
        jSONObject.put("url", this.url);
        return jSONObject;
    }
}
