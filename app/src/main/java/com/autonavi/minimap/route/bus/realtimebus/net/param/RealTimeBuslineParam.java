package com.autonavi.minimap.route.bus.realtimebus.net.param;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"adcode", "lines", "stations"}, url = "ws/mapapi/realtimebus/linestation/")
public class RealTimeBuslineParam implements ParamEntity {
    public String adcode;
    public String count;
    public String from_page;
    public String is_refresh;
    public String lines;
    public String need_bus_status;
    public String need_bus_track;
    public boolean need_not_depart;
    public String need_over_station;
    public String source_type;
    public String stations;
}
