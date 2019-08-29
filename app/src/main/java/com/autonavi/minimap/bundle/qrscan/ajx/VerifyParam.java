package com.autonavi.minimap.bundle.qrscan.ajx;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_passport_url", sign = {"redirect_url"}, url = "/ws/pp/qrcode/verify/?")
public class VerifyParam implements ParamEntity {
    public String redirect_url;
}
