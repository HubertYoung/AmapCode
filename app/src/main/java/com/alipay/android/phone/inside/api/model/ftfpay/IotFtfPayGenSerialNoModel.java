package com.alipay.android.phone.inside.api.model.ftfpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.ftfpay.IotFtfPayGenSerialNoCode;

public class IotFtfPayGenSerialNoModel extends IotFtfPayBaseModel<IotFtfPayGenSerialNoCode> {
    public IBizOperation<IotFtfPayGenSerialNoCode> getOperaion() {
        return new IBizOperation<IotFtfPayGenSerialNoCode>() {
            public IotFtfPayGenSerialNoCode parseResultCode(String str, String str2) {
                return IotFtfPayGenSerialNoCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_FTF_PAY_GEN_SERIAL_NO;
            }
        };
    }
}
