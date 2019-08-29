package com.autonavi.minimap.basemap.save.net;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"div"}, url = "/ws/archive/favorites/navi_remind/")
public class SaveSyncAutoDataShowParam implements ParamEntity {
}
