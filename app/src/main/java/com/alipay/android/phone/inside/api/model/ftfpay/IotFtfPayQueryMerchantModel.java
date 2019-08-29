package com.alipay.android.phone.inside.api.model.ftfpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.ftfpay.IotFtfPayQueryMerchantCode;

public class IotFtfPayQueryMerchantModel extends IotFtfPayBaseModel<IotFtfPayQueryMerchantCode> {
    protected String activeToken;

    public String getActiveToken() {
        return this.activeToken;
    }

    public void setActiveToken(String str) {
        this.activeToken = str;
    }

    public IBizOperation<IotFtfPayQueryMerchantCode> getOperaion() {
        return new IBizOperation<IotFtfPayQueryMerchantCode>() {
            public IotFtfPayQueryMerchantCode parseResultCode(String str, String str2) {
                return IotFtfPayQueryMerchantCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_FTF_PAY_QUERY_MERCHANT;
            }
        };
    }
}
