package com.autonavi.minimap.ajx3.upgrade.debugupload;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.amap.bundle.network.util.NetworkReachability;

@Path(builder = AosURLBuilder.class, host = "lotuspool_upload_url", sign = {"diu", "div"}, url = "/ud/updata")
public class DebugAjxCrashUploadParam implements ParamEntity {
    private static final String AJX3_MODE = "ajx3";
    public String content;
    public String environment;
    public String md5;
    public String mode;
    public String uploadTime;

    public DebugAjxCrashUploadParam() {
        this.mode = AJX3_MODE;
        this.uploadTime = String.valueOf(System.currentTimeMillis());
        this.environment = NetworkReachability.a() ? "0" : "1";
    }

    public DebugAjxCrashUploadParam(String str) {
        this();
        this.content = str;
        this.md5 = agy.a(str);
    }
}
