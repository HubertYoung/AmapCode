package com.amap.bundle.adiu.internal.net;

import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.network.request.param.builder.AosRequestBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.server.aos.serverkey;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@Path(builder = AosRequestBuilder.class, host = "adiu_url", sign = {"div"}, url = "/ws/device/adius/")
@KeepImplementations
@KeepName
public class AdiuWrapper implements ParamEntity {
    String channel = serverkey.getAosChannel();
    String div = NetworkParam.getDiv();
}
