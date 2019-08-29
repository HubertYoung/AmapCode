package com.autonavi.minimap.route.bus.busline.model;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.minimap.route.bus.model.BusLineSearchWrapper;

@Path(builder = AosURLBuilder.class, host = "search_aos_url", sign = {"keywords", "city", "longitude", "latitude"}, url = "ws/mapapi/poi/buslite/?")
public class BusLineSearchKeywordsUrlWrapper extends BusLineSearchWrapper {
    public String city = "";
    public String id = "";
    public String latitude = "";
    public String longitude = "";
    public int pagesize = 10;
    public String range = "50000";
    public String search_sceneid = "";
    public String superid = "";
    public String transfer_realtimebus = "";
    public String version = "2.14";

    public BusLineSearchKeywordsUrlWrapper(String str, String str2, String str3, String str4, int i, int i2) {
        this.keywords = str;
        this.city = str2;
        this.id = str3;
        this.search_sceneid = str4;
        this.pagenum = i;
        this.pagesize = i2;
    }

    public void setSuperid(String str) {
        this.superid = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("BusLineSearchKeywordsUrlWrapper [version=");
        sb.append(this.version);
        sb.append(", pagesize=");
        sb.append(this.pagesize);
        sb.append(", keywords=");
        sb.append(this.keywords);
        sb.append(", city=");
        sb.append(this.city);
        sb.append(", id=");
        sb.append(this.id);
        sb.append(", longitude=");
        sb.append(this.longitude);
        sb.append(", latitude=");
        sb.append(this.latitude);
        sb.append(", range=");
        sb.append(this.range);
        sb.append(", pagenum =");
        sb.append(this.pagenum);
        sb.append("]");
        return sb.toString();
    }
}
