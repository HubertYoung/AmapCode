package com.autonavi.minimap.account.appeal.model;

import com.autonavi.minimap.account.base.model.AccountProfile;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class AppealBindMobileData implements Serializable {
    public String errOrderId = null;
    public String errmsg = null;
    public AccountProfile profile = new AccountProfile();
    public String remain = null;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            JSONObject optJSONObject = jSONObject.optJSONObject("profile");
            if (optJSONObject != null) {
                AccountProfile accountProfile = new AccountProfile();
                accountProfile.fromJson(optJSONObject);
                this.profile = accountProfile;
            }
            this.remain = jSONObject.optString("remain");
            this.errmsg = jSONObject.optString("errmsg");
            this.errOrderId = jSONObject.optString("err_order_id");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("profile", this.profile.toJson());
        jSONObject.put("remain", this.remain);
        jSONObject.put("errmsg", this.errmsg);
        jSONObject.put("err_order_id", this.errOrderId);
        return jSONObject;
    }
}
