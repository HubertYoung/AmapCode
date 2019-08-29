package com.autonavi.minimap.offline.auto.model.protocolModel;

import android.support.annotation.Keep;
import java.util.List;

@Keep
public class ATUploadCityDataListRequest {
    private List<ATUploadCityDataItem> uploadCities;

    public List<ATUploadCityDataItem> getUploadCities() {
        return this.uploadCities;
    }

    public void setUploadCities(List<ATUploadCityDataItem> list) {
        this.uploadCities = list;
    }
}
