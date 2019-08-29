package com.alipay.android.phone.inside.api.model.report;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.report.ReportDeviceEnvCode;

public class ReportDeviceEnvModel extends BaseModel<ReportDeviceEnvCode> {
    public IBizOperation<ReportDeviceEnvCode> getOperaion() {
        return new IBizOperation<ReportDeviceEnvCode>() {
            public ReportDeviceEnvCode parseResultCode(String str, String str2) {
                return ReportDeviceEnvCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.REPORT_DEVICE_ENV;
            }
        };
    }
}
