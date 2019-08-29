package com.alipay.android.phone.inside.api.model.smartsell;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.smartsell.PayAuthRequestCode;
import org.json.JSONObject;

public class PayAuthRequestModel extends BaseModel<PayAuthRequestCode> {
    private String authParams;

    public void addAuthParams(String str, String str2) {
        if (TextUtils.isEmpty(this.authParams)) {
            this.authParams = new JSONObject().toString();
        }
        try {
            JSONObject jSONObject = new JSONObject(this.authParams);
            jSONObject.put(str, str2);
            this.authParams = jSONObject.toString();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public String getAuthParams() {
        return this.authParams;
    }

    public IBizOperation<PayAuthRequestCode> getOperaion() {
        return new IBizOperation<PayAuthRequestCode>() {
            public PayAuthRequestCode parseResultCode(String str, String str2) {
                return PayAuthRequestCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.SMART_SELL_PAY_AUTH;
            }
        };
    }
}
