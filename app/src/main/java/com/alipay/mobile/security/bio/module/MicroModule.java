package com.alipay.mobile.security.bio.module;

public class MicroModule {
    private String a;

    public MicroModule() {
    }

    public MicroModule(String str) {
        this.a = str;
    }

    public String getZimId() {
        return this.a;
    }

    public void setZimId(String str) {
        this.a = str;
    }
}
