package com.autonavi.minimap.life.order.hotel.net;

import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.SnsURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = SnsURLBuilder.class, host = "aos_sns_url", sign = {}, url = "ws/boss/order/hotel/list?")
public class OrderHotelParamNew implements ParamEntity {
    public int pagenum = 1;
    public int pagesize = 20;
}
