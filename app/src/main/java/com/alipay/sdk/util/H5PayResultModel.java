package com.alipay.sdk.util;

public class H5PayResultModel {
    private String resultCode;
    private String returnUrl;

    public String getReturnUrl() {
        return this.returnUrl;
    }

    public void setReturnUrl(String str) {
        this.returnUrl = str;
    }

    public String getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(String str) {
        this.resultCode = str;
    }
}
