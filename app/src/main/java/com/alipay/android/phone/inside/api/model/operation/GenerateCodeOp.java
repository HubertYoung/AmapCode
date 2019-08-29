package com.alipay.android.phone.inside.api.model.operation;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.code.GenerateCodeCode;

public class GenerateCodeOp implements IBizOperation<GenerateCodeCode> {
    public GenerateCodeCode parseResultCode(String str, String str2) {
        return GenerateCodeCode.parse(str2);
    }

    public ActionEnum getAction() {
        return ActionEnum.GENERATE_CODE;
    }
}
