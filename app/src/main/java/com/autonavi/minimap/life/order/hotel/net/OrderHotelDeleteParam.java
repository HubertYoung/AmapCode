package com.autonavi.minimap.life.order.hotel.net;

import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.SnsURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = SnsURLBuilder.class, host = "aos_sns_url", sign = {"oids"}, url = "ws/valueadded/ordercenter/order/delete/?")
public class OrderHotelDeleteParam implements ParamEntity {
    public String oids;
}
