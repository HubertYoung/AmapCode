package com.alipay.mobile.monitor.track.spm.sem;

import java.util.Map;

public class SemInfo {
    private String a;
    private String b;
    private String c;
    private String d;
    private Map<String, String> e;

    public String getSemId() {
        return this.a;
    }

    public void setSemId(String semId) {
        this.a = semId;
    }

    public String getRpcId() {
        return this.b;
    }

    public void setRpcId(String rpcId) {
        this.b = rpcId;
    }

    public void setBizCode(String bizCode) {
        this.c = bizCode;
    }

    public String getBizCode() {
        return this.c;
    }

    public String getPos() {
        return this.d;
    }

    public void setPos(String pos) {
        this.d = pos;
    }

    public Map<String, String> getEntityInfo() {
        return this.e;
    }

    public void setEntityInfo(Map<String, String> entityInfo) {
        this.e = entityInfo;
    }
}
