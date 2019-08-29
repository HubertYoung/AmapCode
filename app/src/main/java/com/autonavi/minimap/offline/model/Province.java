package com.autonavi.minimap.offline.model;

import android.text.TextUtils;
import com.autonavi.minimap.offline.util.JsConvertUtils;
import java.util.List;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public class Province {
    private String adCode;
    private List<City> cityList;
    private int downloadStatus;
    private String jianpin;
    private long mapSize;
    private int mapState;
    private String name;
    private int naviState;
    private String pinyin;
    private long routeSize;
    private String updateFlag = "0";
    private String updateSize = "0";

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getJianpin() {
        return this.jianpin;
    }

    public void setJianpin(String str) {
        this.jianpin = str;
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public void setPinyin(String str) {
        this.pinyin = str;
    }

    public String getAdCode() {
        return this.adCode;
    }

    public void setAdCode(String str) {
        this.adCode = JsConvertUtils.checkAdCode(str);
    }

    public long getMapSize() {
        return this.mapSize;
    }

    public void setMapSize(long j) {
        this.mapSize = j;
    }

    public long getRouteSize() {
        return this.routeSize;
    }

    public void setRouteSize(long j) {
        this.routeSize = j;
    }

    public int getMapState() {
        return this.mapState;
    }

    public void setMapState(int i) {
        this.mapState = i;
    }

    public int getNaviState() {
        return this.naviState;
    }

    public void setNaviState(int i) {
        this.naviState = i;
    }

    public int getDownloadStatus() {
        return this.downloadStatus;
    }

    public void setDownloadStatus(int i) {
        this.downloadStatus = i;
    }

    public List<City> getCityList() {
        return this.cityList;
    }

    public void setCityList(List<City> list) {
        this.cityList = list;
    }

    public String getUpdateFlag() {
        return this.updateFlag;
    }

    public void setUpdateFlag(String str) {
        this.updateFlag = str;
    }

    public String getUpdateSize() {
        return this.updateSize;
    }

    public void setUpdateSize(String str) {
        if (TextUtils.isDigitsOnly(str)) {
            this.updateSize = str;
        }
    }
}
