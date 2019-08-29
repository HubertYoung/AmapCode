package com.autonavi.bundle.maphome.api.reverse;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "search_aos_url", sign = {"longitude", "latitude"}, url = "ws/mapapi/geo/reversecode?")
public class ReverseGeocodeParam implements ParamEntity {
    public int crossnum = 1;
    public final int desctype = 1;
    public double latitude;
    public double longitude;
    public final boolean near = true;
    public int poinum;
    public int roadnum = 1;
}
