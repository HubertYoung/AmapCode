package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module;

import com.alibaba.fastjson.annotation.JSONField;

public class Token {
    private long createTime;
    private long expireTime;
    @JSONField(name = "currentTime")
    private long serverTime;
    private String token;

    public String getToken() {
        return this.token;
    }

    public void setToken(String token2) {
        this.token = token2;
    }

    public long getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(long expireTime2) {
        this.expireTime = expireTime2;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime2) {
        this.createTime = createTime2;
    }

    public long getServerTime() {
        return this.serverTime;
    }

    public void setServerTime(long serverTime2) {
        this.serverTime = serverTime2;
    }

    public String toString() {
        return "Token{token='" + this.token + '\'' + ", expireTime=" + this.expireTime + ", createTime=" + this.createTime + ", serverTime=" + this.serverTime + '}';
    }
}
