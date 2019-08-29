package com.autonavi.minimap.basemap.traffic;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"diu", "div"}, url = "/ws/transfer/auth/alarm-access-service-for122/aos/state/?")
public class TrafficAlarmParam implements ParamEntity {
    public String diu;
    public String div;
}
