package com.alipay.android.phone.inside.api.model.operation;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.code.LogoutCode;

public class LoginOutOp implements IBizOperation<LogoutCode> {
    public LogoutCode parseResultCode(String str, String str2) {
        return LogoutCode.parse(str2);
    }

    public ActionEnum getAction() {
        return ActionEnum.LOGIN_OUT;
    }
}
