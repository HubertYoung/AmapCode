package com.alipay.mobile.common.transport.httpdns.downloader;

import com.alipay.mobile.common.transport.httpdns.HttpDns.HttpdnsIP;
import java.util.Map;

public class StrategyResponse {
    private long a;
    private Map<String, HttpdnsIP> b;
    private String c;
    private String d;
    private boolean e;
    private int f;

    public long getCode() {
        return this.a;
    }

    public Map<String, HttpdnsIP> getDns() {
        return this.b;
    }

    public String getConf() {
        return this.c;
    }

    public String getClientIp() {
        return this.d;
    }

    public boolean isOversea() {
        return this.e;
    }

    public int getTtd() {
        return this.f;
    }

    public StrategyResponse(long code, Map<String, HttpdnsIP> dns, String conf, String clientIp, boolean overSea, int ttd) {
        this.a = code;
        this.b = dns;
        this.c = conf;
        this.d = clientIp;
        this.e = overSea;
        this.f = ttd;
    }
}
