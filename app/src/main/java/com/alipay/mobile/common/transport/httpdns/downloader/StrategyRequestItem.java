package com.alipay.mobile.common.transport.httpdns.downloader;

import com.alipay.android.phone.a.a.a;

public class StrategyRequestItem {
    private String a;
    private String b;
    private String c = a.a;
    private String d;
    private int e = 2;
    private String f;
    private String g;
    private int h = 1;
    private int i;

    public String getUtdid() {
        return this.b;
    }

    public void setUtdid(String utdid) {
        this.b = utdid;
    }

    public String getOsType() {
        return this.c;
    }

    public String getClientVersion() {
        return this.d;
    }

    public void setClientVersion(String clientVersion) {
        this.d = clientVersion;
    }

    public String getUid() {
        return this.a;
    }

    public void setUid(String uid) {
        this.a = uid;
    }

    public int getVer() {
        return this.e;
    }

    public void setVer(int ver) {
        this.e = ver;
    }

    public String getWsid() {
        return this.f;
    }

    public void setWsid(String wsid) {
        this.f = wsid;
    }

    public String getConfigVersion() {
        return this.g;
    }

    public void setConfigVersion(String configVersion) {
        this.g = configVersion;
    }

    public int getSdkVersion() {
        return this.h;
    }

    public void setSdkVersion(int sdkVersion) {
        this.h = sdkVersion;
    }

    public int getNetType() {
        return this.i;
    }

    public void setNetType(int netType) {
        this.i = netType;
    }
}
