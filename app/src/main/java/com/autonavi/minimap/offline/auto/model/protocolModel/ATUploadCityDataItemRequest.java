package com.autonavi.minimap.offline.auto.model.protocolModel;

import android.support.annotation.Keep;

@Keep
public class ATUploadCityDataItemRequest {
    private ATUploadCityDataItem uploadCity;

    public ATUploadCityDataItem getUploadCity() {
        return this.uploadCity;
    }

    public void setUploadCity(ATUploadCityDataItem aTUploadCityDataItem) {
        this.uploadCity = aTUploadCityDataItem;
    }
}
