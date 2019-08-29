package com.alipay.android.phone.inside.main.action;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.aliautologin.AliAutoLoginCheckConditionCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class AliAutoLoginCheckConditionAction implements SdkAction {
    private final String a = "AliAutoLoginCheckConditionAction";

    public final String a() {
        return ActionEnum.ALI_AUTO_LOGIN_CHECK_CONDITION.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        LoggerFactory.f().e("AliAutoLoginCheckConditionAction", "AliAutoLoginCheckConditionAction::startAction");
        OperationResult operationResult = new OperationResult(AliAutoLoginCheckConditionCode.CAN_NOT_AUTO_LOGIN, ActionEnum.ALI_AUTO_LOGIN_CHECK_CONDITION.getActionName());
        try {
            if (((Boolean) ServiceExecutor.b("ALI_AUTO_LOGIN_CHECK_CONDITION_SERVICE", jSONObject)).booleanValue()) {
                operationResult.setCode(AliAutoLoginCheckConditionCode.CAN_AUTO_LOGIN);
            } else {
                operationResult.setCode(AliAutoLoginCheckConditionCode.CAN_NOT_AUTO_LOGIN);
            }
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "aliautologin", (String) "doActionAliAutoLoginCheckConditionAction", th);
            LoggerFactory.f().a((String) "AliAutoLoginCheckConditionAction", th);
        }
        return operationResult;
    }
}
