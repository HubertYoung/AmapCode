package com.alipay.android.phone.inside.api.model.request;

import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.model.operation.LoginOutOp;
import com.alipay.android.phone.inside.api.result.code.LogoutCode;

public class LoginOutModel extends BaseOpenAuthModel<LogoutCode> {
    private String authBizData;

    public String getAuthBizData() {
        return this.authBizData;
    }

    public void setAuthBizData(String str) {
        this.authBizData = str;
    }

    public IBizOperation<LogoutCode> getOperaion() {
        return new LoginOutOp();
    }
}
