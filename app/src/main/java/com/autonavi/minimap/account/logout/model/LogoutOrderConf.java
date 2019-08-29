package com.autonavi.minimap.account.logout.model;

import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class LogoutOrderConf implements Serializable {
    public String cancelText = null;
    public String okText = null;
    public String schema = null;
    public String text = null;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.schema = jSONObject.optString(ActionConstant.SCHEMA);
            this.text = jSONObject.optString("text");
            this.okText = jSONObject.optString("ok_text");
            this.cancelText = jSONObject.optString("cancel_text");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(ActionConstant.SCHEMA, this.schema);
        jSONObject.put("text", this.text);
        jSONObject.put("ok_text", this.okText);
        jSONObject.put("cancel_text", this.cancelText);
        return jSONObject;
    }
}
