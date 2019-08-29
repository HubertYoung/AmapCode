package com.alipay.bis.common.service.facade.gw.model.common.BisJson;

public class BisClientConfigContent {
    private String androidcfg;
    private String ioscfg;
    private int sampleMode;
    private String token;
    private int type;

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

    public String getAndroidcfg() {
        return this.androidcfg;
    }

    public void setAndroidcfg(String str) {
        this.androidcfg = str;
    }

    public String getIoscfg() {
        return this.ioscfg;
    }

    public void setIoscfg(String str) {
        this.ioscfg = str;
    }
}
