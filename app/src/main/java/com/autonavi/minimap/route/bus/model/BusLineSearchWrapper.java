package com.autonavi.minimap.route.bus.model;

import com.amap.bundle.network.request.param.builder.ParamEntity;

public abstract class BusLineSearchWrapper implements ParamEntity {
    public boolean isShowNextPage = true;
    public String keywords = "";
    public int pagenum = 1;
}
