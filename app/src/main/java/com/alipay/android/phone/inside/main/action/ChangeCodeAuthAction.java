package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.changecode.ChangeCodeAuthCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import org.json.JSONObject;

public class ChangeCodeAuthAction implements SdkAction {
    public final String a() {
        return ActionEnum.CHANGE_CODE_AUTH_ACTION.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(ChangeCodeAuthCode.parse("xxx"), ActionEnum.CHANGE_CODE_AUTH_ACTION.getActionName());
        ServiceExecutor.a((String) "CHANGE_CODE_PLUGIN_AUTH", new Bundle());
        return operationResult;
    }
}
