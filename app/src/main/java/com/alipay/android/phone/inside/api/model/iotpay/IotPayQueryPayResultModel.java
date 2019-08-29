package com.alipay.android.phone.inside.api.model.iotpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotpay.IotPayQueryPayResultCode;

public class IotPayQueryPayResultModel extends IotPayBaseModel<IotPayQueryPayResultCode> {
    private String outOrderNo;

    public String getOutOrderNo() {
        return this.outOrderNo;
    }

    public void setOutOrderNo(String str) {
        this.outOrderNo = str;
    }

    public IBizOperation<IotPayQueryPayResultCode> getOperaion() {
        return new IBizOperation<IotPayQueryPayResultCode>() {
            public IotPayQueryPayResultCode parseResultCode(String str, String str2) {
                return IotPayQueryPayResultCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_PAY_QUERY_PAY_RESULT;
            }
        };
    }
}
