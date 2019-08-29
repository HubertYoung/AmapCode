package com.ali.user.mobile.account.domain;

public class MspDeviceInfoBean {
    String imei;
    String imsi;
    String mspkey;
    String tid;
    private String vimei;
    private String vimsi;

    public String getTid() {
        return this.tid;
    }

    public void setTid(String str) {
        this.tid = str;
    }

    public String getMspkey() {
        return this.mspkey;
    }

    public void setMspkey(String str) {
        this.mspkey = str;
    }

    public String getImei() {
        return this.imei;
    }

    public void setImei(String str) {
        this.imei = str;
    }

    public String getImsi() {
        return this.imsi;
    }

    public void setImsi(String str) {
        this.imsi = str;
    }

    public void setVimsi(String str) {
        this.vimsi = str;
    }

    public String getVimsi() {
        return this.vimsi;
    }

    public void setVimei(String str) {
        this.vimei = str;
    }

    public String getVimei() {
        return this.vimei;
    }
}
