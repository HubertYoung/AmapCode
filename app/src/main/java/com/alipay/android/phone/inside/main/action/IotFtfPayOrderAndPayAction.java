package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.ftfpay.IotFtfPayOrderAndPayCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class IotFtfPayOrderAndPayAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_FTF_PAY_ORDER_AND_PAY.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotFtfPayOrderAndPayCode.SUCCESS, ActionEnum.IOT_FTF_PAY_ORDER_AND_PAY.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_FTF_PAY_PLUGIN_ORDER_AND_PAY", jSONObject);
            String string = bundle.getString("code");
            if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(IotFtfPayOrderAndPayCode.SUCCESS);
            } else if (TextUtils.equals(string, "NOT_BIND")) {
                operationResult.setCode(IotFtfPayOrderAndPayCode.BIND_ERROR);
            } else {
                operationResult.setCode(IotFtfPayOrderAndPayCode.FAIL);
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Exception e) {
            operationResult.setCode(IotFtfPayOrderAndPayCode.FAIL);
            LoggerFactory.e().a((String) "ftfpay", (String) "IotFtfPayOrderAndPayEx", (Throwable) e);
        }
        return operationResult;
    }
}
