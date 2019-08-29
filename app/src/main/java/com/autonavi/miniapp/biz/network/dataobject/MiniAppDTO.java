package com.autonavi.miniapp.biz.network.dataobject;

public class MiniAppDTO {
    private String appId;
    private String appSlogan;
    private String iconUrl;
    private String name;
    private boolean top;

    public String getAppSlogan() {
        return this.appSlogan;
    }

    public void setAppSlogan(String str) {
        this.appSlogan = str;
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

    public boolean getTop() {
        return this.top;
    }

    public void setTop(boolean z) {
        this.top = z;
    }
}
