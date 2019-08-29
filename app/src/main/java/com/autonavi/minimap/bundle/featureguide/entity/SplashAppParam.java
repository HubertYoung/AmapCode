package com.autonavi.minimap.bundle.featureguide.entity;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {}, url = "ws/app/conf/app_promotion")
public class SplashAppParam implements ParamEntity {
}
