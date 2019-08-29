package com.alipay.mobile.nebula.log;

public class H5MonitorLogConfig {
    public static final String BEHAVIOR_HEADER = "HD-VM";
    public static final String H5EXCEPTION_TYPE = "H5Exception";
    public static final String MONITOR_HEADER = "H-MM";
    public static final String WEBAPP_TINY_TYPE = "webapp-tiny";
    public static final String WEBAPP_TYPE = "webapp";
    private String logHeader;
    private String logType;

    public static H5MonitorLogConfig newH5MonitorLogConfig() {
        return new H5MonitorLogConfig();
    }

    private H5MonitorLogConfig() {
    }

    public H5MonitorLogConfig setLogType(String logType2) {
        this.logType = logType2;
        return this;
    }

    public H5MonitorLogConfig setLogHeader(String logHeader2) {
        this.logHeader = logHeader2;
        return this;
    }

    public String getLogType() {
        return this.logType;
    }

    public String getLogHeader() {
        return this.logHeader;
    }
}
