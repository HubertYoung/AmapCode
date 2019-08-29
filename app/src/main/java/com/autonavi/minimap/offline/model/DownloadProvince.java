package com.autonavi.minimap.offline.model;

import android.text.TextUtils;
import com.autonavi.minimap.offline.util.JsConvertUtils;
import java.util.List;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public class DownloadProvince {
    private String adCode;
    private List<DownloadCity> cityList;
    private int code;
    private int downloadStatus;
    private String isCurrentCity;
    private String jianpin;
    private long mapSize;
    private int mapState;
    private String mapUpdateFlag = "0";
    private String name;
    private int naviState;
    private String pinyin;
    private long routeSize;
    private String routeUpdateFlag = "0";
    private String updateFlag = "0";
    private String updateSize = "0";

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

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

    public List<DownloadCity> getCityList() {
        return this.cityList;
    }

    public void setCityList(List<DownloadCity> list) {
        this.cityList = list;
    }

    public String getUpdateFlag() {
        return this.updateFlag;
    }

    public void setUpdateFlag(String str) {
        this.updateFlag = str;
    }

    public String getIsCurrentCity() {
        return this.isCurrentCity;
    }

    public void setIsCurrentCity(String str) {
        this.isCurrentCity = str;
    }

    public String getUpdateSize() {
        return this.updateSize;
    }

    public void setUpdateSize(String str) {
        if (TextUtils.isDigitsOnly(str)) {
            this.updateSize = str;
        }
    }

    public String getMapUpdateFlag() {
        return this.mapUpdateFlag;
    }

    public void setMapUpdateFlag(String str) {
        this.mapUpdateFlag = str;
    }

    public String getRouteUpdateFlag() {
        return this.routeUpdateFlag;
    }

    public void setRouteUpdateFlag(String str) {
        this.routeUpdateFlag = str;
    }
}
