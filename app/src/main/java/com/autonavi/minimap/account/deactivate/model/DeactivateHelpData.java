package com.autonavi.minimap.account.deactivate.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class DeactivateHelpData implements Serializable {
    public String helpDoc = null;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.helpDoc = jSONObject.optString("help_doc");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("help_doc", this.helpDoc);
        return jSONObject;
    }
}
