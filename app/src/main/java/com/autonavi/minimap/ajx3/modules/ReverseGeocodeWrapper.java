package com.autonavi.minimap.ajx3.modules;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"lon", "lat"}, url = "ws/valueadded/private_car/rgeo?")
public class ReverseGeocodeWrapper implements ParamEntity {
    public String lat;
    public String lon;
}
