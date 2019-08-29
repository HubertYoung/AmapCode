package com.autonavi.miniapp.biz.network.dataobject;

public class MiniAppDO {
    private String appId;
    private String appLink;
    private String appLogo;
    private String appName;
    private String appSlogan;
    private int favorite;
    private int sticky;

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public String getAppLink() {
        return this.appLink;
    }

    public void setAppLink(String str) {
        this.appLink = str;
    }

    public String getAppLogo() {
        return this.appLogo;
    }

    public void setAppLogo(String str) {
        this.appLogo = str;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String str) {
        this.appName = str;
    }

    public int getFavorite() {
        return this.favorite;
    }

    public void setFavorite(int i) {
        this.favorite = i;
    }

    public int getSticky() {
        return this.sticky;
    }

    public void setSticky(int i) {
        this.sticky = i;
    }

    public String getAppSlogan() {
        return this.appSlogan;
    }

    public void setAppSlogan(String str) {
        this.appSlogan = str;
    }
}
