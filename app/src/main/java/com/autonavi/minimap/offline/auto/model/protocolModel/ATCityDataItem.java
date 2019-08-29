package com.autonavi.minimap.offline.auto.model.protocolModel;

import android.support.annotation.Keep;
import java.util.List;

@Keep
public class ATCityDataItem {
    private String adCode;
    private List<ATCityDataFile> files;
    private String name;
    private String pinyin;
    private List<ATCityDataItem> subCities;

    public String getAdCode() {
        return this.adCode;
    }

    public void setAdCode(String str) {
        this.adCode = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public void setPinyin(String str) {
        this.pinyin = str;
    }

    public List<ATCityDataItem> getSubCities() {
        return this.subCities;
    }

    public void setSubCities(List<ATCityDataItem> list) {
        this.subCities = list;
    }

    public List<ATCityDataFile> getFiles() {
        return this.files;
    }

    public void setFiles(List<ATCityDataFile> list) {
        this.files = list;
    }
}
