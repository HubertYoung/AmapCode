package com.alipay.android.phone.inside.api.model.operation;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.code.OnlinePayCode;

public class OnlinePayOp implements IBizOperation<OnlinePayCode> {
    public OnlinePayCode parseResultCode(String str, String str2) {
        return OnlinePayCode.parse(str2);
    }

    public ActionEnum getAction() {
        return ActionEnum.ONLINE_PAY;
    }
}
