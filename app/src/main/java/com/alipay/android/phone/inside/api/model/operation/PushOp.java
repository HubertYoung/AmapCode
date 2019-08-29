package com.alipay.android.phone.inside.api.model.operation;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.ResultCode;
import com.alipay.android.phone.inside.api.result.code.LogoutCode;
import com.alipay.android.phone.inside.api.result.code.PushCode;
import com.alipay.android.phone.inside.api.result.code.QueryPayCode;

public class PushOp implements IBizOperation<ResultCode> {
    public ResultCode parseResultCode(String str, String str2) {
        if (TextUtils.equals(str, ActionEnum.QUERY_PAY_RESULT.getActionName())) {
            return QueryPayCode.parse(str2);
        }
        if (TextUtils.equals(str, ActionEnum.LOGIN_OUT.getActionName())) {
            return LogoutCode.parse(str2);
        }
        if (TextUtils.equals(str, ActionEnum.PUSH.getActionName())) {
            return PushCode.parse(str2);
        }
        return null;
    }

    public ActionEnum getAction() {
        return ActionEnum.PUSH;
    }
}
