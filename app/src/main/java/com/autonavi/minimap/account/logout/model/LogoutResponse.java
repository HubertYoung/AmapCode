package com.autonavi.minimap.account.logout.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class LogoutResponse extends dkm implements Serializable {
    public LogoutOrderConf orderConf = new LogoutOrderConf();

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            JSONObject optJSONObject = jSONObject.optJSONObject("order_conf");
            if (optJSONObject != null) {
                LogoutOrderConf logoutOrderConf = new LogoutOrderConf();
                logoutOrderConf.fromJson(optJSONObject);
                this.orderConf = logoutOrderConf;
            }
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        json.put("order_conf", this.orderConf.toJson());
        return json;
    }
}
