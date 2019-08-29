package com.alipay.android.phone.inside.api.model.operation;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.code.AuthCode;

public class AuthOp implements IBizOperation<AuthCode> {
    public AuthCode parseResultCode(String str, String str2) {
        return AuthCode.parse(str2);
    }

    public ActionEnum getAction() {
        return ActionEnum.AUTH;
    }
}
