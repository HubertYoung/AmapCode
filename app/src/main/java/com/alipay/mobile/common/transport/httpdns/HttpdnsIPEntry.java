package com.alipay.mobile.common.transport.httpdns;

public class HttpdnsIPEntry {
    public String group = "";
    public String ip;
    public int port;

    public HttpdnsIPEntry() {
    }

    public HttpdnsIPEntry(String ip2, int port2) {
        this.ip = ip2;
        this.port = port2;
    }

    public HttpdnsIPEntry(String gr, String ip2, int port2) {
        this.group = gr;
        this.ip = ip2;
        this.port = port2;
    }

    public void setIp(String ip2) {
        this.ip = ip2;
    }

    public void setPort(int port2) {
        this.port = port2;
    }

    public String getIpWithPort() {
        return this.ip + ":" + this.port;
    }

    public void setGroup(String group2) {
        this.group = group2;
    }

    public String toString() {
        return "HttpdnsIPEntry{ip='" + this.ip + '\'' + ", port=" + this.port + ", group='" + this.group + '\'' + '}';
    }
}
