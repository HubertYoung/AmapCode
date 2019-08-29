package com.alipay.mobile.common.transport.httpdns.downloader;

import java.util.List;

public class StrategyRequest extends StrategyRequestItem {
    private List<String> a;
    private String b;
    private String c;
    private String d;

    public StrategyRequest() {
    }

    public StrategyRequest(List<String> domains) {
        this.a = domains;
    }

    public StrategyRequest(String uid, List<String> domains) {
        super.setUid(uid);
        this.a = domains;
    }

    public List<String> getDomains() {
        return this.a;
    }

    public void setDomains(List<String> domains) {
        this.a = domains;
    }

    public String getS() {
        return this.b;
    }

    public void setS(int s) {
        this.b = String.valueOf(s);
    }

    public String getLat_lng() {
        return this.c;
    }

    public void setLat_lng(String lat_lng) {
        this.c = lat_lng;
    }

    public String getDg() {
        return this.d;
    }

    public void setDg(String dg) {
        this.d = dg;
    }
}
