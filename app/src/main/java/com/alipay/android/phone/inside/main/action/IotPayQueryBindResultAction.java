package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.iotpay.IotPayQueryBindResultCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class IotPayQueryBindResultAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_PAY_QUERY_BIND_RESULT.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotPayQueryBindResultCode.SUCCESS, ActionEnum.IOT_PAY_QUERY_BIND_RESULT.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_PAY_PLUGIN_QUERY_BIND_RESULT", jSONObject);
            String string = bundle.getString("code");
            if (TextUtils.equals(string, "EXIT_PULL")) {
                operationResult.setCode(IotPayQueryBindResultCode.SUCCESS);
            } else if (TextUtils.equals(string, "CONTINUE_PULL")) {
                operationResult.setCode(IotPayQueryBindResultCode.NOT_FINISH);
            } else if (TextUtils.equals(string, "PARAMS_ILLEGAL")) {
                operationResult.setCode(IotPayQueryBindResultCode.PARAMS_ILLEGAL);
            } else if (TextUtils.equals(string, "TOKEN_EXPIRE")) {
                operationResult.setCode(IotPayQueryBindResultCode.TOKEN_EXPIRE);
            } else {
                operationResult.setCode(IotPayQueryBindResultCode.FAIL);
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Exception e) {
            e.printStackTrace();
            operationResult.setCode(IotPayQueryBindResultCode.FAIL);
            LoggerFactory.e().a((String) "iotpay", (String) "bindResultEx", (Throwable) e);
        }
        return operationResult;
    }
}
