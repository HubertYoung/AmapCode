package com.autonavi.minimap.app.init.amaplog.network;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.location.sdk.fusion.LocationParams;

@Path(builder = AosURLBuilder.class, host = "loginall_url", sign = {"diu", "div"}, url = "/ws/shield/alc/collect")
public class ALCRecordUploadParam implements ParamEntity {
    public String content;
    public int environment;
    public String md5;
    public String mode = LocationParams.PARA_AMAP_CLOUD_ALC;
    public long uploadTime;

    public ALCRecordUploadParam(String str) {
        this.content = str;
        this.uploadTime = System.currentTimeMillis();
        this.environment = NetworkReachability.b() ^ true ? 1 : 0;
        this.md5 = agy.a(str);
    }
}
