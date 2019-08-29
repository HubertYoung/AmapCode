package com.autonavi.minimap.route.bus.realtimebus.net.param;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"adcode", "xy", "lines"}, url = "ws/mapapi/realtimebus/lines/ex/?")
public class RealTimeBusLinesExUrlWrapper implements ParamEntity {
    public String adcode = "";
    public String from_page = "";
    public String is_refresh = "";
    public String lines = "";
    public String xy = "";

    public RealTimeBusLinesExUrlWrapper(String str, String str2, String str3, String str4, String str5) {
        this.adcode = str;
        this.xy = str2;
        this.lines = str3;
        this.from_page = str4;
        this.is_refresh = str5;
    }
}
