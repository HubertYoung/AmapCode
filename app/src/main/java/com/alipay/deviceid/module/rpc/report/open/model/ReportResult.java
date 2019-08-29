package com.alipay.deviceid.module.rpc.report.open.model;

import java.io.Serializable;
import java.util.Map;

public class ReportResult implements Serializable {
    public String resultCode;
    public Map<String, String> resultData;
    public boolean success;
}
