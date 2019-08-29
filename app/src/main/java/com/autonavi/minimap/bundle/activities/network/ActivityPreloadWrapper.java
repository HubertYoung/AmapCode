package com.autonavi.minimap.bundle.activities.network;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.common.model.GeoPoint;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"user_loc"}, url = "ws/mapapi/geo/reversepoi?")
@KeepImplementations
@KeepName
public class ActivityPreloadWrapper implements ParamEntity {
    String biztype = "op_icon";
    String user_loc;

    public ActivityPreloadWrapper(GeoPoint geoPoint) {
        StringBuilder sb = new StringBuilder();
        sb.append(geoPoint.getLongitude());
        sb.append(",");
        sb.append(geoPoint.getLatitude());
        this.user_loc = sb.toString();
    }
}
