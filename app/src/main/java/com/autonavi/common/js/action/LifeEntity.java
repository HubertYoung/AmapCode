package com.autonavi.common.js.action;

import android.text.TextUtils;
import java.io.Serializable;

public class LifeEntity implements Serializable {
    private String isCu;
    private String isDiscount = "0";
    private String isGroupBuy = "0";
    private String isGroupOrJuan;
    public String isHourlyRoom = "";
    private String isJuan = "0";
    private String isZuo = "0";
    public String jsonStr;
    private String movieId;
    public String oid;
    public String phone;
    public String request;
    public String result;
    public String srcType;
    public String type;

    public String getIsGroupOrJuan() {
        return this.isGroupOrJuan;
    }

    public void setIsGroupOrJuan(String str) {
        this.isGroupOrJuan = str;
    }

    public void setIsGroupOrJuan(boolean z) {
        if (z) {
            this.isGroupOrJuan = "1";
        } else {
            this.isGroupOrJuan = "0";
        }
    }

    public String getIsDiscount() {
        return this.isDiscount;
    }

    public void setIsDiscount(String str) {
        this.isDiscount = str;
    }

    public String getMovieId() {
        return this.movieId;
    }

    public void setMovieId(String str) {
        this.movieId = str;
    }

    public String getIsGroupBuy() {
        return this.isGroupBuy;
    }

    public boolean isGroupBuy() {
        return getBoolean(this.isGroupBuy);
    }

    public void setIsGroupBuy(boolean z) {
        if (z) {
            this.isGroupBuy = "1";
        } else {
            this.isGroupBuy = "0";
        }
    }

    public void setIsGroupBuy(String str) {
        this.isGroupBuy = str;
    }

    public String getIsJuan() {
        return this.isJuan;
    }

    public boolean isJuan() {
        return getBoolean(this.isJuan);
    }

    public void setIsJuan(String str) {
        this.isJuan = str;
    }

    public void setIsJuan(boolean z) {
        if (z) {
            this.isJuan = "1";
        } else {
            this.isJuan = "0";
        }
    }

    public String getIsZuo() {
        return this.isZuo;
    }

    public boolean isZou() {
        return getBoolean(this.isZuo);
    }

    public void setIsZuo(String str) {
        this.isZuo = str;
    }

    public void setIsZuo(boolean z) {
        if (z) {
            this.isZuo = "1";
        } else {
            this.isZuo = "0";
        }
    }

    public String getIsCu() {
        return this.isCu;
    }

    public void setIsCu(String str) {
        this.isCu = str;
    }

    public boolean getBoolean(String str) {
        return !TextUtils.isEmpty(str) && !"0".equals(str);
    }
}
