package com.autonavi.minimap.account.base.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountProfilePaymentTaxi implements Serializable {
    public String agreementNo = null;
    public String authId = null;
    public String authOpenId = null;
    public int type = 0;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.type = jSONObject.optInt("type");
            this.agreementNo = jSONObject.optString("agreement_no");
            this.authId = jSONObject.optString("auth_id");
            this.authOpenId = jSONObject.optString("auth_openid");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", this.type);
        jSONObject.put("agreement_no", this.agreementNo);
        jSONObject.put("auth_id", this.authId);
        jSONObject.put("auth_openid", this.authOpenId);
        return jSONObject;
    }
}
