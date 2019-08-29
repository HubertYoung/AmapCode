package com.autonavi.minimap.account.bind.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class BindInfoResponseData implements Serializable {
    public String authInfo = null;
    public int type = 0;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.authInfo = jSONObject.optString("auth_info");
            this.type = jSONObject.optInt("type");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("auth_info", this.authInfo);
        jSONObject.put("type", this.type);
        return jSONObject;
    }
}
