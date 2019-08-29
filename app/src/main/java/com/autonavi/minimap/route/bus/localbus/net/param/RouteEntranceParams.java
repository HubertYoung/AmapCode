package com.autonavi.minimap.route.bus.localbus.net.param;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"lon", "lat"}, url = "ws/mapapi/entrance_list/?")
public class RouteEntranceParams implements ParamEntity {
    public int adcode;
    public double lat;
    public double lon;
    public String xy_backup;
}
