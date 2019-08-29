package com.autonavi.minimap.route.train.net;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"train"}, url = "ws/valueadded/train/index?")
public class TrainInfoParam implements ParamEntity {
    public String train = null;
}
