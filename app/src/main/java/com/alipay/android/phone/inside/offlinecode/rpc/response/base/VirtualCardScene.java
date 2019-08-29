package com.alipay.android.phone.inside.offlinecode.rpc.response.base;

import com.alipay.android.phone.inside.offlinecode.rpc.utils.ExtInfoUtil;
import java.util.Map;
import org.json.JSONObject;

public class VirtualCardScene {
    public String cardType;
    public Map<String, String> extInfo;
    public String sceneCode;
    public String subCodeName;
    public String subSceneCode;

    public JSONObject serializeJson() throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("sceneCode", this.sceneCode);
        jSONObject.put("subSceneCode", this.subSceneCode);
        jSONObject.put("cardType", this.cardType);
        jSONObject.put("subCodeName", this.subCodeName);
        if (this.extInfo != null) {
            jSONObject.put("extInfo", ExtInfoUtil.buildExtInfo(this.extInfo));
        }
        return jSONObject;
    }
}
