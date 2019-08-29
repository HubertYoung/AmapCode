package com.amap.bundle.dumpcrash;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.amap.bundle.network.util.NetworkReachability;

@Path(builder = AosURLBuilder.class, host = "lotuspool_upload_url", sign = {"diu", "div"}, url = "/ud/upDataAndFile?ent=2")
public class CrashUploadParam implements ParamEntity {
    private static final String MODE = "bestor";
    public String content;
    public int environment = (NetworkReachability.b() ^ true ? 1 : 0);
    public String md5;
    public String mode = MODE;
    public long uploadTime = System.currentTimeMillis();

    public CrashUploadParam(String str) {
        this.content = str;
        this.md5 = agy.a(str);
    }
}
