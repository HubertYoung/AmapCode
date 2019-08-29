package com.alipay.android.phone.inside.api.model.operation;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.code.SwitchUserCode;

public class SwitchUserOp implements IBizOperation<SwitchUserCode> {
    public SwitchUserCode parseResultCode(String str, String str2) {
        return null;
    }

    public ActionEnum getAction() {
        return ActionEnum.SWITCH_USER;
    }
}
