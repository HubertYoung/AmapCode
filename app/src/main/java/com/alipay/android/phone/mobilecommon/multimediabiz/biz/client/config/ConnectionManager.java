package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config;

import android.annotation.SuppressLint;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

@SuppressLint({"DefaultLocale"})
public abstract class ConnectionManager<T> {
    private String a = "aliwallet";
    private int b = 35;
    private String c = "acl";
    private String d = Oauth2AccessToken.KEY_UID;

    public abstract T getConnection();

    public abstract T getConnection(boolean z);

    public int getAppId() {
        return this.b;
    }

    public String getAppKey() {
        return this.a;
    }

    public void setAppKey(String appKey) {
        this.a = appKey;
    }

    public String getAcl() {
        return this.c;
    }

    public void setAcl(String acl) {
        this.c = acl;
    }

    public String getUid() {
        return this.d;
    }

    public void setUid(String uid) {
        this.d = uid;
    }
}
