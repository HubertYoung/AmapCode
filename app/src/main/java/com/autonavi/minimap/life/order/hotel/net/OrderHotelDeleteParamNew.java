package com.autonavi.minimap.life.order.hotel.net;

import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.SnsURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = SnsURLBuilder.class, host = "aos_sns_url", sign = {"order_ids"}, url = "ws/boss/order/hotel/delete/?")
public class OrderHotelDeleteParamNew implements ParamEntity {
    public String order_ids;
}
