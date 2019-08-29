package com.alipay.android.phone.inside.main.action;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.smartsellv2.PayAuthV2ResultCode;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import org.json.JSONObject;

public class SmartSellV2PreloadAction implements ISmartSellV2Action {
    private static final String a = "SmartSellV2PreloadAction";

    public final String a() {
        return ActionEnum.SMART_SELL_PAY_AUTH_V2_PRELOAD.getActionName();
    }

    public final OperationResult<PayAuthV2ResultCode> a(JSONObject jSONObject) {
        LoggerFactory.d().a("onceauth_v2", BehaviorType.EVENT, "main").g = a;
        OperationResult<PayAuthV2ResultCode> operationResult = new OperationResult<>(PayAuthV2ResultCode.SUCCESS, ActionEnum.SMART_SELL_PAY_AUTH_V2_PRELOAD.getActionName());
        try {
            ServiceExecutor.b("ONCE_AUTH_V2_PLUGIN_PRELOAD_SERVICE", jSONObject);
        } catch (Throwable th) {
            operationResult.setCode(PayAuthV2ResultCode.FAILED);
            LoggerFactory.e().a((String) "main", (String) "OnceAuthV2StartEx", th);
        }
        Behavior a2 = LoggerFactory.d().a("onceauth_v2", BehaviorType.EVENT, "exit");
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(MergeUtil.SEPARATOR_KV);
        sb.append(operationResult.getCodeValue());
        a2.g = sb.toString();
        return operationResult;
    }
}
