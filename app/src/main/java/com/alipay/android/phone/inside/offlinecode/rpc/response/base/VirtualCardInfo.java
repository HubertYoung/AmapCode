package com.alipay.android.phone.inside.offlinecode.rpc.response.base;

import com.alipay.android.phone.inside.offlinecode.rpc.utils.ExtInfoUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class VirtualCardInfo {
    public List<VirtualCardModel> cardModels = new ArrayList();
    public List<VirtualCardScene> cardScenes = new ArrayList();
    public String cardTitle;
    public String cardType;
    public List<String> downgradedFunctions = new ArrayList();
    public Map<String, String> dynamicConfig = new HashMap();
    public Map<String, String> extInfo = new HashMap();
    public String functionType;
    public String gmtCreate;
    public String gmtModified;
    public Object menuConfig;
    public Map<String, String> styleConfig;

    public JSONObject serializeJson() throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("gmtCreate", this.gmtCreate);
        jSONObject.put("gmtModified", this.gmtModified);
        jSONObject.put("cardType", this.cardType);
        jSONObject.put("functionType", this.functionType);
        jSONObject.put("cardTitle", this.cardTitle);
        jSONObject.put("menuConfig", this.menuConfig);
        jSONObject.put("styleConfig", ExtInfoUtil.buildExtInfo(this.styleConfig));
        if (this.cardScenes != null) {
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < this.cardScenes.size(); i++) {
                VirtualCardScene virtualCardScene = this.cardScenes.get(i);
                if (virtualCardScene != null) {
                    jSONArray.put(virtualCardScene.serializeJson());
                }
            }
            jSONObject.put("cardScenes", jSONArray);
        }
        if (this.cardModels != null) {
            JSONArray jSONArray2 = new JSONArray();
            for (int i2 = 0; i2 < this.cardModels.size(); i2++) {
                VirtualCardModel virtualCardModel = this.cardModels.get(i2);
                if (virtualCardModel != null) {
                    jSONArray2.put(virtualCardModel.serializeJson());
                }
            }
            jSONObject.put("cardModels", jSONArray2);
        }
        if (this.extInfo != null) {
            jSONObject.put("extInfo", ExtInfoUtil.buildExtInfo(this.extInfo));
        }
        return jSONObject;
    }
}
