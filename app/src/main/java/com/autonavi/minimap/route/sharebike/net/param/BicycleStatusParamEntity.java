package com.autonavi.minimap.route.sharebike.net.param;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "search_aos_url", sign = {"diu", "div", "cmd"}, url = "ws/shield/bicycle_status?")
public class BicycleStatusParamEntity implements ParamEntity {
    public String city;
    public String cmd = "2";
    public String frm;
    public String source;
    public String version = "1";
    public String x;
    public String y;

    public BicycleStatusParamEntity(String str, String str2, String str3, String str4, String str5, String str6) {
        this.city = str;
        this.x = str2;
        this.y = str3;
        this.source = str4;
        this.frm = str5;
        this.version = str6;
    }
}
