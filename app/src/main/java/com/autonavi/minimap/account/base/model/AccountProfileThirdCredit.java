package com.autonavi.minimap.account.base.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountProfileThirdCredit implements Serializable {
    public String alipayUid = null;
    public String certNo = null;
    public String nickName = null;
    public String userName = null;
    public int zmScore = 0;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.alipayUid = jSONObject.optString("alipay_uid");
            this.zmScore = jSONObject.optInt("zm_score");
            this.nickName = jSONObject.optString("nick_name");
            this.certNo = jSONObject.optString("cert_no");
            this.userName = jSONObject.optString("user_name");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("alipay_uid", this.alipayUid);
        jSONObject.put("zm_score", this.zmScore);
        jSONObject.put("nick_name", this.nickName);
        jSONObject.put("cert_no", this.certNo);
        jSONObject.put("user_name", this.userName);
        return jSONObject;
    }
}
