package com.autonavi.minimap.route.bus.localbus.net.param;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"x1", "y1", "x2", "y2", "poiid1", "poiid2"}, url = "ws/transfer/auth/navigation/bus-ext/?")
public class RouteBusParamUrlWrapper implements ParamEntity {
    public String ad1;
    public String ad2;
    public int altercoord = 0;
    public String areacode = "";
    public int arrive = 0;
    public String date;
    public int eta = 1;
    public int group = 1;
    public int humanize = 0;
    public int isindoor = 1;
    public int max_trans = 4;
    public int night = 1;
    public String poiid1;
    public String poiid2;
    public int precision1 = 0;
    public int precision2 = 0;
    public int pure_walk = 0;
    public int req_num = 5;
    public int taxi = 0;
    public String time;
    public String type;
    public String ver = "3";
    public double x1;
    public double x2;
    public double y1;
    public double y2;
}
