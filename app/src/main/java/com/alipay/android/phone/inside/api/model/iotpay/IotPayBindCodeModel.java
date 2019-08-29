package com.alipay.android.phone.inside.api.model.iotpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotpay.IotPayBindCode;

public class IotPayBindCodeModel extends IotPayBaseModel<IotPayBindCode> {
    public IBizOperation<IotPayBindCode> getOperaion() {
        return new IBizOperation<IotPayBindCode>() {
            public IotPayBindCode parseResultCode(String str, String str2) {
                return IotPayBindCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_PAY_BIND_CODE;
            }
        };
    }
}
