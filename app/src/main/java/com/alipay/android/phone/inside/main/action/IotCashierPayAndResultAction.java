package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.iotcashier.IotCashierPayAndResultCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class IotCashierPayAndResultAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_CASHIER_PAY_AND_RESULT.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotCashierPayAndResultCode.SUCCESS, ActionEnum.IOT_CASHIER_PAY_AND_RESULT.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_CASHIER_PLUGIN_PAY_AND_RESULT", jSONObject);
            if (TextUtils.equals(bundle.getString("code"), GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(IotCashierPayAndResultCode.SUCCESS);
            } else {
                operationResult.setCode(IotCashierPayAndResultCode.FAIL);
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Throwable th) {
            operationResult.setCode(IotCashierPayAndResultCode.FAIL);
            LoggerFactory.e().a((String) "iotcashier", (String) "initError", th);
        }
        return operationResult;
    }
}
