package com.alipay.android.phone.inside.api.model.smartsell;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.smartsell.PayAuthPreloadCode;

public class PayAuthPreloadModel extends BaseModel<PayAuthPreloadCode> {
    public IBizOperation<PayAuthPreloadCode> getOperaion() {
        return new IBizOperation<PayAuthPreloadCode>() {
            public PayAuthPreloadCode parseResultCode(String str, String str2) {
                return PayAuthPreloadCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.SMART_SELL_PAY_AUTH_PRELOAD;
            }
        };
    }
}
