package com.autonavi.minimap.route.sharebike.net.param;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "search_aos_url", sign = {"diu", "div", "cmd"}, url = "ws/shield/bicycle_status?")
public class BicycleDetailParamWithTokenEntity implements ParamEntity {
    public String city;
    public String cmd = "3";
    public String id;
    public String scope;
    public String source;
    public String token = "";
    public String version = "1";
    public String x;
    public String y;

    public BicycleDetailParamWithTokenEntity(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.city = str;
        this.x = str2;
        this.y = str3;
        this.source = str4;
        this.id = str5;
        this.scope = str6;
        this.version = str7;
        this.token = str8;
    }
}
