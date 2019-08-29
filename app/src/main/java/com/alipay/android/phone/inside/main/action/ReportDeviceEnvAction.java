package com.alipay.android.phone.inside.main.action;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.report.ReportDeviceEnvCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import org.json.JSONObject;

public class ReportDeviceEnvAction implements SdkAction {
    private final String a = "inside-ReportDeviceEnvAction";

    public final String a() {
        return ActionEnum.REPORT_DEVICE_ENV.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(ReportDeviceEnvCode.SUCCESS, ActionEnum.REPORT_DEVICE_ENV.getActionName());
        ServiceExecutor.a((String) "REPORT_DEVICE_LOCATION_SERVICE", "");
        operationResult.setCode(ReportDeviceEnvCode.SUCCESS);
        return operationResult;
    }
}
