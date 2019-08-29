package com.autonavi.minimap.route.sharebike.net.param;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"ts"}, url = "ws/valueadded/sharebike/iconconf?")
public class IconConfParamEntity implements ParamEntity {
    public float ts;

    public IconConfParamEntity(float f) {
        this.ts = f;
    }
}
