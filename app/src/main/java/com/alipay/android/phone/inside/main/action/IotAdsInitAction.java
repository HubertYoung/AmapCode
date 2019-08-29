package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.iotads.IotAdsInitCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class IotAdsInitAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_ADS_INIT.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotAdsInitCode.SUCCESS, ActionEnum.IOT_ADS_INIT.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_ADS_PLUGIN_INIT", jSONObject);
            if (TextUtils.equals(bundle.getString("code"), GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(IotAdsInitCode.SUCCESS);
            } else {
                operationResult.setCode(IotAdsInitCode.FAIL);
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Throwable th) {
            operationResult.setCode(IotAdsInitCode.FAIL);
            LoggerFactory.e().a((String) "iotads", (String) "initEx", th);
        }
        return operationResult;
    }
}
