package com.taobao.statistic.core;

public class Device {
    private String imei = "";
    private String imsi = "";
    private String udid = "";

    public void setUdid(String str) {
        this.udid = str;
    }

    public void setImei(String str) {
        this.imei = str;
    }

    public void setImsi(String str) {
        this.imsi = str;
    }

    public String getUdid() {
        return this.udid;
    }

    public String getImei() {
        return this.imei;
    }

    public String getImsi() {
        return this.imsi;
    }
}
