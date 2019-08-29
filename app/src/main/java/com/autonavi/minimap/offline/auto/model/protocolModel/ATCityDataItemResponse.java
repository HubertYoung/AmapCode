package com.autonavi.minimap.offline.auto.model.protocolModel;

import android.support.annotation.Keep;

@Keep
public class ATCityDataItemResponse {
    private ATCityDataItem city;
    private int code;
    private String msg;

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public ATCityDataItem getCity() {
        return this.city;
    }

    public void setCity(ATCityDataItem aTCityDataItem) {
        this.city = aTCityDataItem;
    }
}
