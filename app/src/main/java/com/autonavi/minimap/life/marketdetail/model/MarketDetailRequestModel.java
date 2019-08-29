package com.autonavi.minimap.life.marketdetail.model;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"poiid"}, url = "ws/valueadded/shopping/poi_list/?")
public class MarketDetailRequestModel implements ParamEntity {
    private String classify;
    private String filter;
    private String floor;
    private int pagenum;
    private int pagesize;
    private String poiid;

    public MarketDetailRequestModel(String str, String str2, String str3, String str4, int i, int i2) {
        this.poiid = str;
        this.floor = str2;
        this.classify = str3;
        this.filter = str4;
        this.pagenum = i;
        this.pagesize = i2;
    }

    public MarketDetailRequestModel(String str) {
        this.poiid = str;
        this.floor = null;
        this.classify = null;
        this.filter = null;
        this.pagenum = 1;
        this.pagesize = 10;
    }

    public String getPoiid() {
        return this.poiid;
    }

    public void setPoiid(String str) {
        this.poiid = str;
    }

    public String getFloor() {
        return this.floor;
    }

    public void setFloor(String str) {
        this.floor = str;
    }

    public String getClassify() {
        return this.classify;
    }

    public void setClassify(String str) {
        this.classify = str;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String str) {
        this.filter = str;
    }

    public int getPagenum() {
        return this.pagenum;
    }

    public void setPagenum(int i) {
        this.pagenum = i;
    }

    public int getPagesize() {
        return this.pagesize;
    }

    public void setPagesize(int i) {
        this.pagesize = i;
    }
}
