package com.autonavi.minimap.bundle.share.passphrase.model;

import com.alipay.mobile.beehive.eventbus.Subscribe;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class PassphraseResponseData implements Serializable {
    public String actionScheme = null;
    public String activityId = null;
    public String background = null;
    public String btnColor = null;
    public String btnText = null;
    public String btnTextColor = null;
    public int codeType = 0;
    public String targetName = null;
    public String tips = null;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.codeType = jSONObject.optInt("code_type");
            this.actionScheme = jSONObject.optString("action_scheme");
            this.targetName = jSONObject.optString("target_name");
            this.tips = jSONObject.optString(ModulePoi.TIPS);
            this.btnText = jSONObject.optString("btn_text");
            this.btnTextColor = jSONObject.optString("btn_text_color");
            this.btnColor = jSONObject.optString("btn_color");
            this.background = jSONObject.optString(Subscribe.THREAD_BACKGROUND);
            this.activityId = jSONObject.optString("activity_id");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("code_type", this.codeType);
        jSONObject.put("action_scheme", this.actionScheme);
        jSONObject.put("target_name", this.targetName);
        jSONObject.put(ModulePoi.TIPS, this.tips);
        jSONObject.put("btn_text", this.btnText);
        jSONObject.put("btn_text_color", this.btnTextColor);
        jSONObject.put("btn_color", this.btnColor);
        jSONObject.put(Subscribe.THREAD_BACKGROUND, this.background);
        jSONObject.put("activity_id", this.activityId);
        return jSONObject;
    }
}
