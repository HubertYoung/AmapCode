package com.autonavi.minimap.account.appeal.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class AppealBindMobileResponse extends dkm implements Serializable {
    public AppealBindMobileData data = new AppealBindMobileData();
    public String status = null;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            this.status = jSONObject.optString("status");
            JSONObject optJSONObject = jSONObject.optJSONObject("data");
            if (optJSONObject != null) {
                AppealBindMobileData appealBindMobileData = new AppealBindMobileData();
                appealBindMobileData.fromJson(optJSONObject);
                this.data = appealBindMobileData;
            }
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        json.put("status", this.status);
        json.put("data", this.data.toJson());
        return json;
    }
}
