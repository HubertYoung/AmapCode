package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.iotadx.IotAdxResultCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.sdk.util.h;
import org.json.JSONObject;

public class IotAdxInitAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_ADX_INIT.getActionName();
    }

    public final OperationResult<IotAdxResultCode> a(JSONObject jSONObject) {
        OperationResult<IotAdxResultCode> operationResult = new OperationResult<>(IotAdxResultCode.SUCCESS, ActionEnum.IOT_ADX_INIT.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_ADS_PLUGIN_ADX_INIT", jSONObject);
            boolean z = bundle.getBoolean("success", false);
            String string = bundle.getString("result", bny.c);
            String string2 = bundle.getString("subcode", bny.c);
            if (z) {
                operationResult.setCode(IotAdxResultCode.SUCCESS);
            } else {
                operationResult.setCode(IotAdxResultCode.FAILED);
            }
            StringBuilder sb = new StringBuilder("{\"result\":");
            sb.append(string);
            sb.append(",\"subcode\":");
            sb.append(string2);
            sb.append(h.d);
            operationResult.setResult(sb.toString());
        } catch (Throwable th) {
            operationResult.setCode(IotAdxResultCode.FAILED);
            LoggerFactory.e().a((String) "main", (String) "IotAdxInitActionEx", th);
        }
        return operationResult;
    }
}
