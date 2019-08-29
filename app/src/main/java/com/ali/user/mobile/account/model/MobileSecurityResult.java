package com.ali.user.mobile.account.model;

import java.io.Serializable;

public class MobileSecurityResult extends ToString implements Serializable {
    public String message = "";
    public String resultCode = "";
    public boolean success = false;

    public void setSuccess(boolean z) {
        this.success = z;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setResultCode(String str) {
        this.resultCode = str;
    }

    public String getResultCode() {
        return this.resultCode;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public String getMessage() {
        return this.message;
    }
}
