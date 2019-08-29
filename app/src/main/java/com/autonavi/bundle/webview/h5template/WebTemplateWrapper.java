package com.autonavi.bundle.webview.h5template;

import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.SearchURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = SearchURLBuilder.class, sign = {"diu", "div"}, url = "ws/shield/h5/app/offline")
public final class WebTemplateWrapper implements ParamEntity {
    public String version;
}
