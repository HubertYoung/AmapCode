package com.autonavi.bundle.routecommute.net;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"diu", "div"}, url = "/ws/shield/maps/mapapi/navigation/address?")
@KeepName
public class NaviAddressParam implements ParamEntity {
}
