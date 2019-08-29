package com.autonavi.minimap.account.trust_token.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class TaobaoTrustLoginTokenResponse extends dkm implements Serializable {
    public TokenData token_data = new TokenData();

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            JSONObject optJSONObject = jSONObject.optJSONObject("token_data");
            if (optJSONObject != null) {
                TokenData tokenData = new TokenData();
                tokenData.fromJson(optJSONObject);
                this.token_data = tokenData;
            }
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        json.put("token_data", this.token_data.toJson());
        return json;
    }
}
