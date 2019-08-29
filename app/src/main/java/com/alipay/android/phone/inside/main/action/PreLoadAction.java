package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.code.PreLoadCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class PreLoadAction implements SdkAction {
    public final String a() {
        return ActionEnum.PRE_LOAD.getActionName();
    }

    public final OperationResult<PreLoadCode> a(JSONObject jSONObject) {
        OperationResult<PreLoadCode> operationResult = new OperationResult<>(PreLoadCode.SUCCESS, ActionEnum.PRE_LOAD.getActionName());
        try {
            if (TextUtils.equals(((Bundle) ServiceExecutor.b("BARCODE_PLUGIN_PRE_LOAD", jSONObject)).getString("resultCode"), GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(PreLoadCode.SUCCESS);
            } else {
                operationResult.setCode(PreLoadCode.FAILED);
            }
        } catch (Throwable th) {
            operationResult.setCode(PreLoadCode.FAILED);
            LoggerFactory.e().a((String) "preload", (String) "PreLoadUnknowmEx", th);
        }
        return operationResult;
    }
}
