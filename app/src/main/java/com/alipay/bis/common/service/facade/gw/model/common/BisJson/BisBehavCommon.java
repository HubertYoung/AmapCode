package com.alipay.bis.common.service.facade.gw.model.common.BisJson;

public class BisBehavCommon {
    public String invtp;
    public String retry;
    public String tm;

    public String getInvtp() {
        return this.invtp;
    }

    public void setInvtp(String str) {
        this.invtp = str;
    }

    public String getTm() {
        return this.tm;
    }

    public void setTm(String str) {
        this.tm = str;
    }

    public String getRetry() {
        return this.retry;
    }

    public void setRetry(String str) {
        this.retry = str;
    }
}
