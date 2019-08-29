package com.autonavi.minimap.route.sharebike.net.param;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"ts", "source"}, url = "ws/valueadded/sharebike/cpconf/?")
public class CpConfParamEntity implements ParamEntity {
    public String adcode;
    public long ts = System.currentTimeMillis();

    public CpConfParamEntity(String str) {
        this.adcode = str;
    }
}
