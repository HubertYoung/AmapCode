package com.autonavi.minimap.route.sharebike.net.param;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"source", "x", "y"}, url = "ws/valueadded/sharebike/ridestate/?")
public class RideStateParamEntity implements ParamEntity {
    public String citycode;
    public String orderid;
    public String source;
    public double x;
    public double y;

    public RideStateParamEntity(String str, double d, double d2, String str2, String str3) {
        this.source = str;
        this.x = d;
        this.y = d2;
        this.citycode = str2;
        this.orderid = str3;
    }

    public void resetLonLat(double d, double d2) {
        this.x = d;
        this.y = d2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("source=");
        sb.append(this.source);
        sb.append("x=");
        sb.append(this.x);
        sb.append("y=");
        sb.append(this.y);
        sb.append("citycode=");
        sb.append(this.citycode);
        return sb.toString();
    }
}
