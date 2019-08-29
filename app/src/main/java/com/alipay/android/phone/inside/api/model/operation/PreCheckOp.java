package com.alipay.android.phone.inside.api.model.operation;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.code.PreCheckCode;

public class PreCheckOp implements IBizOperation<PreCheckCode> {
    public PreCheckCode parseResultCode(String str, String str2) {
        return PreCheckCode.parse(str2);
    }

    public ActionEnum getAction() {
        return ActionEnum.PRE_CHECK;
    }
}
