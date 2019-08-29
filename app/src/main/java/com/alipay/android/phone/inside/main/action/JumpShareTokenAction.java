package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.sharetoken.JumpShareTokenCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class JumpShareTokenAction implements SdkAction {
    public final String a() {
        return ActionEnum.JUMP_TOKEN.getActionName();
    }

    public final OperationResult<JumpShareTokenCode> a(JSONObject jSONObject) {
        OperationResult<JumpShareTokenCode> operationResult = new OperationResult<>(JumpShareTokenCode.FAILED, ActionEnum.JUMP_TOKEN.getActionName());
        String optString = jSONObject.optString("token");
        Bundle bundle = new Bundle();
        bundle.putString("token", optString);
        try {
            if (((Bundle) ServiceExecutor.b("SERVICE_JUMPBYTOKEN", bundle)).getBoolean("success")) {
                operationResult.setCode(JumpShareTokenCode.SUCCESS);
            }
        } catch (Throwable th) {
            LoggerFactory.f().b((String) "sharetoken", th);
        }
        return operationResult;
    }
}
