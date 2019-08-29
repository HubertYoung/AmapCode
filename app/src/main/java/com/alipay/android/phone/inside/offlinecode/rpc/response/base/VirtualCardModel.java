package com.alipay.android.phone.inside.offlinecode.rpc.response.base;

import org.json.JSONObject;

public class VirtualCardModel {
    public String cardBalance;
    public String cardNo;
    public MerchantDiscountModel discountModel;
    public String gmtCreate;

    public JSONObject serializeJson() throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("gmtCreate", this.gmtCreate);
        jSONObject.put("cardNo", this.cardNo);
        jSONObject.put("cardBalance", this.cardBalance);
        if (this.discountModel != null) {
            jSONObject.put("discountModel", this.discountModel.serializeJSON());
        }
        return jSONObject;
    }
}
