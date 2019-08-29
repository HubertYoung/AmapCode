package com.autonavi.minimap.life.order.hotel.net;

import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.SearchURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = SearchURLBuilder.class, host = "search_aos_url", sign = {}, url = "ws/mapapi/poi/hotelcondition/?")
public class OrderHotelParam implements ParamEntity {
}
