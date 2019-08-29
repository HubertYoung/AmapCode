package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.code.CodeInvalidCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class CodeInvalidAction implements SdkAction {
    public final String a() {
        return ActionEnum.CODE_INVALID.getActionName();
    }

    public final OperationResult<CodeInvalidCode> a(JSONObject jSONObject) {
        OperationResult<CodeInvalidCode> operationResult = new OperationResult<>(CodeInvalidCode.SUCCESS, ActionEnum.CODE_INVALID.getActionName());
        try {
            String string = ((Bundle) ServiceExecutor.b("BARCODE_PLUGIN_CODE_INVALID", jSONObject)).getString("resultCode");
            if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(CodeInvalidCode.SUCCESS);
            } else if (TextUtils.equals(string, "PARAMS_ERROR")) {
                operationResult.setCode(CodeInvalidCode.PARAMS_ILLEGAL);
            } else if (TextUtils.equals(string, "INNER_ERROR")) {
                operationResult.setCode(CodeInvalidCode.INNER_EX);
            } else if (TextUtils.equals(string, GenBusCodeService.CODE_FAILED)) {
                operationResult.setCode(CodeInvalidCode.FAILED);
            } else {
                operationResult.setCode(CodeInvalidCode.FAILED);
            }
        } catch (Throwable unused) {
            operationResult.setCode(CodeInvalidCode.INNER_EX);
        }
        return operationResult;
    }
}
