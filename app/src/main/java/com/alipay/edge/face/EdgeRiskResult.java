package com.alipay.edge.face;

import org.json.JSONObject;

public class EdgeRiskResult {
    public String data = "";
    public int needSync = 0;
    public String rdsResult = "";
    public int result = 0;
    public String sealedData = "";
    public int status = -1;

    public String toStringEx() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("result", this.result);
            jSONObject.put("status", this.status);
            jSONObject.put("rdsData", this.rdsResult);
            jSONObject.put("sealedData", this.sealedData);
            jSONObject.put("data", this.data);
        } catch (Exception unused) {
        }
        return jSONObject.toString();
    }
}
