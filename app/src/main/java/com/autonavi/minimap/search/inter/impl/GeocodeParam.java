package com.autonavi.minimap.search.inter.impl;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "search_aos_url", sign = {"address"}, url = "ws/mapapi/geo/code?")
public class GeocodeParam implements ParamEntity {
    public String adcode;
    public String address;
    public boolean onerow = true;
}
