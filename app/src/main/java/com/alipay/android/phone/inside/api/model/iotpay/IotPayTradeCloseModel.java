package com.alipay.android.phone.inside.api.model.iotpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotpay.IotPayTradeCloseCode;

public class IotPayTradeCloseModel extends IotPayBaseModel<IotPayTradeCloseCode> {
    private String outOrderNo;
    private String reason;

    public String getOutOrderNo() {
        return this.outOrderNo;
    }

    public void setOutOrderNo(String str) {
        this.outOrderNo = str;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String str) {
        this.reason = str;
    }

    public IBizOperation<IotPayTradeCloseCode> getOperaion() {
        return new IBizOperation<IotPayTradeCloseCode>() {
            public IotPayTradeCloseCode parseResultCode(String str, String str2) {
                return IotPayTradeCloseCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_PAY_TRADE_CLOSE;
            }
        };
    }
}
