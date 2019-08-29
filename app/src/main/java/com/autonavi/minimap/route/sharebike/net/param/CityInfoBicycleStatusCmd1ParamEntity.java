package com.autonavi.minimap.route.sharebike.net.param;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "search_aos_url", sign = {"diu", "div", "cmd"}, url = "ws/shield/bicycle_status?")
public class CityInfoBicycleStatusCmd1ParamEntity implements ParamEntity {
    public String city;
    public String cmd = "1";
    public String version = "2";

    public CityInfoBicycleStatusCmd1ParamEntity(String str) {
        this.city = str;
    }
}
