package com.autonavi.minimap.offline.auto.model.protocolModel;

import android.support.annotation.Keep;
import java.util.List;

@Keep
public class ATUploadCityDataItem {
    private String adCode;
    private String name;
    private String pinyin;
    private int state;
    private List<ATUploadCityDataFile> uploadFiles;

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

    public List<ATUploadCityDataFile> getUploadFiles() {
        return this.uploadFiles;
    }

    public void setUploadFiles(List<ATUploadCityDataFile> list) {
        this.uploadFiles = list;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int i) {
        this.state = i;
    }
}
