package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.iotads.IotAdsPreMemberCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class IotAdsPreMemberAction implements SdkAction {
    public final String a() {
        return ActionEnum.IOT_ADS_PREMEMBER.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(IotAdsPreMemberCode.SUCCESS, ActionEnum.IOT_ADS_PREMEMBER.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("IOT_ADS_PLUGIN_PRE_MEMBER", jSONObject);
            if (TextUtils.equals(bundle.getString("code"), GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(IotAdsPreMemberCode.SUCCESS);
            } else {
                operationResult.setCode(IotAdsPreMemberCode.FAIL);
            }
            operationResult.setResult(bundle.getString("value"));
        } catch (Throwable th) {
            operationResult.setCode(IotAdsPreMemberCode.FAIL);
            LoggerFactory.e().a((String) "iotads", (String) "preMemberEx", th);
        }
        return operationResult;
    }
}
