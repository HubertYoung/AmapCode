package com.alipay.android.phone.inside.api.model.ftfpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseIotPaymentModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.ftfpay.IotFtfPayCode;

public class IotFtfPayModel extends BaseIotPaymentModel<IotFtfPayCode> {
    public IBizOperation<IotFtfPayCode> getOperaion() {
        return new IBizOperation<IotFtfPayCode>() {
            public IotFtfPayCode parseResultCode(String str, String str2) {
                return IotFtfPayCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_FTF_PAY;
            }
        };
    }
}
