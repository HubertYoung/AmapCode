package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.smartsell.PayAuthRequestCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import org.json.JSONObject;

public class SmartSellPayAuthAction implements SdkAction {
    public final String a() {
        return ActionEnum.SMART_SELL_PAY_AUTH.getActionName();
    }

    public final OperationResult<PayAuthRequestCode> a(JSONObject jSONObject) {
        OperationResult<PayAuthRequestCode> operationResult = new OperationResult<>(PayAuthRequestCode.SUCCESS, ActionEnum.SMART_SELL_PAY_AUTH.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("ONCE_AUTH_PLUGIN_START_SERVICE", jSONObject);
            String string = bundle.getString("resultCode");
            String string2 = bundle.getString("resultValue");
            if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(PayAuthRequestCode.SUCCESS);
            } else if (TextUtils.equals(string, GenBusCodeService.CODE_TIMEOUT)) {
                operationResult.setCode(PayAuthRequestCode.TIMEOUT);
            } else if (TextUtils.equals(string, RPCDataItems.CANCEL)) {
                operationResult.setCode(PayAuthRequestCode.CANCEL);
            } else {
                operationResult.setCode(PayAuthRequestCode.FAILED);
            }
            operationResult.setResult(string2);
        } catch (Throwable th) {
            operationResult.setCode(PayAuthRequestCode.FAILED);
            LoggerFactory.e().a((String) "main", (String) "OnceAuthStartEx", th);
        }
        return operationResult;
    }
}
