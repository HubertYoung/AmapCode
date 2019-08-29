package com.autonavi.minimap.account.payment.model;

import com.autonavi.minimap.account.base.model.AccountProfile;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentMobileResponse extends dkm implements Serializable {
    public AccountProfile data = new AccountProfile();
    public String remain = null;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            JSONObject optJSONObject = jSONObject.optJSONObject("data");
            if (optJSONObject != null) {
                JSONObject optJSONObject2 = optJSONObject.optJSONObject("profile");
                if (optJSONObject2 != null) {
                    AccountProfile accountProfile = new AccountProfile();
                    accountProfile.fromJson(optJSONObject2);
                    this.data = accountProfile;
                }
            }
            this.remain = jSONObject.optString("remain");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        json.put("data", this.data.toJson());
        json.put("remain", this.remain);
        return json;
    }
}
