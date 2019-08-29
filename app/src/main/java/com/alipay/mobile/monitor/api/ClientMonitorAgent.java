package com.alipay.mobile.monitor.api;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.Map;

public class ClientMonitorAgent {
    @Deprecated
    public static final int PERFORMANCE_SCORE_ENDURE = 2013;
    public static boolean isResumeByStartup = true;
    private static Map<String, Object> sStartupPerfData;

    public static void cacheStartupPerfData(Map<String, Object> map) {
        if (map != null) {
            map.put("invokedTime", Long.valueOf(System.currentTimeMillis()));
            sStartupPerfData = map;
            isResumeByStartup = true;
        }
    }

    public static Map<String, Object> getStartupPerfData() {
        return sStartupPerfData;
    }

    @Deprecated
    public static int getDevicePerformanceScore() {
        return LoggerFactory.getLogContext().getDevicePerformanceScore();
    }
}
