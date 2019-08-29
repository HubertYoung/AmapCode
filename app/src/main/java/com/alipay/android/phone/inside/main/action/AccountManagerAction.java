package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.account.AccountManagerCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import org.json.JSONObject;

public class AccountManagerAction implements SdkAction {
    public final String a() {
        return ActionEnum.ACCOUNT_MANAGER_ACTION.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(AccountManagerCode.SUCCESS, ActionEnum.ACCOUNT_MANAGER_ACTION.getActionName());
        String optString = jSONObject.optString("isThirdPartyApp", "");
        Bundle bundle = new Bundle();
        if (TextUtils.equals(optString, "true")) {
            bundle.putString("insideLoginType", "withoutPwd");
        } else {
            bundle.putString("insideLoginType", "normalLogin");
        }
        ServiceExecutor.a((String) "ACCOUNT_MANAGER_SERVICE", bundle);
        return operationResult;
    }
}
