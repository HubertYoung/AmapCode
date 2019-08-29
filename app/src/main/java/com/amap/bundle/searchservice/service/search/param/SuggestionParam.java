package com.amap.bundle.searchservice.service.search.param;

import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.autonavi.common.model.GeoPoint;
import java.io.Serializable;

public class SuggestionParam implements ParamEntity, Serializable {
    public long adcode;
    public String category;
    public String keyWord;
    public GeoPoint mCenter;
    public String suggestType;

    public SuggestionParam() {
        this.mCenter = new GeoPoint();
        this.adcode = 0;
        this.keyWord = null;
        this.category = null;
        this.suggestType = null;
    }

    public SuggestionParam(long j, String str, String str2, String str3) {
        this.mCenter = new GeoPoint();
        this.adcode = j;
        this.keyWord = str;
        this.category = str2;
        this.suggestType = str3;
    }

    public long getAdcode() {
        return this.adcode;
    }

    public void setAdcode(long j) {
        this.adcode = j;
    }

    public String getKeyWord() {
        return this.keyWord;
    }

    public void setKeyWord(String str) {
        this.keyWord = str;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String str) {
        this.category = str;
    }

    public String getSuggestType() {
        return this.suggestType;
    }

    public void setSuggestType(String str) {
        this.suggestType = str;
    }

    public GeoPoint getCenter() {
        return this.mCenter;
    }

    public void setCenter(GeoPoint geoPoint) {
        if (geoPoint == null) {
            this.mCenter = new GeoPoint();
        } else {
            this.mCenter = geoPoint;
        }
    }
}
