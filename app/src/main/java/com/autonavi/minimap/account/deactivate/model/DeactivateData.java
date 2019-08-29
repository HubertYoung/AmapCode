package com.autonavi.minimap.account.deactivate.model;

import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DeactivateData implements Serializable {
    public String mobile = null;
    public ArrayList<String> reason = new ArrayList<>();
    public int remain = 0;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            JSONArray optJSONArray = jSONObject.optJSONArray("reason");
            if (optJSONArray != null && optJSONArray.length() > 0) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    this.reason.add(optJSONArray.optString(i));
                }
            }
            this.remain = jSONObject.optInt("remain");
            this.mobile = jSONObject.optString("mobile");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        if (this.reason != null && this.reason.size() > 0) {
            for (int i = 0; i < this.reason.size(); i++) {
                jSONArray.put(this.reason.get(i));
            }
        }
        jSONObject.put("reason", jSONArray);
        jSONObject.put("remain", this.remain);
        jSONObject.put("mobile", this.mobile);
        return jSONObject;
    }
}
