package com.autonavi.miniapp.biz.network.request;

public class SyncRecentUseRequest implements ffb {
    private String API_NAME = "mtop.autonavi.miniprogram.recentuse.syncRecentUse";
    private boolean NEED_ECODE = false;
    private boolean NEED_SESSION = false;
    private String VERSION = "1.0";
    private String adiu = null;
    private String appId = null;
    private String chinfo = null;
    private long lastClickTime = 0;
    private String recentUseJson = null;

    public String getAPI_NAME() {
        return this.API_NAME;
    }

    public void setAPI_NAME(String str) {
        this.API_NAME = str;
    }

    public String getVERSION() {
        return this.VERSION;
    }

    public void setVERSION(String str) {
        this.VERSION = str;
    }

    public boolean isNEED_ECODE() {
        return this.NEED_ECODE;
    }

    public void setNEED_ECODE(boolean z) {
        this.NEED_ECODE = z;
    }

    public boolean isNEED_SESSION() {
        return this.NEED_SESSION;
    }

    public void setNEED_SESSION(boolean z) {
        this.NEED_SESSION = z;
    }

    public String getAdiu() {
        return this.adiu;
    }

    public void setAdiu(String str) {
        this.adiu = str;
    }

    public long getLastClickTime() {
        return this.lastClickTime;
    }

    public void setLastClickTime(long j) {
        this.lastClickTime = j;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public String getChinfo() {
        return this.chinfo;
    }

    public void setChinfo(String str) {
        this.chinfo = str;
    }

    public String getRecentUseJson() {
        return this.recentUseJson;
    }

    public void setRecentUseJson(String str) {
        this.recentUseJson = str;
    }
}
