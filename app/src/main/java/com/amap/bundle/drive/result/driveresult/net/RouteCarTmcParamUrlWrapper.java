package com.amap.bundle.drive.result.driveresult.net;

import com.amap.bundle.drivecommon.request.RouteCarParamUrlWrapper;
import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.CombineParam;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.amap.bundle.network.request.param.builder.URLBuilder.SpecialParam;

@Path(builder = AosURLBuilder.class, combine = {@CombineParam(key = "nav_home", value = {"toX", "toY", "end_poiid", "end_types", "invoker", "end_typecode", "end_name", "end_parentid", "end_parentrel", "end_floor", "end_poi_angle"}), @CombineParam(key = "nav_company", special = {@SpecialParam(field = "toXEx", name = "toX"), @SpecialParam(field = "toYEx", name = "toY"), @SpecialParam(field = "end_poiidEx", name = "end_poiid"), @SpecialParam(field = "end_typesEx", name = "end_types"), @SpecialParam(field = "invokerEx", name = "invoker"), @SpecialParam(field = "end_typecodeEx", name = "end_typecode"), @SpecialParam(field = "end_nameEx", name = "end_name"), @SpecialParam(field = "end_parentidEx", name = "end_parentid"), @SpecialParam(field = "end_parentrelEx", name = "end_parentrel"), @SpecialParam(field = "end_floorEx", name = "end_floor"), @SpecialParam(field = "end_poi_angleEx", name = "end_poi_angle")})}, host = "drive_aos_url", sign = {"diu", "div"}, url = "ws/shield/dsp/app/route/navigation/?")
public class RouteCarTmcParamUrlWrapper extends RouteCarParamUrlWrapper {
    public int dsp_svrctl = 3;
    public String dsp_svrctl_in = "nav_home,nav_company";
    public String end_floorEx;
    public String end_nameEx;
    public String end_parentidEx;
    public String end_parentrelEx;
    public String end_poi_angleEx;
    public String end_poiidEx;
    public String end_typecodeEx;
    public String end_typesEx;
    public String invokerEx;
    public String toXEx;
    public String toYEx;

    public RouteCarTmcParamUrlWrapper(RouteCarParamUrlWrapper routeCarParamUrlWrapper, RouteCarParamUrlWrapper routeCarParamUrlWrapper2) {
        this.off = routeCarParamUrlWrapper.off;
        this.fromX = routeCarParamUrlWrapper.fromX;
        this.fromY = routeCarParamUrlWrapper.fromY;
        this.toX = routeCarParamUrlWrapper.toX;
        this.toY = routeCarParamUrlWrapper.toY;
        this.policy2 = routeCarParamUrlWrapper.policy2;
        this.start_poiid = routeCarParamUrlWrapper.start_poiid;
        this.start_types = routeCarParamUrlWrapper.start_types;
        this.end_poiid = routeCarParamUrlWrapper.end_poiid;
        this.end_types = routeCarParamUrlWrapper.end_types;
        this.viapoints = routeCarParamUrlWrapper.viapoints;
        this.viapoint_types = routeCarParamUrlWrapper.viapoint_types;
        this.via_typecodes = routeCarParamUrlWrapper.via_typecodes;
        this.viapoint_poiids = routeCarParamUrlWrapper.viapoint_poiids;
        this.carplate = routeCarParamUrlWrapper.carplate;
        this.usepoiquery = routeCarParamUrlWrapper.usepoiquery;
        this.output = routeCarParamUrlWrapper.output;
        this.sdk_version = routeCarParamUrlWrapper.sdk_version;
        this.angle = routeCarParamUrlWrapper.angle;
        this.credibility = routeCarParamUrlWrapper.credibility;
        this.invoker = routeCarParamUrlWrapper.invoker;
        this.start_typecode = routeCarParamUrlWrapper.start_typecode;
        this.end_typecode = routeCarParamUrlWrapper.end_typecode;
        this.contentoptions = routeCarParamUrlWrapper.contentoptions;
        this.sloc_precision = routeCarParamUrlWrapper.sloc_precision;
        this.sloc_speed = routeCarParamUrlWrapper.sloc_speed;
        this.route_version = routeCarParamUrlWrapper.route_version;
        this.sigshelter = routeCarParamUrlWrapper.sigshelter;
        this.threeD = routeCarParamUrlWrapper.threeD;
        this.v_type = routeCarParamUrlWrapper.v_type;
        this.v_height = routeCarParamUrlWrapper.v_height;
        this.v_weight = routeCarParamUrlWrapper.v_weight;
        this.v_width = routeCarParamUrlWrapper.v_width;
        this.v_load = routeCarParamUrlWrapper.v_load;
        this.v_size = routeCarParamUrlWrapper.v_size;
        this.v_length = routeCarParamUrlWrapper.v_length;
        this.v_axis = routeCarParamUrlWrapper.v_axis;
        this.cc = routeCarParamUrlWrapper.cc;
        this.refresh = routeCarParamUrlWrapper.refresh;
        this.playstyle = routeCarParamUrlWrapper.playstyle;
        this.soundtype = routeCarParamUrlWrapper.soundtype;
        this.end_name = routeCarParamUrlWrapper.end_name;
        this.end_parentid = routeCarParamUrlWrapper.end_parentid;
        this.end_parentrel = routeCarParamUrlWrapper.end_parentrel;
        this.end_floor = routeCarParamUrlWrapper.end_floor;
        this.end_poi_angle = routeCarParamUrlWrapper.end_poi_angle;
        this.toXEx = routeCarParamUrlWrapper2.toX;
        this.toYEx = routeCarParamUrlWrapper2.toY;
        this.end_poiidEx = routeCarParamUrlWrapper2.end_poiid;
        this.end_typesEx = routeCarParamUrlWrapper2.end_types;
        this.invokerEx = routeCarParamUrlWrapper2.invoker;
        this.end_typecodeEx = routeCarParamUrlWrapper2.end_typecode;
        this.end_nameEx = routeCarParamUrlWrapper2.end_name;
        this.end_parentidEx = routeCarParamUrlWrapper2.end_parentid;
        this.end_parentrelEx = routeCarParamUrlWrapper2.end_parentrel;
        this.end_floorEx = routeCarParamUrlWrapper2.end_floor;
        this.end_poi_angleEx = routeCarParamUrlWrapper2.end_poi_angle;
    }
}
