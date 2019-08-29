package com.alipay.android.phone.inside.api.model.iotpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotpay.IotPayUnbindCode;

public class IotPayUnbindCodeModel extends IotPayBaseModel<IotPayUnbindCode> {
    public IBizOperation<IotPayUnbindCode> getOperaion() {
        return new IBizOperation<IotPayUnbindCode>() {
            public IotPayUnbindCode parseResultCode(String str, String str2) {
                return IotPayUnbindCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_PAY_UNBIND_CODE;
            }
        };
    }
}
