package com.alipay.bis.core.protocol;

public class BisMetaInfo {
    private String bEva = "0";
    private String evVer = "EYE";
    private String feVer = "5.2";
    private String fmt = "1";
    private String hwVer = "1.0";

    public String getFmt() {
        return this.fmt;
    }

    public void setFmt(String str) {
        this.fmt = str;
    }

    public String getEvVer() {
        return this.evVer;
    }

    public void setEvVer(String str) {
        this.evVer = str;
    }

    public String getbEva() {
        return this.bEva;
    }

    public void setbEva(String str) {
        this.bEva = str;
    }

    public String getFeVer() {
        return this.feVer;
    }

    public void setFeVer(String str) {
        this.feVer = str;
    }

    public String getHwVer() {
        return this.hwVer;
    }

    public void setHwVer(String str) {
        this.hwVer = str;
    }
}
