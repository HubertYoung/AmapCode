package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.code.PreCheckCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.alipay.android.phone.inside.proxy.util.ServerTimeSyncUtil;
import org.json.JSONObject;

public class PreCheckAction implements SdkAction {
    public final String a() {
        return ActionEnum.PRE_CHECK.getActionName();
    }

    public final OperationResult<PreCheckCode> a(JSONObject jSONObject) {
        OperationResult<PreCheckCode> operationResult = new OperationResult<>(PreCheckCode.SUCCESS, ActionEnum.PRE_CHECK.getActionName());
        ServerTimeSyncUtil.a();
        try {
            String string = ((Bundle) ServiceExecutor.b("BARCODE_PLUGIN_CHECK_CODE", jSONObject)).getString("resultCode");
            if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(PreCheckCode.SUCCESS);
            } else if (TextUtils.equals(string, GenBusCodeService.CODE_UNAUTH)) {
                operationResult.setCode(PreCheckCode.AUTH_INVALID);
            } else {
                operationResult.setCode(PreCheckCode.FAILED);
            }
        } catch (Throwable th) {
            operationResult.setCode(PreCheckCode.FAILED);
            LoggerFactory.e().a((String) "precheck", (String) "PreCheckUnknowmEx", th);
        }
        return operationResult;
    }
}
