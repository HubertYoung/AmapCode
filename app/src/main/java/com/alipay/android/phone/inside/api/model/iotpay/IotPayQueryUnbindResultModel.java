package com.alipay.android.phone.inside.api.model.iotpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotpay.IotPayQueryUnbindResultCode;

public class IotPayQueryUnbindResultModel extends IotPayBaseModel<IotPayQueryUnbindResultCode> {
    public IBizOperation<IotPayQueryUnbindResultCode> getOperaion() {
        return new IBizOperation<IotPayQueryUnbindResultCode>() {
            public IotPayQueryUnbindResultCode parseResultCode(String str, String str2) {
                return IotPayQueryUnbindResultCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_PAY_QUERY_UNBIND_RESULT;
            }
        };
    }
}
