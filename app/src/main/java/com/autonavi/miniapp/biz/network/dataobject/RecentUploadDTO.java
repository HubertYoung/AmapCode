package com.autonavi.miniapp.biz.network.dataobject;

import java.io.Serializable;

public class RecentUploadDTO implements Serializable {
    private static final long serialVersionUID = 268828027806231945L;
    private String appid;
    private String chinfo;
    private Long lastClickTime;
    private String uid;

    public String getUid() {
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public String getAppid() {
        return this.appid;
    }

    public void setAppid(String str) {
        this.appid = str;
    }

    public String getChinfo() {
        return this.chinfo;
    }

    public void setChinfo(String str) {
        this.chinfo = str;
    }

    public Long getLastClickTime() {
        return this.lastClickTime;
    }

    public void setLastClickTime(Long l) {
        this.lastClickTime = l;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("RecentUploadDTO{uid='");
        sb.append(this.uid);
        sb.append('\'');
        sb.append(", appid='");
        sb.append(this.appid);
        sb.append('\'');
        sb.append(", chinfo='");
        sb.append(this.chinfo);
        sb.append('\'');
        sb.append(", lastClickTime=");
        sb.append(this.lastClickTime);
        sb.append('}');
        return sb.toString();
    }
}
