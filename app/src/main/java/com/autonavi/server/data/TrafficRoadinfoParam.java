package com.autonavi.server.data;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"x", "y"}, url = "/ws/archive/trafficevent_roadinfo/?")
public class TrafficRoadinfoParam implements ParamEntity {
    public String x;
    public String y;
}
