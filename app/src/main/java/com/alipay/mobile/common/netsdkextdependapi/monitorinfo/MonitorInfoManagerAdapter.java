package com.alipay.mobile.common.netsdkextdependapi.monitorinfo;

import com.alipay.mobile.common.netsdkextdependapi.InnerMiscUtil;

public class MonitorInfoManagerAdapter implements MonitorInfoManager {
    public void record(MonitorLoggerModel monitorLoggerModel) {
        if (monitorLoggerModel != null) {
            InnerMiscUtil.logger.info("[MonitorInfoManagerAdapter#record] [record] subType=" + monitorLoggerModel.getSubType());
        }
    }
}
