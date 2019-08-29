package com.autonavi.mine.feedbackv2.drivenavigationissues;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"tid", "dic", "dip", "diu", "div"}, url = "ws/shield/traffic/ubi-querypage?")
public class DrivingHistoryParam implements ParamEntity {
}
