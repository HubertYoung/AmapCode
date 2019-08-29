package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.ftfpay.IotFtfPayClearCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class IotFtfPayClearAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_FTF_PAY_CLEAR.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotFtfPayClearCode.SUCCESS, ActionEnum.IOT_FTF_PAY_CLEAR.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_FTF_PAY_PLUGIN_CLEAR", jSONObject);
            if (TextUtils.equals(bundle.getString("code"), GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(IotFtfPayClearCode.SUCCESS);
            } else {
                operationResult.setCode(IotFtfPayClearCode.FAILED);
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Exception e) {
            operationResult.setCode(IotFtfPayClearCode.FAILED);
            LoggerFactory.e().a((String) "ftfpay", (String) "IotFtfPayClearEx", (Throwable) e);
        }
        return operationResult;
    }
}
