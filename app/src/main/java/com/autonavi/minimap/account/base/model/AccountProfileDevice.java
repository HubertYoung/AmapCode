package com.autonavi.minimap.account.base.model;

import com.amap.location.sdk.fusion.LocationParams;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountProfileDevice implements Serializable {
    public String dic = null;
    public String diu = null;
    public String div = null;
    public int os = 0;
    public String tid = null;
    public String token = null;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.diu = jSONObject.optString(LocationParams.PARA_COMMON_DIU);
            this.div = jSONObject.optString(LocationParams.PARA_COMMON_DIV);
            this.dic = jSONObject.optString(LocationParams.PARA_COMMON_DIC);
            this.os = jSONObject.optInt("os");
            this.tid = jSONObject.optString("tid");
            this.token = jSONObject.optString("token");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(LocationParams.PARA_COMMON_DIU, this.diu);
        jSONObject.put(LocationParams.PARA_COMMON_DIV, this.div);
        jSONObject.put(LocationParams.PARA_COMMON_DIC, this.dic);
        jSONObject.put("os", this.os);
        jSONObject.put("tid", this.tid);
        jSONObject.put("token", this.token);
        return jSONObject;
    }
}
