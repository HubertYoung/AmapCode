package com.alipay.sdk.auth;

public class APAuthInfo {
    private String a;
    private String b;
    private String c;
    private String d;

    public APAuthInfo(String str, String str2, String str3) {
        this(str, str2, str3, null);
    }

    public APAuthInfo(String str, String str2, String str3, String str4) {
        this.a = str;
        this.b = str2;
        this.d = str3;
        this.c = str4;
    }

    public String getAppId() {
        return this.a;
    }

    public String getProductId() {
        return this.b;
    }

    public String getPid() {
        return this.c;
    }

    public String getRedirectUri() {
        return this.d;
    }
}
