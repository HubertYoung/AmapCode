package com.alipay.android.phone.inside.api.model;

import com.alipay.android.phone.inside.api.result.ResultCode;

public abstract class BaseOpenAuthModel<T extends ResultCode> extends BaseModel<T> {
    private String alipayUserId;
    private String authToken;
    private boolean isOpenAuthLogin;

    public boolean isOpenAuthLogin() {
        return this.isOpenAuthLogin;
    }

    public void setOpenAuthLogin(boolean z) {
        this.isOpenAuthLogin = z;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public void setAuthToken(String str) {
        this.authToken = str;
    }

    public String getAlipayUserId() {
        return this.alipayUserId;
    }

    public void setAlipayUserId(String str) {
        this.alipayUserId = str;
    }
}
