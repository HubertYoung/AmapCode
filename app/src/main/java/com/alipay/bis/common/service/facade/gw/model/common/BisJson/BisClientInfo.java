package com.alipay.bis.common.service.facade.gw.model.common.BisJson;

public class BisClientInfo {
    public String clientVer;
    public String model;
    public String os;
    public String osVer;

    public String getModel() {
        return this.model;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public String getOs() {
        return this.os;
    }

    public void setOs(String str) {
        this.os = str;
    }

    public String getOsVer() {
        return this.osVer;
    }

    public void setOsVer(String str) {
        this.osVer = str;
    }

    public String getClientVer() {
        return this.clientVer;
    }

    public void setClientVer(String str) {
        this.clientVer = str;
    }
}
