package com.autonavi.minimap.drive.auto;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"dic", "div", "ver_info"}, url = "ws/app/conf/app_update/auto_telecontroller/?")
public class UrlWrapperAutoApkUpdateConfig implements ParamEntity {
    public String ver_info = "";
}
