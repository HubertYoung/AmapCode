package com.autonavi.minimap.account.deactivate.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class DeactivateCheckResponse extends dkm implements Serializable {
    public DeactivateCheckData data = new DeactivateCheckData();

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            JSONObject optJSONObject = jSONObject.optJSONObject("data");
            if (optJSONObject != null) {
                DeactivateCheckData deactivateCheckData = new DeactivateCheckData();
                deactivateCheckData.fromJson(optJSONObject);
                this.data = deactivateCheckData;
            }
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        json.put("data", this.data.toJson());
        return json;
    }
}
