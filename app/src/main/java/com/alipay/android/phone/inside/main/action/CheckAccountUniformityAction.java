package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.account.CheckAccountUniformityCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class CheckAccountUniformityAction implements SdkAction {
    public final String a() {
        return ActionEnum.CHECK_ACCOUNT_UNIFORMITY.getActionName();
    }

    public final OperationResult<CheckAccountUniformityCode> a(JSONObject jSONObject) {
        OperationResult<CheckAccountUniformityCode> operationResult = new OperationResult<>(CheckAccountUniformityCode.SUCCESS, ActionEnum.CHECK_ACCOUNT_UNIFORMITY.getActionName());
        try {
            if (((Bundle) ServiceExecutor.b("COMMONBIZ_SERVICE_CHECKACCOUNTUNIFORMITY", jSONObject)).getBoolean("uniformity", false)) {
                operationResult.setCode(CheckAccountUniformityCode.SUCCESS);
            } else {
                operationResult.setCode(CheckAccountUniformityCode.INCONFORMITY);
            }
        } catch (Exception e) {
            LoggerFactory.e().a((String) "inside", (String) "checkau", (Throwable) e);
            operationResult.setCode(CheckAccountUniformityCode.INNER_EX);
        }
        return operationResult;
    }
}
