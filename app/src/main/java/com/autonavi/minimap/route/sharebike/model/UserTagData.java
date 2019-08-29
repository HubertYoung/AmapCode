package com.autonavi.minimap.route.sharebike.model;

import java.io.Serializable;

public class UserTagData implements Serializable {
    private UserTagDataFees fees;
    private UserTagDataInfo taginfo;
    private UserTagDataTips tips;
    private String tipsMd5;

    public String getTipsMd5() {
        return this.tipsMd5;
    }

    public void setTipsMd5(String str) {
        this.tipsMd5 = str;
    }

    public UserTagDataInfo getTaginfo() {
        return this.taginfo;
    }

    public void setTaginfo(UserTagDataInfo userTagDataInfo) {
        this.taginfo = userTagDataInfo;
    }

    public UserTagDataTips getTips() {
        return this.tips;
    }

    public void setTips(UserTagDataTips userTagDataTips) {
        this.tips = userTagDataTips;
    }

    public UserTagDataFees getFees() {
        return this.fees;
    }

    public void setFees(UserTagDataFees userTagDataFees) {
        this.fees = userTagDataFees;
    }
}
