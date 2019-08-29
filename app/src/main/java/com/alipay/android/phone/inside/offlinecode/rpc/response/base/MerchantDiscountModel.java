package com.alipay.android.phone.inside.offlinecode.rpc.response.base;

import com.alipay.mobile.scansdk.constant.Constants;
import org.json.JSONObject;

public class MerchantDiscountModel {
    public String actionUrl;
    public String img;
    public String text;

    public JSONObject serializeJSON() throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("text", this.text);
        jSONObject.put("img", this.img);
        jSONObject.put(Constants.SERVICE_ACTION_URL, this.actionUrl);
        return jSONObject;
    }
}
