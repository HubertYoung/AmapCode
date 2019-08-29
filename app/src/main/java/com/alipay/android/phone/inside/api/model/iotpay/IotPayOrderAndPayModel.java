package com.alipay.android.phone.inside.api.model.iotpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotpay.IotPayOrderAndPayCode;

public class IotPayOrderAndPayModel extends IotPayBaseModel<IotPayOrderAndPayCode> {
    private boolean isFacePay;
    private String outOrderNo;
    private String payCode;
    private long queryTimeout;
    private String subject;
    private String totalAmount;

    public String getPayCode() {
        return this.payCode;
    }

    public void setPayCode(String str) {
        this.payCode = str;
    }

    public String getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(String str) {
        this.totalAmount = str;
    }

    public String getOutOrderNo() {
        return this.outOrderNo;
    }

    public void setOutOrderNo(String str) {
        this.outOrderNo = str;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String str) {
        this.subject = str;
    }

    public long getQueryTimeout() {
        return this.queryTimeout;
    }

    public void setQueryTimeout(long j) {
        this.queryTimeout = j;
    }

    public boolean isFacePay() {
        return this.isFacePay;
    }

    public void setFacePay(boolean z) {
        this.isFacePay = z;
    }

    public IBizOperation<IotPayOrderAndPayCode> getOperaion() {
        return new IBizOperation<IotPayOrderAndPayCode>() {
            public IotPayOrderAndPayCode parseResultCode(String str, String str2) {
                return IotPayOrderAndPayCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_PAY_ORDER_AND_PAY;
            }
        };
    }
}
