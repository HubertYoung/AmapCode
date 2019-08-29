package com.autonavi.miniapp.biz.db.model;

import java.util.Objects;

public class RecentSmallProModel {
    private String appId;
    private String chInfo;
    private String iconUrl;
    private Long lastClickTime;
    private String name;
    private String slogan;

    public Long getLastClickTime() {
        return this.lastClickTime;
    }

    public void setLastClickTime(Long l) {
        this.lastClickTime = l;
    }

    public String getChInfo() {
        return this.chInfo;
    }

    public void setChInfo(String str) {
        this.chInfo = str;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

    public void setIconUrl(String str) {
        this.iconUrl = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getSlogan() {
        return this.slogan;
    }

    public void setSlogan(String str) {
        this.slogan = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.appId, ((RecentSmallProModel) obj).appId);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.appId});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("RecentSmallProModel{appId='");
        sb.append(this.appId);
        sb.append('\'');
        sb.append(", iconUrl='");
        sb.append(this.iconUrl);
        sb.append('\'');
        sb.append(", name='");
        sb.append(this.name);
        sb.append('\'');
        sb.append(", slogan='");
        sb.append(this.slogan);
        sb.append('\'');
        sb.append(", lastClickTime=");
        sb.append(this.lastClickTime);
        sb.append(", chInfo='");
        sb.append(this.chInfo);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
