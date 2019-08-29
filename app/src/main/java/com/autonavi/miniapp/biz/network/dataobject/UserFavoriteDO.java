package com.autonavi.miniapp.biz.network.dataobject;

import java.util.Date;

public class UserFavoriteDO {
    private String alipayUid;
    private String appId;
    private int favorite;
    private Date gmtCreate;
    private Date gmtModified;
    private long id;
    private int sticky;
    private String uid;

    public String getAlipayUid() {
        return this.alipayUid;
    }

    public void setAlipayUid(String str) {
        this.alipayUid = str;
    }

    public void setId(long j) {
        this.id = j;
    }

    public long getId() {
        return this.id;
    }

    public void setGmtCreate(Date date) {
        this.gmtCreate = date;
    }

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtModified(Date date) {
        this.gmtModified = date;
    }

    public Date getGmtModified() {
        return this.gmtModified;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public String getUid() {
        return this.uid;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public void setFavorite(int i) {
        this.favorite = i;
    }

    public int getFavorite() {
        return this.favorite;
    }

    public void setSticky(int i) {
        this.sticky = i;
    }

    public int getSticky() {
        return this.sticky;
    }
}
