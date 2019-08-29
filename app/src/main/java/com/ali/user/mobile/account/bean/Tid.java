package com.ali.user.mobile.account.bean;

import com.ali.user.mobile.account.model.ToString;

public class Tid extends ToString {
    public String clientKey;
    public String imei;
    public String imsi;
    public String tid;
    public String vimei;
    public String vimsi;

    public void setImei(String str) {
        this.imei = str;
    }

    public String getImei() {
        return this.imei;
    }

    public void setImsi(String str) {
        this.imsi = str;
    }

    public String getImsi() {
        return this.imsi;
    }

    public void setClientKey(String str) {
        this.clientKey = str;
    }

    public String getClientKey() {
        return this.clientKey;
    }

    public void setTid(String str) {
        this.tid = str;
    }

    public String getTid() {
        return this.tid;
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
