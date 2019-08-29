package com.autonavi.miniapp.biz.network.request;

public class ListRecommendMiniAppsRequest implements ffb {
    private String API_NAME = "mtop.autonavi.miniprogram.recommend.listRecommendMiniApps";
    private boolean NEED_ECODE = false;
    private boolean NEED_SESSION = false;
    private String VERSION = "1.0";

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
}
