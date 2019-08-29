package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.ftfpay.IotFtfPayCheckBindCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import org.json.JSONObject;

public class IotFtfPayCheckBindAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_FTF_PAY_CHECK_BIND.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        Bundle bundle;
        OperationResult operationResult = new OperationResult(IotFtfPayCheckBindCode.BIND, ActionEnum.IOT_FTF_PAY_CHECK_BIND.getActionName());
        try {
            if (jSONObject.optBoolean("checkLocalOnly", false)) {
                LoggerFactory.d().a("iotftfpay", BehaviorType.EVENT, "FtfPayCheckBind_LocalDataOnly");
                bundle = (Bundle) ServiceExecutor.b("IOT_FTF_PAY_PLUGIN_LOCAL_CHECK_BIND", jSONObject);
            } else {
                LoggerFactory.d().a("iotftfpay", BehaviorType.EVENT, "FtfPayCheckBind_LocalAndServer");
                bundle = (Bundle) ServiceExecutor.b("IOT_FTF_PAY_PLUGIN_CHECK_BIND", jSONObject);
            }
            String string = bundle.getString("code");
            if (TextUtils.equals(string, "BIND")) {
                operationResult.setCode(IotFtfPayCheckBindCode.BIND);
            } else if (TextUtils.equals(string, "NOT_BIND")) {
                operationResult.setCode(IotFtfPayCheckBindCode.UNBIND);
            } else {
                operationResult.setCode(IotFtfPayCheckBindCode.FAILED);
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Exception e) {
            operationResult.setCode(IotFtfPayCheckBindCode.FAILED);
            LoggerFactory.e().a((String) "ftfpay", (String) "IotFtfPayCheckBindEx", (Throwable) e);
        }
        return operationResult;
    }
}
