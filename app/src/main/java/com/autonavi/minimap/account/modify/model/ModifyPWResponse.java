package com.autonavi.minimap.account.modify.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class ModifyPWResponse extends dkm implements Serializable {
    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
        }
    }

    public JSONObject toJson() throws JSONException {
        return super.toJson();
    }
}
