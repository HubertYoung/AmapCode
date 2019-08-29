package com.alipay.android.phone.inside.api.model.iotpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotpay.IotPayInitCode;

public class IotPayInitModel extends IotPayBaseModel<IotPayInitCode> {
    private String protocolModelId;

    public String getProtocolModelId() {
        return this.protocolModelId;
    }

    public void setProtocolModelId(String str) {
        this.protocolModelId = str;
    }

    public IBizOperation<IotPayInitCode> getOperaion() {
        return new IBizOperation<IotPayInitCode>() {
            public IotPayInitCode parseResultCode(String str, String str2) {
                return IotPayInitCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_PAY_INIT;
            }
        };
    }
}
