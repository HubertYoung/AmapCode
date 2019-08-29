package com.autonavi.minimap.account.password.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class PasspordInitData implements Serializable {
    public int repwd = 0;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.repwd = jSONObject.optInt("repwd");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("repwd", this.repwd);
        return jSONObject;
    }
}
