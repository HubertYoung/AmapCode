package com.alipay.android.phone.inside.main.action;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.smartsell.PayAuthPreloadCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class SmartSellPayAuthPreloadAction implements SdkAction {
    public final String a() {
        return ActionEnum.SMART_SELL_PAY_AUTH_PRELOAD.getActionName();
    }

    public final OperationResult<PayAuthPreloadCode> a(JSONObject jSONObject) {
        OperationResult<PayAuthPreloadCode> operationResult = new OperationResult<>(PayAuthPreloadCode.SUCCESS, ActionEnum.SMART_SELL_PAY_AUTH_PRELOAD.getActionName());
        try {
            ServiceExecutor.b("ONCE_AUTH_PLUGIN_PRELOAD_SERVICE", jSONObject);
            operationResult.setCode(PayAuthPreloadCode.SUCCESS);
        } catch (Throwable th) {
            operationResult.setCode(PayAuthPreloadCode.FAILED);
            LoggerFactory.e().a((String) "main", (String) "OnceAuthStartEx", th);
        }
        return operationResult;
    }
}
