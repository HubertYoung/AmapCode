package com.autonavi.minimap.onekeycheck.request;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.amap.bundle.network.util.NetworkReachability;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
@Path(builder = AosURLBuilder.class, host = "lotuspool_upload_url", sign = {"diu", "div"}, url = "/ud/updata")
public class UpLoadParaEntity implements ParamEntity {
    private static final String MODE = "onekey_feedback";
    public String content;
    public int environment = (NetworkReachability.b() ^ true ? 1 : 0);
    public String md5;
    public String mode = MODE;
    public long uploadTime;

    public UpLoadParaEntity(String str, long j) {
        this.content = str;
        this.md5 = agy.a(str);
        this.uploadTime = j;
    }
}
