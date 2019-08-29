package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.iotads.IotAdsLoadingCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class IotAdsLoadingPageAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_ADS_LOADING.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotAdsLoadingCode.SUCCESS, ActionEnum.IOT_ADS_LOADING.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_ADS_PLUGIN_PAY_LOADING", jSONObject);
            if (TextUtils.equals(bundle.getString("code"), GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(IotAdsLoadingCode.SUCCESS);
            } else {
                operationResult.setCode(IotAdsLoadingCode.FAIL);
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Throwable th) {
            operationResult.setCode(IotAdsLoadingCode.FAIL);
            LoggerFactory.e().a((String) "iotads", (String) "loadingEx", th);
        }
        return operationResult;
    }
}
