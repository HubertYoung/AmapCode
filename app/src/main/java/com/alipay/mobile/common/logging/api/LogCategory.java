package com.alipay.mobile.common.logging.api;

import com.alipay.mobile.common.logging.api.monitor.PerformanceID;

public class LogCategory {
    public static final String CATEGORY_ALIVEREPORT = "alivereport";
    public static final String CATEGORY_APM = "apm";
    public static final String CATEGORY_APPLOG = "applog";
    public static final String CATEGORY_AUTOUSERBEHAVOR = "autouserbehavor";
    public static final String CATEGORY_BATTERY = "battery";
    public static final String CATEGORY_CRASH = "crash";
    public static final String CATEGORY_DATAFLOW = "dataflow";
    public static final String CATEGORY_EXCEPTION = "exception";
    public static final String CATEGORY_FOOTPRINT = PerformanceID.MONITORPOINT_FOOTPRINT.getDes();
    public static final String CATEGORY_H5EXCEPTION = PerformanceID.MONITORPOINT_H5EXCEPTION.getDes();
    public static final String CATEGORY_HIGHAVAIL = "bizHighAvail";
    public static final String CATEGORY_KEYBIZTRACE = PerformanceID.MONITORPOINT_KEYBIZTRACE.getDes();
    public static final String CATEGORY_LOGCAT = "logcat";
    public static final String CATEGORY_LOGMONITOR = "LogMonitor";
    public static final String CATEGORY_NETWORK = PerformanceID.MONITORPOINT_NETWORK.getDes();
    public static final String CATEGORY_PERFORMANCE = PerformanceID.MONITORPOINT_PERFORMANCE.getDes();
    public static final String CATEGORY_ROMESYNC = "romesync";
    public static final String CATEGORY_SDKMONITOR = PerformanceID.MONITORPOINT_SDKMONITOR.getDes();
    public static final String CATEGORY_TRAFFICLOG = "trafficLog";
    public static final String CATEGORY_USERBEHAVOR = "userbehavor";
    public static final String CATEGORY_WEBAPP = PerformanceID.MONITORPOINT_WEBAPP.getDes();
}
