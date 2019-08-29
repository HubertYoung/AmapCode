package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.ftfpay.IotFtfPayGenSerialNoCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class IotFtfPayGenSerialNoAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_FTF_PAY_GEN_SERIAL_NO.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotFtfPayGenSerialNoCode.SUCCESS, ActionEnum.IOT_FTF_PAY_GEN_SERIAL_NO.getActionName());
        try {
            operationResult.setCode(IotFtfPayGenSerialNoCode.SUCCESS);
            operationResult.setResult(((Bundle) ServiceExecutor.b("IOT_FTF_PAY_PLUGIN_GEN_SERIAL_NO", jSONObject)).getString("value"));
        } catch (Exception e) {
            LoggerFactory.e().a((String) "ftfpay", (String) "IotFtfPayGenSeriralNoEx", (Throwable) e);
        }
        return operationResult;
    }
}
