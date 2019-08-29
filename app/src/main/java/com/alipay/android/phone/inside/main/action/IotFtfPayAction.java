package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.ali.auth.third.login.UTConstants;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.ftfpay.IotFtfPayCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.main.action.utils.IotPayConstants;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class IotFtfPayAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_FTF_PAY.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotFtfPayCode.SUCCESS, ActionEnum.IOT_FTF_PAY.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_FTF_PAY_PLUGIN_PAYMENT", jSONObject);
            String string = bundle.getString("code");
            if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(IotFtfPayCode.SUCCESS);
            } else if (TextUtils.equals(string, "FAIL")) {
                operationResult.setCode(IotFtfPayCode.FAIL);
            } else if (TextUtils.equals(string, "UNKNOWN")) {
                operationResult.setCode(IotFtfPayCode.UNKNOWN);
            } else if (TextUtils.equals(string, "PARAMS_ILLEGAL")) {
                operationResult.setCode(IotFtfPayCode.PARAMS_ILLEGAL);
            } else {
                if (!TextUtils.equals(string, UTConstants.E_COOOPERATION_UNBIND)) {
                    if (!TextUtils.equals(string, "NOT_BIND")) {
                        IotPayConstants.a(operationResult, IotFtfPayCode.UNKNOWN, "支付结果未知");
                    }
                }
                operationResult.setCode(IotFtfPayCode.BIND_ERROR);
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Exception e) {
            IotPayConstants.a(operationResult, IotFtfPayCode.UNKNOWN, "支付结果未知");
            LoggerFactory.e().a((String) "iotftfpay", (String) "IotFtfPayEx", (Throwable) e);
        }
        return operationResult;
    }
}
