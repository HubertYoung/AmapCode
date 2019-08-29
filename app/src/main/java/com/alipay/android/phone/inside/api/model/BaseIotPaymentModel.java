package com.alipay.android.phone.inside.api.model;

import com.alipay.android.phone.inside.api.result.ResultCode;

public abstract class BaseIotPaymentModel<T extends ResultCode> extends BaseIotModel<T> {
    protected String ftoken;
    protected boolean isFacePay;
    protected String outOrderNo;
    protected String payCode;
    protected boolean queryResultPage;
    protected long queryTimeout;
    protected String subject;
    protected String terminalId;
    protected String totalAmount;

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

    public boolean isFacePay() {
        return this.isFacePay;
    }

    public void setFacePay(boolean z) {
        this.isFacePay = z;
    }

    public String getFtoken() {
        return this.ftoken;
    }

    public void setFtoken(String str) {
        this.ftoken = str;
    }

    public long getQueryTimeout() {
        return this.queryTimeout;
    }

    public void setQueryTimeout(long j) {
        this.queryTimeout = j;
    }

    public String getTerminalId() {
        return this.terminalId;
    }

    public void setTerminalId(String str) {
        this.terminalId = str;
    }

    public boolean isQueryResultPage() {
        return this.queryResultPage;
    }

    public void setQueryResultPage(boolean z) {
        this.queryResultPage = z;
    }
}
