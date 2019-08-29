package com.autonavi.minimap.offline.auto.model.protocolModel;

import android.support.annotation.Keep;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import java.util.List;

@Keep
public class ATCityDataListResponse implements ResponseValue {
    private List<ATCityDataItem> cities;
    private String code;
    private String msg;

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public List<ATCityDataItem> getCities() {
        return this.cities;
    }

    public void setCities(List<ATCityDataItem> list) {
        this.cities = list;
    }
}
