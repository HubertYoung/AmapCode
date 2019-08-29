package com.autonavi.minimap.account.alipay.model;

import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AlipayAuthorizeInfoResponse extends dkm implements Serializable {
    public ArrayList<AlipayAuthorizeInfoResponseData> data = new ArrayList<>();

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            JSONArray optJSONArray = jSONObject.optJSONArray("data");
            if (optJSONArray != null && optJSONArray.length() > 0) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        AlipayAuthorizeInfoResponseData alipayAuthorizeInfoResponseData = new AlipayAuthorizeInfoResponseData();
                        alipayAuthorizeInfoResponseData.fromJson(optJSONObject);
                        this.data.add(alipayAuthorizeInfoResponseData);
                    }
                }
            }
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        JSONArray jSONArray = new JSONArray();
        if (this.data != null && this.data.size() > 0) {
            for (int i = 0; i < this.data.size(); i++) {
                jSONArray.put(this.data.get(i).toJson());
            }
        }
        json.put("data", jSONArray);
        return json;
    }
}
