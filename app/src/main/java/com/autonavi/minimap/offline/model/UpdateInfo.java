package com.autonavi.minimap.offline.model;

import com.autonavi.minimap.offline.util.JsConvertUtils;

public class UpdateInfo {
    public static final int UPDATE_CODE_ALL_STATUS = 3;
    public static final int UPDATE_CODE_CITY_STATUS = 1;
    public static final int UPDATE_CODE_PROGRESS = 2;
    public static final int UPDATE_PROVINCE_TYPE = 1;
    private int cityId = -1;
    private int cityStatus = -1;
    private int code = 2;
    private int error = -1;
    private int mapItemStatus = -1;
    private int mapUpdateFlag = -1;
    private int naviItemStatus = -1;
    private int progress = -1;
    private int routeUpdateFlag = -1;
    private int updateFlag = -1;
    private int updateType;

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public String getCityId() {
        return JsConvertUtils.checkCityId(String.valueOf(this.cityId));
    }

    public void setCityId(int i) {
        this.cityId = i;
    }

    public int getMapItemStatus() {
        return this.mapItemStatus;
    }

    public void setMapItemStatus(int i) {
        this.mapItemStatus = i;
    }

    public int getCityStatus() {
        return this.cityStatus;
    }

    public void setCityStatus(int i) {
        this.cityStatus = i;
    }

    public int getNaviItemStatus() {
        return this.naviItemStatus;
    }

    public void setNaviItemStatus(int i) {
        this.naviItemStatus = i;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int i) {
        this.progress = i;
    }

    public int getError() {
        return this.error;
    }

    public void setError(int i) {
        this.error = i;
    }

    public int getUpdateFlag() {
        return this.updateFlag;
    }

    public void setUpdateFlag(int i) {
        this.updateFlag = i;
    }

    public int getMapUpdateFlag() {
        return this.mapUpdateFlag;
    }

    public void setMapUpdateFlag(int i) {
        this.mapUpdateFlag = i;
    }

    public int getRouteUpdateFlag() {
        return this.routeUpdateFlag;
    }

    public void setRouteUpdateFlag(int i) {
        this.routeUpdateFlag = i;
    }

    public int getUpdateType() {
        return this.updateType;
    }

    public void setUpdateType(int i) {
        this.updateType = i;
    }
}
