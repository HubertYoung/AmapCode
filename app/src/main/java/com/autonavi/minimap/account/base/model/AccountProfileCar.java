package com.autonavi.minimap.account.base.model;

import com.alipay.sdk.packet.d;
import com.taobao.accs.common.Constants;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountProfileCar implements Serializable {
    public String device = null;
    public String manufacture = null;
    public String model = null;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.model = jSONObject.optString(Constants.KEY_MODEL);
            this.device = jSONObject.optString(d.n);
            this.manufacture = jSONObject.optString("manufacture");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(Constants.KEY_MODEL, this.model);
        jSONObject.put(d.n, this.device);
        jSONObject.put("manufacture", this.manufacture);
        return jSONObject;
    }
}
