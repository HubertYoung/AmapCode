package com.autonavi.minimap.route.train.net.param;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"diu", "div"}, url = "ws/valueadded/train/static/stations/?")
public class StationRequestEntity implements ParamEntity {
    public String md5 = "";
    public String station_type = "train";

    public StationRequestEntity(String str, String str2) {
        this.station_type = str;
        this.md5 = str2;
    }
}
