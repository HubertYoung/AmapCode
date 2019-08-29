package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.ftfpay.IotFtfPayQueryMerchantCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class IotFtfPayQueryMerchantAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_FTF_PAY_QUERY_MERCHANT.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotFtfPayQueryMerchantCode.SUCCESS, ActionEnum.IOT_FTF_PAY_QUERY_MERCHANT.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_FTF_PAY_PLUGIN_QUERY_MERCHANT", jSONObject);
            String string = bundle.getString("code");
            if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(IotFtfPayQueryMerchantCode.SUCCESS);
            } else if (TextUtils.equals(string, "PARAMS_ILLEGAL")) {
                operationResult.setCode(IotFtfPayQueryMerchantCode.PARAMS_ILLEGAL);
            } else {
                operationResult.setCode(IotFtfPayQueryMerchantCode.FAILED);
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Exception e) {
            operationResult.setCode(IotFtfPayQueryMerchantCode.FAILED);
            LoggerFactory.e().a((String) "ftfpay", (String) "IotFtfPayQueryMerchantEx", (Throwable) e);
        }
        return operationResult;
    }
}
