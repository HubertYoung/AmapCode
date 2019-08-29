package com.alipay.apmobilesecuritysdk.rpc.gen;

import com.alipay.inside.mobile.framework.service.annotation.OperationType;
import com.alipay.inside.mobile.framework.service.annotation.SignCheck;

public interface ReportPBService4AndroidV7 {
    @SignCheck
    @OperationType("alipay.security.device.data.report.pb.android.v7")
    ReportResult reportData(ReportRequest reportRequest);
}
