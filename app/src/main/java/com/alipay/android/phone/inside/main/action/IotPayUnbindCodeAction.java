package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.ali.auth.third.login.UTConstants;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.iotpay.IotPayUnbindCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class IotPayUnbindCodeAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_PAY_UNBIND_CODE.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotPayUnbindCode.SUCCESS, ActionEnum.IOT_PAY_UNBIND_CODE.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_PAY_PLUGIN_UNBIND_CODE", jSONObject);
            String string = bundle.getString("code");
            if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(IotPayUnbindCode.SUCCESS);
            } else if (TextUtils.equals(string, UTConstants.E_COOOPERATION_UNBIND)) {
                operationResult.setCode(IotPayUnbindCode.UNBIND);
            } else {
                operationResult.setCode(IotPayUnbindCode.FAIL);
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Exception e) {
            operationResult.setCode(IotPayUnbindCode.FAIL);
            LoggerFactory.e().a((String) "iotpay", (String) "unbindCodeEx", (Throwable) e);
        }
        return operationResult;
    }
}
