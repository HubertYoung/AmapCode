package com.autonavi.minimap.offline.auto.model.protocolModel;

import android.support.annotation.Keep;

@Keep
public class ATDeleteCityDataItemRequest {
    private String pinyinList;

    public String getPinyinList() {
        return this.pinyinList;
    }

    public void setPinyinList(String str) {
        this.pinyinList = str;
    }
}
