package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.iotcashier.IotCashierResultErrorCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class IotCashierResultErrorAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_CASHIER_RESULT_ERROR.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotCashierResultErrorCode.SUCCESS, ActionEnum.IOT_CASHIER_RESULT_ERROR.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_CASHIER_PLUGIN_RESULT_ERROR", jSONObject);
            if (TextUtils.equals(bundle.getString("code"), GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(IotCashierResultErrorCode.SUCCESS);
            } else {
                operationResult.setCode(IotCashierResultErrorCode.FAIL);
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Throwable th) {
            operationResult.setCode(IotCashierResultErrorCode.FAIL);
            LoggerFactory.e().a((String) "iotcashier", (String) "resultError_Error", th);
        }
        return operationResult;
    }
}
