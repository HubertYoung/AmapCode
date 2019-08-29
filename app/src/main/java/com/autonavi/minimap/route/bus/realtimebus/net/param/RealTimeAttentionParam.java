package com.autonavi.minimap.route.bus.realtimebus.net.param;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"diu", "station_id", "line_id"}, url = "ws/bus_info/subscribe/")
public class RealTimeAttentionParam implements ParamEntity {
    public String diu;
    public String div;
    public String is_push;
    public String line_id;
    public String push_time;
    public String push_weekday;
    public String station_id;
    public String tid;
    public String token;
}
