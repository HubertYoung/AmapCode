package com.oppo.oms.sdk.entity;

import org.json.JSONObject;

public class FeatureEntity {
    public String appPackage;
    public String certVersion;
    public String statusWord;

    public String toString() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("certVersion", this.certVersion);
            jSONObject.put("statusWord", this.statusWord);
            jSONObject.put("appPackage", this.appPackage);
            return jSONObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return super.toString();
        }
    }
}
