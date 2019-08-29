package com.taobao.applink;

public class TBAppLinkParam {
    public String mAppSecret;
    public String mAppkey;
    public String mBackUrl;
    public String mPid;
    public String mSource;
    public String mTag;
    public String mTtid;
    public String mUtdid;

    public TBAppLinkParam(String str, String str2, String str3, String str4) {
        this.mAppkey = str;
        this.mAppSecret = str2;
        this.mBackUrl = str3;
        this.mPid = str4;
        setDefaultTTID(str);
    }

    public TBAppLinkParam(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.mAppkey = str;
        this.mBackUrl = str2;
        this.mPid = str3;
        this.mSource = str4;
        this.mTtid = str5;
        this.mTag = str6;
        this.mUtdid = str7;
        if (this.mTtid == null) {
            setDefaultTTID(str);
        }
    }

    private void setDefaultTTID(String str) {
        if (str != null) {
            this.mTtid = String.format("2014_0_%s@baichuan_android_%s_linkout", new Object[]{str, "2.0.0"});
        }
    }

    public TBAppLinkParam setAppSecret(String str) {
        this.mAppSecret = str;
        return this;
    }

    public TBAppLinkParam setAppkey(String str) {
        this.mAppkey = str;
        return this;
    }

    public TBAppLinkParam setBackUrl(String str) {
        this.mBackUrl = str;
        return this;
    }

    public TBAppLinkParam setPid(String str) {
        this.mPid = str;
        return this;
    }

    public TBAppLinkParam setSource(String str) {
        this.mSource = str;
        return this;
    }

    public TBAppLinkParam setTTID(String str) {
        this.mTtid = str;
        return this;
    }

    public TBAppLinkParam setTag(String str) {
        this.mTag = str;
        return this;
    }

    public TBAppLinkParam setUtdid(String str) {
        this.mUtdid = str;
        return this;
    }
}
