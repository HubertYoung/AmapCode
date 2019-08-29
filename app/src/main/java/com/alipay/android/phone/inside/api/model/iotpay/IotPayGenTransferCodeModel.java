package com.alipay.android.phone.inside.api.model.iotpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotpay.IotPayGenTransferCode;

public class IotPayGenTransferCodeModel extends IotPayBaseModel<IotPayGenTransferCode> {
    public IBizOperation<IotPayGenTransferCode> getOperaion() {
        return new IBizOperation<IotPayGenTransferCode>() {
            public IotPayGenTransferCode parseResultCode(String str, String str2) {
                return IotPayGenTransferCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_PAY_GEN_TRANSFER_CODE;
            }
        };
    }
}
