package com.amap.bundle.cloudconfig.aocs.model;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"channel", "diu", "div", "_aosmd5"}, url = "ws/shield/frogserver/aocs/updatable/1?")
public class ConfigRequestParam implements ParamEntity {
    public static final String UPDATE_MODE_ALL = "1";
    public static final String UPDATE_MODE_SPEC = "2";
    @SuppressFBWarnings
    private String lat = "";
    @SuppressFBWarnings
    private String lon = "";
    @SuppressFBWarnings
    private String module;
    @SuppressFBWarnings
    private String update_mode;

    public ConfigRequestParam(String str, String str2, String str3, String str4) {
        this.update_mode = str;
        this.module = str2;
        this.lon = str3;
        this.lat = str4;
    }

    @SuppressFBWarnings
    public ConfigRequestParam(String str, String str2) {
        this.update_mode = str;
        this.module = str2;
    }
}
