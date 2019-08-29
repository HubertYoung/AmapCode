package com.autonavi.minimap.basemap.traffic;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"eventid"}, url = "/ws/archive/trafficevent_detail/more/?")
public class TrafficDetailRequestInfo implements ParamEntity {
    public int eventid;

    public TrafficDetailRequestInfo(int i) {
        this.eventid = i;
    }
}
