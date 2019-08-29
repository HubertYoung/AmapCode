package com.alipay.mobile.common.netsdkextdependapi.monitorinfo;

public class MonitorInfoUtil {
    public static final void record(MonitorLoggerModel monitorLoggerModel) {
        ((MonitorInfoManager) MonitorInfoManagerFactory.getInstance().getDefaultBean()).record(monitorLoggerModel);
    }
}
