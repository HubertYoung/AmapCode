package com.alipay.mobile.framework.service.ext;

public class BizResult {
    public String appName;
    public String message;
    public int resultCode;
    public boolean success;

    public void setSuccess(boolean success2) {
        this.success = success2;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setResultCode(int resultCode2) {
        this.resultCode = resultCode2;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }

    public String getMessage() {
        return this.message;
    }

    public void setAppName(String appName2) {
        this.appName = appName2;
    }

    public String getAppName() {
        return this.appName;
    }
}
