package com.autonavi.minimap.route.train.net;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"from_station", "to_station"}, url = "ws/valueadded/train/station?")
public class TrainTicketListParam implements ParamEntity {
    public String from_station = null;
    public String to_station = null;
}
