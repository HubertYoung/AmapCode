package com.autonavi.minimap.route.foot.net.param;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"poinavi"}, url = "ws/mapapi/navigation/foot/?")
public class RouteFootParamUrlWrapper implements ParamEntity {
    public String fromX;
    public String fromY;
    public String from_floor;
    public String from_pid;
    public String isindoor = "1";
    public String lv = "3.1";
    public String output = "bin";
    public String poinavi;
    public int taxi = 1;
    public String toX;
    public String toY;
    public String to_floor;
    public String to_pid;
}
