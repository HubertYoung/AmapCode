package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.ali.auth.third.login.UTConstants;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.iotpay.IotPayTradeCloseCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.main.action.utils.IotPayConstants;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class IotPayTradeCloseAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_PAY_TRADE_CLOSE.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotPayTradeCloseCode.SUCCESS, ActionEnum.IOT_PAY_TRADE_CLOSE.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_PAY_PLUGIN_TRADE_CLOSE", jSONObject);
            String string = bundle.getString("code");
            if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(IotPayTradeCloseCode.SUCCESS);
            } else if (TextUtils.equals(string, "FAIL")) {
                operationResult.setCode(IotPayTradeCloseCode.FAIL);
            } else if (TextUtils.equals(string, "UNKNOWN")) {
                operationResult.setCode(IotPayTradeCloseCode.UNKNOWN);
            } else if (TextUtils.equals(string, "PARAMS_ILLEGAL")) {
                operationResult.setCode(IotPayTradeCloseCode.PARAMS_ILLEGAL);
            } else if (TextUtils.equals(string, UTConstants.E_COOOPERATION_UNBIND)) {
                operationResult.setCode(IotPayTradeCloseCode.BIND_ERROR);
            } else {
                IotPayConstants.a(operationResult, IotPayTradeCloseCode.UNKNOWN, "网络异常，关单结果未知，请重试");
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Exception e) {
            IotPayConstants.a(operationResult, IotPayTradeCloseCode.UNKNOWN, "网络异常，关单结果未知，请重试");
            LoggerFactory.e().a((String) "iotpay", (String) "tradeCloseEx", (Throwable) e);
        }
        return operationResult;
    }
}
