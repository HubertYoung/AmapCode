package com.autonavi.map.search.net.wrapper;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "search_aos_url", sign = {"start_x", "start_y", "start_poi", "end_x", "end_y", "end_poi"}, url = "/ws/mapapi/navigation/auto/etarequest/?")
public class SearchETAWrapper implements ParamEntity {
    public String end_adcode = "";
    public String end_poi = "";
    public String end_x = "";
    public String end_y = "";
    public String etype = "";
    public String start_adcode = "";
    public String start_poi = "";
    public String start_x = "";
    public String start_y = "";
    public int taxi_price_flag;
}
