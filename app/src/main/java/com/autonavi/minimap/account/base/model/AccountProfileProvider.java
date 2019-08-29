package com.autonavi.minimap.account.base.model;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountProfileProvider implements Serializable {
    public String aUserId = null;
    public HashMap<String, String> alipayTokenMap = new HashMap<>();
    public String authId = null;
    public String authToken = null;
    public String authUsername = null;
    public int provider = 0;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.provider = jSONObject.optInt("provider");
            this.authId = jSONObject.optString("auth_id");
            this.aUserId = jSONObject.optString("a_user_id");
            this.authUsername = jSONObject.optString("auth_username");
            this.authToken = jSONObject.optString("auth_token");
            String optString = jSONObject.optString("scope_token");
            if (!TextUtils.isEmpty(optString)) {
                this.alipayTokenMap.putAll((Map) JSON.parse(optString));
            }
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("provider", this.provider);
        jSONObject.put("auth_id", this.authId);
        jSONObject.put("a_user_id", this.aUserId);
        jSONObject.put("auth_username", this.authUsername);
        jSONObject.put("auth_token", this.authToken);
        jSONObject.put("scope_token", JSON.toJSONString(this.alipayTokenMap));
        return jSONObject;
    }
}
