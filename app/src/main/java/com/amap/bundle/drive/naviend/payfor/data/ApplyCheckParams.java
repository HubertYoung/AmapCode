package com.amap.bundle.drive.naviend.payfor.data;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "operational_url", sign = {"poiid", "tid"}, url = "/ws/use_pay/apply_check?")
public class ApplyCheckParams implements ParamEntity {
    public int distance;
    public String poiid;
}
