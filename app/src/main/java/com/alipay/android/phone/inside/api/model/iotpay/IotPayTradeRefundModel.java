package com.alipay.android.phone.inside.api.model.iotpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotpay.IotPayTradeRefundCode;

public class IotPayTradeRefundModel extends IotPayBaseModel<IotPayTradeRefundCode> {
    private String outOrderNo;

    public String getOutOrderNo() {
        return this.outOrderNo;
    }

    public void setOutOrderNo(String str) {
        this.outOrderNo = str;
    }

    public IBizOperation<IotPayTradeRefundCode> getOperaion() {
        return new IBizOperation<IotPayTradeRefundCode>() {
            public IotPayTradeRefundCode parseResultCode(String str, String str2) {
                return IotPayTradeRefundCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_PAY_TRADE_REFUND;
            }
        };
    }
}
