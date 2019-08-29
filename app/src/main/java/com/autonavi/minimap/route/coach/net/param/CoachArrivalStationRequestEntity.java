package com.autonavi.minimap.route.coach.net.param;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"adcode", "diu", "div"}, url = "ws/valueadded/coach/arrival_station")
public class CoachArrivalStationRequestEntity implements ParamEntity {
    public String adcode = "";

    public CoachArrivalStationRequestEntity(String str) {
        this.adcode = str;
    }
}
