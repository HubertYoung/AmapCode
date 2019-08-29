package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.ali.auth.third.login.UTConstants;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.iotpay.IotPayCheckBindStatusCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class IotPayCheckBindStatusAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_PAY_CHECK_BIND_STATUS.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotPayCheckBindStatusCode.BIND, ActionEnum.IOT_PAY_CHECK_BIND_STATUS.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_PAY_PLUGIN_CHECK_BIND_STATUS", jSONObject);
            String string = bundle.getString("code");
            if (TextUtils.equals(string, "BIND")) {
                operationResult.setCode(IotPayCheckBindStatusCode.BIND);
            } else if (TextUtils.equals(string, UTConstants.E_COOOPERATION_UNBIND)) {
                operationResult.setCode(IotPayCheckBindStatusCode.UNBIND);
            } else {
                operationResult.setCode(IotPayCheckBindStatusCode.FAILED);
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Exception e) {
            operationResult.setCode(IotPayCheckBindStatusCode.FAILED);
            LoggerFactory.e().a((String) "iotpay", (String) "checkBindEx", (Throwable) e);
        }
        return operationResult;
    }
}
