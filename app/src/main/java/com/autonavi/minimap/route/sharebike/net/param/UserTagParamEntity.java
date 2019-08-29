package com.autonavi.minimap.route.sharebike.net.param;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"ts"}, url = "ws/valueadded/sharebike/usertag?")
public class UserTagParamEntity implements ParamEntity {
    public float ts;

    public UserTagParamEntity(float f) {
        this.ts = f;
    }
}
