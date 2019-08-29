package com.autonavi.minimap.offline.auto.model.protocolModel;

import android.support.annotation.Keep;

@Keep
public class ATCityDataItemRequest {
    private String pinyin;

    public String getPinyin() {
        return this.pinyin;
    }

    public void setPinyin(String str) {
        this.pinyin = str;
    }
}
