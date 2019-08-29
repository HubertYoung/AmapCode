package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.aliautologin.PreLoadConfigCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.config.plugin.ConfigPlugin;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class PreLoadConfigAction implements SdkAction {
    private final String a = "PreLoadConfigAction";

    public final String a() {
        return ActionEnum.PRE_LOAD_CONFIG_ACTION.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        LoggerFactory.f().e("PreLoadConfigAction", "PreLoadConfigAction::startAction");
        OperationResult operationResult = new OperationResult(PreLoadConfigCode.FAILED, ActionEnum.PRE_LOAD_CONFIG_ACTION.getActionName());
        try {
            Bundle bundle = new Bundle();
            bundle.putString("configName", "JUST_JUST_PRELOAD_CONFIG");
            ServiceExecutor.b(ConfigPlugin.SERVICE_DYNAMI_CCONFIG_LOAD, bundle);
            operationResult.setCode(PreLoadConfigCode.SUCCESS);
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "inside", (String) "PreLoadConfigActionAction", th);
            LoggerFactory.f().a((String) "PreLoadConfigAction", th);
        }
        return operationResult;
    }
}
