package com.amap.bundle.lotuspool.internal.model.http;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"diu", "div", "_aosmd5"}, url = "ws/shield/frogserver/rd/displist?")
public class CommandsListEntity implements ParamEntity {
    public String last_dispatch_sequence;
}
