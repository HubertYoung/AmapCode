package com.autonavi.minimap.route.bus.busline.model;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.minimap.route.bus.model.BusLineSearchWrapper;

@Path(builder = AosURLBuilder.class, host = "search_aos_url", sign = {"id", "city"}, url = "ws/mapapi/poi/newbus/?")
public class BusLineSearchIdUrlWrapper extends BusLineSearchWrapper {
    public String city = "";
    public String id = "";
    public int pagesize = 10;
    public String search_sceneid = "";
    public String version = "2.14";

    public BusLineSearchIdUrlWrapper(String str, String str2, String str3, int i) {
        if (str == null) {
            this.city = "";
        } else {
            this.city = str;
        }
        this.id = str2;
        this.search_sceneid = str3;
        this.pagesize = i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("BusLineSearchIdUrlWrapper [version=");
        sb.append(this.version);
        sb.append(", pagesize=");
        sb.append(this.pagesize);
        sb.append(", keywords=");
        sb.append(this.keywords);
        sb.append(", city=");
        sb.append(this.city);
        sb.append(", id=");
        sb.append(this.id);
        sb.append(", search_sceneid=");
        sb.append(this.search_sceneid);
        sb.append(", pagenum=");
        sb.append(this.pagenum);
        sb.append("]");
        return sb.toString();
    }
}
