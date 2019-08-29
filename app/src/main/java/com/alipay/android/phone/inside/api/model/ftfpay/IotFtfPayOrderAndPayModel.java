package com.alipay.android.phone.inside.api.model.ftfpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.ftfpay.IotFtfPayOrderAndPayCode;

public class IotFtfPayOrderAndPayModel extends IotFtfPayBaseModel<IotFtfPayOrderAndPayCode> {
    private boolean isFacePay;
    private String outOrderNo;
    private String payCode;
    private long queryTimeout;
    private String subject;
    private String terminalId;
    private String totalAmount;

    public String getTerminalId() {
        return this.terminalId;
    }

    public void setTerminalId(String str) {
        this.terminalId = str;
    }

    public boolean isFacePay() {
        return this.isFacePay;
    }

    public void setFacePay(boolean z) {
        this.isFacePay = z;
    }

    public String getPayCode() {
        return this.payCode;
    }

    public void setPayCode(String str) {
        this.payCode = str;
    }

    public String getOutOrderNo() {
        return this.outOrderNo;
    }

    public void setOutOrderNo(String str) {
        this.outOrderNo = str;
    }

    public String getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(String str) {
        this.totalAmount = str;
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

    public IBizOperation<IotFtfPayOrderAndPayCode> getOperaion() {
        return new IBizOperation<IotFtfPayOrderAndPayCode>() {
            public IotFtfPayOrderAndPayCode parseResultCode(String str, String str2) {
                return IotFtfPayOrderAndPayCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_FTF_PAY_ORDER_AND_PAY;
            }
        };
    }
}
