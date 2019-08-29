package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.ali.auth.third.login.UTConstants;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.iotpay.IotPayGenTransferCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class IotPayGenTransferCodeAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_PAY_GEN_TRANSFER_CODE.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotPayGenTransferCode.SUCCESS, ActionEnum.IOT_PAY_GEN_TRANSFER_CODE.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_PAY_PLUGIN_GEN_TRANSFER_CODE", jSONObject);
            String string = bundle.getString("code");
            if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(IotPayGenTransferCode.SUCCESS);
            } else if (TextUtils.equals(string, UTConstants.E_COOOPERATION_UNBIND)) {
                operationResult.setCode(IotPayGenTransferCode.UNBIND);
            } else {
                operationResult.setCode(IotPayGenTransferCode.FAILED);
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Exception e) {
            operationResult.setCode(IotPayGenTransferCode.FAILED);
            LoggerFactory.e().a((String) "iotpay", (String) "genTransferCodeEx", (Throwable) e);
        }
        return operationResult;
    }
}
