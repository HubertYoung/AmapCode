package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.code.AuthCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class AuthAction implements SdkAction {
    public final String a() {
        return ActionEnum.AUTH.getActionName();
    }

    public final OperationResult<AuthCode> a(JSONObject jSONObject) {
        LoggerFactory.f().e("inside", "AuthAction::startAction");
        OperationResult<AuthCode> operationResult = new OperationResult<>(AuthCode.CANCEL, ActionEnum.AUTH.getActionName());
        if (TextUtils.isEmpty(jSONObject.optString("authBizData"))) {
            LoggerFactory.e().a("auth", "BarcodeAuthParamsIllegal");
            operationResult.setCode(AuthCode.PARAMS_ILLEGAL);
            return operationResult;
        }
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("BARCODE_PLUGIN_AUTH", jSONObject);
            String string = bundle.getString("code");
            String string2 = bundle.getString("result");
            if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(AuthCode.SUCCESS);
            } else {
                TextUtils.equals(string, GenBusCodeService.CODE_TIMEOUT);
                operationResult.setCode(AuthCode.FAILED);
            }
            operationResult.setResult(string2);
        } catch (Throwable unused) {
            LoggerFactory.e().a("auth", "BarcodeAuthEx");
            operationResult.setCode(AuthCode.FAILED);
        }
        return operationResult;
    }
}
