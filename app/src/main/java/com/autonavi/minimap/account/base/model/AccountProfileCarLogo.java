package com.autonavi.minimap.account.base.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountProfileCarLogo implements Serializable {
    public String des = null;
    public int exchangeType = 0;
    public int id = 0;
    public String listLogo = null;
    public int logoStatus = 0;
    public String name = null;
    public String normalLogo = null;
    public int value = 0;
    public String weakLogo = null;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.name = jSONObject.optString("name");
            this.weakLogo = jSONObject.optString("weak_logo");
            this.listLogo = jSONObject.optString("list_logo");
            this.value = jSONObject.optInt("value");
            this.exchangeType = jSONObject.optInt("exchange_type");
            this.logoStatus = jSONObject.optInt("logo_status");
            this.normalLogo = jSONObject.optString("normal_logo");
            this.id = jSONObject.optInt("id");
            this.des = jSONObject.optString("des");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("name", this.name);
        jSONObject.put("weak_logo", this.weakLogo);
        jSONObject.put("list_logo", this.listLogo);
        jSONObject.put("value", this.value);
        jSONObject.put("exchange_type", this.exchangeType);
        jSONObject.put("logo_status", this.logoStatus);
        jSONObject.put("normal_logo", this.normalLogo);
        jSONObject.put("id", this.id);
        jSONObject.put("des", this.des);
        return jSONObject;
    }
}
