package com.alipay.mobile.common.netsdkextdependapi.monitorinfo;

import java.util.HashMap;
import java.util.Map;

public class MonitorLoggerModel {
    public static final int LOG_LEVEL_HIGH = 1;
    public static final int LOG_LEVEL_LOW = 3;
    public static final int LOG_LEVEL_MEDIUM = 2;
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private int f = 2;
    private Map<String, String> g = new HashMap();

    public String getSubType() {
        return this.a;
    }

    public void setSubType(String subType) {
        this.a = subType;
    }

    public String getParam1() {
        return this.c;
    }

    public void setParam1(String param1) {
        this.c = param1;
    }

    public String getParam2() {
        return this.d;
    }

    public void setParam2(String param2) {
        this.d = param2;
    }

    public String getParam3() {
        return this.e;
    }

    public void setParam3(String param3) {
        this.e = param3;
    }

    public Map<String, String> getExtPramas() {
        return getExtParams();
    }

    public Map<String, String> getExtParams() {
        return this.g;
    }

    public void setExtParams(Map<String, String> extParams) {
        this.g = extParams;
    }

    public void addExtParam(String key, String value) {
        this.g.put(key, value);
    }

    public void removeExtParam(String key) {
        this.g.remove(key);
    }

    public String getBizType() {
        return this.b;
    }

    public void setBizType(String bizType) {
        this.b = bizType;
    }

    public int getLoggerLevel() {
        return this.f;
    }

    public void setLoggerLevel(int loggerLevel) {
        this.f = loggerLevel;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getSubType()).append(",");
        sb.append(getParam1()).append(",");
        sb.append(getParam2()).append(",");
        sb.append(getParam3()).append(",");
        for (String key : getExtPramas().keySet()) {
            sb.append(key + "=" + getExtPramas().get(key) + "^");
        }
        return sb.toString();
    }
}
