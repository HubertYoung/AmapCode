package com.autonavi.minimap.account.password.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class PasswordInitResponse extends dkm implements Serializable {
    public PasspordInitData data = new PasspordInitData();

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            JSONObject optJSONObject = jSONObject.optJSONObject("data");
            if (optJSONObject != null) {
                PasspordInitData passpordInitData = new PasspordInitData();
                passpordInitData.fromJson(optJSONObject);
                this.data = passpordInitData;
            }
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        json.put("data", this.data.toJson());
        return json;
    }
}
