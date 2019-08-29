package com.amap.bundle.drive.result.autonavisearchmanager.net;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "search_aos_url", sign = {"poiid"}, url = "ws/mapapi/poi/naviinfo/?")
public class CarSceneSearchParam implements ParamEntity {
    public String poiid;
    public String rel_type;

    public void setParam(String str, String str2) {
        this.poiid = str;
        this.rel_type = str2;
    }
}
