package com.autonavi.minimap.drive.trafficboard;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {}, url = "ws/transfer/auth/traffic/congestion/?")
public class AosTrafficTopBoardWrapper implements ParamEntity {
    public String citycode = "";
    public String listindex = "";
    public String md5 = "";
    public String x = "";
    public String y = "";
}
