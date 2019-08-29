package com.autonavi.minimap.bundle.share.passphrase.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class PassphraseResponse extends dkm implements Serializable {
    public PassphraseResponseData data = new PassphraseResponseData();

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            JSONObject optJSONObject = jSONObject.optJSONObject("data");
            if (optJSONObject != null) {
                PassphraseResponseData passphraseResponseData = new PassphraseResponseData();
                passphraseResponseData.fromJson(optJSONObject);
                this.data = passphraseResponseData;
            }
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        json.put("data", this.data.toJson());
        return json;
    }
}
