package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.code.GenerateCodeCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.alipay.android.phone.inside.proxy.util.BundleUtils;
import org.json.JSONObject;

public class GenerateCodeAction implements SdkAction {
    public final String a() {
        return ActionEnum.GENERATE_CODE.getActionName();
    }

    public final OperationResult<GenerateCodeCode> a(JSONObject jSONObject) {
        OperationResult<GenerateCodeCode> operationResult = new OperationResult<>(GenerateCodeCode.FAILED, ActionEnum.GENERATE_CODE.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("BARCODE_PLUGIN_GEN_CODE", jSONObject);
            String string = bundle.getString("resultCode");
            Bundle bundle2 = bundle.getBundle("barcode");
            if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                operationResult.setResult(BundleUtils.a(bundle2));
                operationResult.setCode(GenerateCodeCode.SUCCESS);
            } else if (TextUtils.equals(string, GenBusCodeService.CODE_UNAUTH)) {
                operationResult.setCode(GenerateCodeCode.AUTH_INVALID);
            } else if (TextUtils.equals(string, GenBusCodeService.CODE_FAILED)) {
                operationResult.setCode(GenerateCodeCode.FAILED);
            } else {
                operationResult.setCode(GenerateCodeCode.FAILED);
            }
        } catch (Exception e) {
            LoggerFactory.e().a((String) "otp", (String) "GenerateCodeEx", (Throwable) e);
            operationResult.setCode(GenerateCodeCode.FAILED);
        }
        return operationResult;
    }
}
