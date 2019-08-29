package com.alipay.deviceid.module.rpc.report.open;

import com.alipay.deviceid.module.rpc.mrpc.annotation.OperationType;
import com.alipay.deviceid.module.rpc.report.open.model.ReportRequest;
import com.alipay.deviceid.module.rpc.report.open.model.ReportResult;

public interface OpenReportService {
    @OperationType("alipay.security.device.data.report.open")
    ReportResult reportData(ReportRequest reportRequest);
}
