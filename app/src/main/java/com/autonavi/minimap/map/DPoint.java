package com.autonavi.minimap.map;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import org.json.JSONException;
import org.json.JSONObject;

public class DPoint {
    public double x;
    public double y;

    public DPoint(double d, double d2) {
        this.x = d;
        this.y = d2;
    }

    public DPoint() {
    }

    public String toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(DictionaryKeys.CTRLXY_X, this.x);
            jSONObject.put(DictionaryKeys.CTRLXY_Y, this.y);
            return jSONObject.toString();
        } catch (JSONException unused) {
            return null;
        }
    }
}
