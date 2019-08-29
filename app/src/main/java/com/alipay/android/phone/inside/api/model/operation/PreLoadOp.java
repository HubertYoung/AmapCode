package com.alipay.android.phone.inside.api.model.operation;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.code.PreLoadCode;

public class PreLoadOp implements IBizOperation<PreLoadCode> {
    public PreLoadCode parseResultCode(String str, String str2) {
        return PreLoadCode.parse(str2);
    }

    public ActionEnum getAction() {
        return ActionEnum.PRE_LOAD;
    }
}
