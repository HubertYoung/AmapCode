package com.autonavi.minimap.account.deactivate.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class DeactivateHelpResponse extends dkm implements Serializable {
    public DeactivateHelpData data = new DeactivateHelpData();

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            JSONObject optJSONObject = jSONObject.optJSONObject("data");
            if (optJSONObject != null) {
                DeactivateHelpData deactivateHelpData = new DeactivateHelpData();
                deactivateHelpData.fromJson(optJSONObject);
                this.data = deactivateHelpData;
            }
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        json.put("data", this.data.toJson());
        return json;
    }
}
