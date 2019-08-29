package com.alipay.bis.common.service.facade.gw.model.common.BisJson;

public class BisBehavToken {
    public String apdid;
    public String apdidToken = "";
    public String appid;
    @Deprecated
    public String behid;
    public String bizid;
    @Deprecated
    public int sampleMode = 0;
    public String token;
    @Deprecated
    public int type = 0;
    public String uid;
    public String verifyid = "";
    @Deprecated
    public String vtoken = "";

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public int getSampleMode() {
        return this.sampleMode;
    }

    public void setSampleMode(int i) {
        this.sampleMode = i;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public String getApdid() {
        return this.apdid;
    }

    public void setApdid(String str) {
        this.apdid = str;
    }

    public String getAppid() {
        return this.appid;
    }

    public void setAppid(String str) {
        this.appid = str;
    }

    public String getBehid() {
        return this.behid;
    }

    public void setBehid(String str) {
        this.behid = str;
    }

    public String getBizid() {
        return this.bizid;
    }

    public void setBizid(String str) {
        this.bizid = str;
    }

    public String getVerifyid() {
        return this.verifyid;
    }

    public void setVerifyid(String str) {
        this.verifyid = str;
    }

    public String getVtoken() {
        return this.vtoken;
    }

    public void setVtoken(String str) {
        this.vtoken = str;
    }

    public String getApdidToken() {
        return this.apdidToken;
    }

    public void setApdidToken(String str) {
        this.apdidToken = str;
    }
}
