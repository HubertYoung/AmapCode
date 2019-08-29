package com.alipay.android.phone.inside.api.model.smartsellv2;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.smartsellv2.PayAuthV2ResultCode;
import org.json.JSONObject;

public class PayAuthV2AppInfoModel extends BaseModel<PayAuthV2ResultCode> {
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

    public IBizOperation<PayAuthV2ResultCode> getOperaion() {
        return new IBizOperation<PayAuthV2ResultCode>() {
            public PayAuthV2ResultCode parseResultCode(String str, String str2) {
                return PayAuthV2ResultCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.SMART_SELL_PAY_AUTH_V2_APPINFO;
            }
        };
    }
}
