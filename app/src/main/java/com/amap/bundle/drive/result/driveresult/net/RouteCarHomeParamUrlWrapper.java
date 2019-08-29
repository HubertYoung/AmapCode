package com.amap.bundle.drive.result.driveresult.net;

import com.amap.bundle.drivecommon.request.RouteCarParamUrlWrapper;
import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.CombineParam;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, combine = {@CombineParam(key = "nav_home", value = {"toX", "toY", "end_poiid", "end_types", "invoker", "end_typecode", "end_name", "end_parentid", "end_parentrel", "end_floor", "end_poi_angle"})}, host = "drive_aos_url", sign = {"diu", "div"}, url = "ws/shield/dsp/app/route/navigation?")
public class RouteCarHomeParamUrlWrapper extends RouteCarParamUrlWrapper {
    public int dsp_svrctl = 1;
    public String dsp_svrctl_in = "nav_home";

    public RouteCarHomeParamUrlWrapper(RouteCarParamUrlWrapper routeCarParamUrlWrapper) {
        super(routeCarParamUrlWrapper);
    }
}
