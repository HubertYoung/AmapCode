package com.autonavi.minimap.route.bus.localbus.net.param;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"line", "sstid", "tstid"}, url = "ws/mapapi/navigation/bus/alterline?")
public class AlterListParam implements ParamEntity {
    public String areacode;
    public String date;
    public int eta = 1;
    public double lat;
    public String line;
    public double lon;
    public String sstid;
    public String time;
    public String tstid;
}
