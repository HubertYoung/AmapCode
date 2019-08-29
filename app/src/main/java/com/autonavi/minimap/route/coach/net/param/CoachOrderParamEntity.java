package com.autonavi.minimap.route.coach.net.param;

import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.SnsURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = SnsURLBuilder.class, host = "aos_sns_url", sign = {""}, url = "ws/boss/order/bus/list/?")
public class CoachOrderParamEntity implements ParamEntity {
    public int page_num = 1;
    public int page_size = 20;
}
