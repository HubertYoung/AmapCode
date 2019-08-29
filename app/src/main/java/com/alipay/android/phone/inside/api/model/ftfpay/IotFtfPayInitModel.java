package com.alipay.android.phone.inside.api.model.ftfpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.ftfpay.IotFtfPayInitCode;

public class IotFtfPayInitModel extends IotFtfPayBaseModel<IotFtfPayInitCode> {
    protected String deviceModel;

    public String getDeviceModel() {
        return this.deviceModel;
    }

    public void setDeviceModel(String str) {
        this.deviceModel = str;
    }

    public IBizOperation<IotFtfPayInitCode> getOperaion() {
        return new IBizOperation<IotFtfPayInitCode>() {
            public IotFtfPayInitCode parseResultCode(String str, String str2) {
                return IotFtfPayInitCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_FTF_PAY_INIT;
            }
        };
    }
}
