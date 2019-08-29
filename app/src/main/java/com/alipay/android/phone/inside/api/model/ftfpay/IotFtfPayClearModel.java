package com.alipay.android.phone.inside.api.model.ftfpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.ftfpay.IotFtfPayClearCode;

public class IotFtfPayClearModel extends IotFtfPayBaseModel<IotFtfPayClearCode> {
    public IBizOperation<IotFtfPayClearCode> getOperaion() {
        return new IBizOperation<IotFtfPayClearCode>() {
            public IotFtfPayClearCode parseResultCode(String str, String str2) {
                return IotFtfPayClearCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_FTF_PAY_CLEAR;
            }
        };
    }
}
