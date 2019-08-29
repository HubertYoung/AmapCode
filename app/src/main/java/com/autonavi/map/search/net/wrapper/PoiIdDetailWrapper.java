package com.autonavi.map.search.net.wrapper;

import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.SearchURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@Path(builder = SearchURLBuilder.class, host = "search_aos_url", sign = {"id"}, url = "ws/mapapi/poi/info/?")
@KeepImplementations
@KeepName
public class PoiIdDetailWrapper implements ParamEntity {
    public String cmspoi = "1";
    public String data_type = "POI";
    public String id = "";
    public String query_type = "IDQ";
    public String search_operate = "0";
    public String sugadcode;
    public String sugpoiname;
}
