package com.autonavi.minimap.app.init.amaplog.network;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.amap.bundle.network.util.NetworkReachability;

@Path(builder = AosURLBuilder.class, host = "lotuspool_upload_url", sign = {"diu", "div"}, url = "/ud/updata/")
public class ALCUploadParam implements ParamEntity {
    private static final String MODE = "alc";
    public String content;
    public int environment = (NetworkReachability.b() ^ true ? 1 : 0);
    public String md5;
    public String mode = "alc";
    public long uploadTime = System.currentTimeMillis();

    public ALCUploadParam(String str) {
        this.content = str;
        this.md5 = agy.a(str);
    }
}
