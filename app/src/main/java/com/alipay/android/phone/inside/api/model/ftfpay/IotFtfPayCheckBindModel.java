package com.alipay.android.phone.inside.api.model.ftfpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.ftfpay.IotFtfPayCheckBindCode;

public class IotFtfPayCheckBindModel extends IotFtfPayBaseModel<IotFtfPayCheckBindCode> {
    private boolean checkLocalOnly;

    public boolean isCheckLocalOnly() {
        return this.checkLocalOnly;
    }

    public void setCheckLocalOnly(boolean z) {
        this.checkLocalOnly = z;
    }

    public IBizOperation<IotFtfPayCheckBindCode> getOperaion() {
        return new IBizOperation<IotFtfPayCheckBindCode>() {
            public IotFtfPayCheckBindCode parseResultCode(String str, String str2) {
                return IotFtfPayCheckBindCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_FTF_PAY_CHECK_BIND;
            }
        };
    }
}
