package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.ali.auth.third.login.UTConstants;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.iotpay.IotPayQueryUnbindResultCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class IotPayQueryUnbindResultAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_PAY_QUERY_UNBIND_RESULT.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotPayQueryUnbindResultCode.SUCCESS, ActionEnum.IOT_PAY_QUERY_UNBIND_RESULT.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_PAY_PLUGIN_QUERY_UNBIND_RESULT", jSONObject);
            String string = bundle.getString("code");
            if (TextUtils.equals(string, "EXIT_PULL")) {
                operationResult.setCode(IotPayQueryUnbindResultCode.SUCCESS);
            } else if (TextUtils.equals(string, "CONTINUE_PULL")) {
                operationResult.setCode(IotPayQueryUnbindResultCode.NOT_FINISH);
            } else if (TextUtils.equals(string, UTConstants.E_COOOPERATION_UNBIND)) {
                operationResult.setCode(IotPayQueryUnbindResultCode.PARAMS_ILLEGAL);
            } else {
                operationResult.setCode(IotPayQueryUnbindResultCode.FAIL);
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Exception e) {
            operationResult.setCode(IotPayQueryUnbindResultCode.FAIL);
            LoggerFactory.e().a((String) "iotpay", (String) "unbindResultEx", (Throwable) e);
        }
        return operationResult;
    }
}
