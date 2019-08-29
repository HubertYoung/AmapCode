package com.sina.weibo.sdk.network.base;

public class WbUserInfo {
    private String gsid;
    private String token;
    private String uid;

    public WbUserInfo(String str, String str2, String str3) {
        this.gsid = str;
        this.uid = str2;
        this.token = str3;
    }

    public String getGsid() {
        return this.gsid;
    }

    public String getUid() {
        return this.uid;
    }

    public String getToken() {
        return this.token;
    }
}
