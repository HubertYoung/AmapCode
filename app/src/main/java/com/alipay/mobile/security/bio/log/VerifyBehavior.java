package com.alipay.mobile.security.bio.log;

import java.util.HashMap;
import java.util.Map;

public class VerifyBehavior {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private Map<String, String> g = new HashMap();
    private int h = 2;
    private String i = "VerifyIdentity";

    public String getUserCaseID() {
        return this.a;
    }

    public void setUserCaseID(String str) {
        this.a = str;
    }

    public String getAppID() {
        return this.b;
    }

    @Deprecated
    public void setAppID(String str) {
        this.b = str;
    }

    public String getSeedID() {
        return this.c;
    }

    public void setSeedID(String str) {
        this.c = str;
    }

    public String getParam1() {
        return this.d;
    }

    public void setParam1(String str) {
        this.d = str;
    }

    public String getParam2() {
        return this.e;
    }

    public void setParam2(String str) {
        this.e = str;
    }

    public String getParam3() {
        return this.f;
    }

    public void setParam3(String str) {
        this.f = str;
    }

    public Map<String, String> getExtParams() {
        return this.g;
    }

    public void addExtParam(String str, String str2) {
        this.g.put(str, str2);
    }

    public void removeExtParam(String str) {
        this.g.remove(str);
    }

    public int getLoggerLevel() {
        return this.h;
    }

    public void setLoggerLevel(int i2) {
        this.h = i2;
    }

    public String getBizType() {
        return this.i;
    }

    public void setBizType(String str) {
        this.i = str;
    }
}
