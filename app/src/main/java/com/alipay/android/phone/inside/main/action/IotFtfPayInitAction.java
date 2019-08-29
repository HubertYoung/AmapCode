package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.ftfpay.IotFtfPayInitCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class IotFtfPayInitAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_FTF_PAY_INIT.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotFtfPayInitCode.SUCCESS, ActionEnum.IOT_FTF_PAY_INIT.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_FTF_PAY_PLUGIN_INIT", jSONObject);
            if (TextUtils.equals(bundle.getString("code"), GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(IotFtfPayInitCode.SUCCESS);
            } else {
                operationResult.setCode(IotFtfPayInitCode.FAILED);
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Exception e) {
            operationResult.setCode(IotFtfPayInitCode.FAILED);
            LoggerFactory.e().a((String) "ftfpay", (String) "IotFtfPayInitEx", (Throwable) e);
        }
        return operationResult;
    }
}
