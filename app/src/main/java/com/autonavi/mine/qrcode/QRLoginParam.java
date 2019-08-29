package com.autonavi.mine.qrcode;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_passport_url", sign = {"qrcode_id"}, url = "ws/pp/qrcode/confirm/?")
public class QRLoginParam implements ParamEntity {
    public String qrcode_id;
}
