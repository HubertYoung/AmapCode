package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.iotcashier.IotCashierBindCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class IotCashierBindAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_CASHIER_BIND.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotCashierBindCode.SUCCESS, ActionEnum.IOT_CASHIER_BIND.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_CASHIER_PLUGIN_BIND", jSONObject);
            if (TextUtils.equals(bundle.getString("code"), GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(IotCashierBindCode.SUCCESS);
            } else {
                operationResult.setCode(IotCashierBindCode.FAIL);
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Throwable th) {
            operationResult.setCode(IotCashierBindCode.FAIL);
            LoggerFactory.e().a((String) "iotcashier", (String) "bindError", th);
        }
        return operationResult;
    }
}
