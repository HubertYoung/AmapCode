package com.autonavi.minimap.life.order.hotel.net;

import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.SnsURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = SnsURLBuilder.class, host = "aos_sns_url", sign = {"hotel_category"}, url = "ws/valueadded/ordercenter/order/category/list/?")
public class OrderHotelParamByCategory implements ParamEntity {
    public int hotel_category;
    public int limit = 0;
    public int pagenum = 1;
    public int pagesize = 20;
}
