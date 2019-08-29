package com.alipay.android.phone.inside.api.model.ftfpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.ftfpay.IotFtfPayBindCode;

public class IotFtfPayBindModel extends IotFtfPayBaseModel<IotFtfPayBindCode> {
    protected String activeToken;

    public String getActiveToken() {
        return this.activeToken;
    }

    public void setActiveToken(String str) {
        this.activeToken = str;
    }

    public IBizOperation<IotFtfPayBindCode> getOperaion() {
        return new IBizOperation<IotFtfPayBindCode>() {
            public IotFtfPayBindCode parseResultCode(String str, String str2) {
                return IotFtfPayBindCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_FTF_PAY_BIND;
            }
        };
    }
}
