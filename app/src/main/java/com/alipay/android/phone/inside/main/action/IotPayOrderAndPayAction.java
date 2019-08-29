package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.ali.auth.third.login.UTConstants;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.iotpay.IotPayOrderAndPayCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.main.action.utils.IotPayConstants;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class IotPayOrderAndPayAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_PAY_ORDER_AND_PAY.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotPayOrderAndPayCode.SUCCESS, ActionEnum.IOT_PAY_ORDER_AND_PAY.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_PAY_PLUGIN_ORDER_AND_PAY", jSONObject);
            String string = bundle.getString("code");
            if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(IotPayOrderAndPayCode.SUCCESS);
            } else if (TextUtils.equals(string, "FAIL")) {
                operationResult.setCode(IotPayOrderAndPayCode.FAIL);
            } else if (TextUtils.equals(string, "UNKNOWN")) {
                operationResult.setCode(IotPayOrderAndPayCode.UNKNOWN);
            } else if (TextUtils.equals(string, "PARAMS_ILLEGAL")) {
                operationResult.setCode(IotPayOrderAndPayCode.PARAMS_ILLEGAL);
            } else if (TextUtils.equals(string, UTConstants.E_COOOPERATION_UNBIND)) {
                operationResult.setCode(IotPayOrderAndPayCode.BIND_ERROR);
            } else {
                IotPayConstants.a(operationResult, IotPayOrderAndPayCode.UNKNOWN, "网络异常，退款结果未知，请重试");
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Exception e) {
            IotPayConstants.a(operationResult, IotPayOrderAndPayCode.UNKNOWN, "网络异常，退款结果未知，请重试");
            LoggerFactory.e().a((String) "iotpay", (String) "orderPayEx", (Throwable) e);
        }
        return operationResult;
    }
}
