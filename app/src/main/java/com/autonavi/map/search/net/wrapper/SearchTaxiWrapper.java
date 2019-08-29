package com.autonavi.map.search.net.wrapper;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "search_aos_url", sign = {"start_x", "start_y"}, url = "/ws/mapapi/navigation/auto/taxi_comparator/?")
public class SearchTaxiWrapper implements ParamEntity {
    public String end_name = "";
    public String end_x = "";
    public String end_y = "";
    public int highway_cost = 0;
    public String mode = "simple";
    public String start_name = "";
    public String start_x = "";
    public String start_y = "";
}
