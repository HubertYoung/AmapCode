package com.alipay.android.phone.inside.api.model.iotpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotpay.IotPayQueryBindResultCode;

public class IotPayQueryBindResultModel extends IotPayBaseModel<IotPayQueryBindResultCode> {
    private String bindToken;

    public String getBindToken() {
        return this.bindToken;
    }

    public void setBindToken(String str) {
        this.bindToken = str;
    }

    public IBizOperation<IotPayQueryBindResultCode> getOperaion() {
        return new IBizOperation<IotPayQueryBindResultCode>() {
            public IotPayQueryBindResultCode parseResultCode(String str, String str2) {
                return IotPayQueryBindResultCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_PAY_QUERY_BIND_RESULT;
            }
        };
    }
}
