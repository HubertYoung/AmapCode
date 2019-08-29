package com.autonavi.minimap.account.deactivate.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class DeactivateResponse extends dkm implements Serializable {
    public DeactivateData data = new DeactivateData();

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            JSONObject optJSONObject = jSONObject.optJSONObject("data");
            if (optJSONObject != null) {
                DeactivateData deactivateData = new DeactivateData();
                deactivateData.fromJson(optJSONObject);
                this.data = deactivateData;
            }
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        json.put("data", this.data.toJson());
        return json;
    }
}
