package com.autonavi.miniapp.biz.network.request;

public class CreateOrUpdateUserFavoriteRequest implements ffb {
    private String API_NAME = "mtop.autonavi.miniprogram.favorite.createOrUpdateUserFavorite";
    private boolean NEED_ECODE = false;
    private boolean NEED_SESSION = false;
    private String VERSION = "1.0";
    private String alipayUid = null;
    private String appId = null;
    private Long favorite = null;
    private String gmtCreate = null;
    private String gmtModified = null;
    private Long id = null;
    private Long sticky = null;
    private String uid = null;

    public String getAlipayUid() {
        return this.alipayUid;
    }

    public void setAlipayUid(String str) {
        this.alipayUid = str;
    }

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

    public String getUid() {
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public String getGmtModified() {
        return this.gmtModified;
    }

    public void setGmtModified(String str) {
        this.gmtModified = str;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public Long getSticky() {
        return this.sticky;
    }

    public void setSticky(Long l) {
        this.sticky = l;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long l) {
        this.id = l;
    }

    public String getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(String str) {
        this.gmtCreate = str;
    }

    public Long getFavorite() {
        return this.favorite;
    }

    public void setFavorite(Long l) {
        this.favorite = l;
    }
}
