package com.alipay.mobile.security.zim.api;

public class ZIMMetaInfo {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;

    public String getZimVer() {
        return this.h;
    }

    public void setZimVer(String str) {
        this.h = str;
    }

    public String getApdidToken() {
        return this.a;
    }

    public void setApdidToken(String str) {
        this.a = str;
    }

    public String getDeviceType() {
        return this.b;
    }

    public void setDeviceType(String str) {
        this.b = str;
    }

    public String getDeviceModel() {
        return this.c;
    }

    public void setDeviceModel(String str) {
        this.c = str;
    }

    public String getAppName() {
        return this.d;
    }

    public void setAppName(String str) {
        this.d = str;
    }

    public String getAppVersion() {
        return this.e;
    }

    public void setAppVersion(String str) {
        this.e = str;
    }

    public String getOsVersion() {
        return this.f;
    }

    public void setOsVersion(String str) {
        this.f = str;
    }

    public String getBioMetaInfo() {
        return this.g;
    }

    public void setBioMetaInfo(String str) {
        this.g = str;
    }

    public String toString() {
        return "ZIMMetaInfo{apdidToken='" + this.a + '\'' + ", deviceType='" + this.b + '\'' + ", deviceModel='" + this.c + '\'' + ", appName='" + this.d + '\'' + ", appVersion='" + this.e + '\'' + ", osVersion='" + this.f + '\'' + ", bioMetaInfo='" + this.g + '\'' + ", zimVer='" + this.h + '\'' + '}';
    }
}
