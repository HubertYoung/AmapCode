package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.code.SwitchChannelCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class SwitchChannelAction implements SdkAction {
    public final String a() {
        return ActionEnum.SWITCH_CHANNEL.getActionName();
    }

    public final OperationResult<SwitchChannelCode> a(JSONObject jSONObject) {
        OperationResult<SwitchChannelCode> operationResult = new OperationResult<>(SwitchChannelCode.FAILED, ActionEnum.SWITCH_CHANNEL.getActionName());
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("BARCODE_PLUGIN_SWITCH_CHANNEL", jSONObject);
            if (TextUtils.equals(bundle.getString("resultCode"), GenBusCodeService.CODE_SUCESS)) {
                operationResult.setResult(bundle.getString("resultValue"));
                operationResult.setCode(SwitchChannelCode.SUCCESS);
            } else {
                operationResult.setCode(SwitchChannelCode.FAILED);
            }
        } catch (Exception e) {
            operationResult.setCode(SwitchChannelCode.FAILED);
            LoggerFactory.f().b((String) "inside", (Throwable) e);
        }
        return operationResult;
    }
}
