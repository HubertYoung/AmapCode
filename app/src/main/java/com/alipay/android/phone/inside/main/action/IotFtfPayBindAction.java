package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.ftfpay.IotFtfPayBindCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class IotFtfPayBindAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_FTF_PAY_BIND.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotFtfPayBindCode.SUCCESS, ActionEnum.IOT_FTF_PAY_BIND.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_FTF_PAY_PLUGIN_BIND", jSONObject);
            String string = bundle.getString("code");
            if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(IotFtfPayBindCode.SUCCESS);
            } else if (TextUtils.equals(string, "PARAMS_ILLEGAL")) {
                operationResult.setCode(IotFtfPayBindCode.PARAMS_ILLEGAL);
            } else {
                operationResult.setCode(IotFtfPayBindCode.FAILED);
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Exception e) {
            operationResult.setCode(IotFtfPayBindCode.FAILED);
            LoggerFactory.e().a((String) "ftfpay", (String) "IotFtfPayBindEx", (Throwable) e);
        }
        return operationResult;
    }
}
