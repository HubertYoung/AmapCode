package com.alipay.deviceid.module.rpc.report.open.model;

import java.io.Serializable;
import java.util.Map;

public class ReportRequest implements Serializable {
    public String appName;
    public String appVersion;
    public Map<String, String> bizData;
    public String bizType;
    public String deviceData;
    public String os;
    public String rpcVersion;
    public String sdkName;
    public String sdkVersion;
}
