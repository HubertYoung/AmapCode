package com.autonavi.minimap.route.bus.extbus.net.param;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"sstid", "tstid"}, url = "ws/mapapi/navigation/bus/railway/?")
public class TrainParam implements ParamEntity {
    public String order;
    public String pagenum;
    public String pagesize;
    public String sstid;
    public String tstid;
}
