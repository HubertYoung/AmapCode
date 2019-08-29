package com.alipay.android.phone.inside.offlinecode.rpc.response.base;

import org.json.JSONObject;

public class VirtualCardSubScene {
    public String category;
    public String sceneCode;
    public String subCodeName;
    public String subSceneCode;

    public JSONObject serializeJSON() throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("sceneCode", this.sceneCode);
        jSONObject.put("subSceneCode", this.subSceneCode);
        jSONObject.put("category", this.category);
        jSONObject.put("subCodeName", this.subCodeName);
        return jSONObject;
    }
}
