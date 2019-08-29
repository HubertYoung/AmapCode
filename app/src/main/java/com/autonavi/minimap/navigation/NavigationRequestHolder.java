package com.autonavi.minimap.navigation;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.navigation.param.AddressRequest;
import com.autonavi.minimap.navigation.param.AutoErouteRequest;
import com.autonavi.minimap.navigation.param.BusAlterLineRequest;
import com.autonavi.minimap.navigation.param.EtaRouteRequest;
import com.autonavi.minimap.navigation.param.FootRankRequest;
import com.autonavi.minimap.navigation.param.HomeCompanyRequest;
import com.autonavi.minimap.navigation.param.NewFootRequest;
import com.autonavi.minimap.navigation.param.RestrictedAreaRequest;
import com.autonavi.minimap.navigation.param.RouteCarRequest;
import com.autonavi.server.aos.serverkey;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.uc.webview.export.internal.interfaces.IWaStat;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class NavigationRequestHolder {
    private static volatile NavigationRequestHolder instance;

    private NavigationRequestHolder() {
    }

    public static NavigationRequestHolder getInstance() {
        if (instance == null) {
            synchronized (NavigationRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new NavigationRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendAddress(AddressRequest addressRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendAddress(addressRequest, new dkn(), aosResponseCallback);
    }

    public void sendAuto(RouteCarRequest routeCarRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendAuto(routeCarRequest, new dkn(), aosResponseCallback);
    }

    public void sendRestrictedArea(RestrictedAreaRequest restrictedAreaRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendRestrictedArea(restrictedAreaRequest, new dkn(), aosResponseCallback);
    }

    public void sendBusAlterLine(BusAlterLineRequest busAlterLineRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendBusAlterLine(busAlterLineRequest, new dkn(), aosResponseCallback);
    }

    public void sendEtaRoute(EtaRouteRequest etaRouteRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendEtaRoute(etaRouteRequest, new dkn(), aosResponseCallback);
    }

    public void sendFootRank(FootRankRequest footRankRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendFootRank(footRankRequest, new dkn(), aosResponseCallback);
    }

    public void sendHomeCompany(HomeCompanyRequest homeCompanyRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendHomeCompany(homeCompanyRequest, new dkn(), aosResponseCallback);
    }

    public void sendNewFoot(NewFootRequest newFootRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendNewFoot(newFootRequest, new dkn(), aosResponseCallback);
    }

    public void sendAutoEroute(AutoErouteRequest autoErouteRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendAutoEroute(autoErouteRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendAddress(AddressRequest addressRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            addressRequest.addHeaders(dkn.d);
            addressRequest.setTimeout(dkn.b);
            addressRequest.setRetryTimes(dkn.c);
        }
        addressRequest.setUrl(AddressRequest.a);
        addressRequest.addSignParam("channel");
        addressRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        addressRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        addressRequest.addReqParam(LocationParams.PARA_COMMON_DIU, addressRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) addressRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) addressRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendAuto(RouteCarRequest routeCarRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            routeCarRequest.addHeaders(dkn.d);
            routeCarRequest.setTimeout(dkn.b);
            routeCarRequest.setRetryTimes(dkn.c);
        }
        routeCarRequest.setUrl(RouteCarRequest.a);
        routeCarRequest.addSignParam("channel");
        routeCarRequest.addSignParam("fromX");
        routeCarRequest.addSignParam("fromY");
        routeCarRequest.addSignParam("toX");
        routeCarRequest.addSignParam("toY");
        routeCarRequest.addReqParam(CameraParams.FLASH_MODE_OFF, Integer.toString(routeCarRequest.b));
        routeCarRequest.addReqParam("fromX", routeCarRequest.c);
        routeCarRequest.addReqParam("fromY", routeCarRequest.d);
        routeCarRequest.addReqParam("toX", routeCarRequest.e);
        routeCarRequest.addReqParam("toY", routeCarRequest.f);
        routeCarRequest.addReqParam("policy2", routeCarRequest.g);
        routeCarRequest.addReqParam("start_poiid", routeCarRequest.h);
        routeCarRequest.addReqParam("start_types", routeCarRequest.i);
        routeCarRequest.addReqParam("end_poiid", routeCarRequest.j);
        routeCarRequest.addReqParam("end_types", routeCarRequest.k);
        routeCarRequest.addReqParam("viapoints", routeCarRequest.l);
        routeCarRequest.addReqParam("viapoint_types", routeCarRequest.m);
        routeCarRequest.addReqParam("via_typecodes", routeCarRequest.n);
        routeCarRequest.addReqParam("viapoint_poiids", routeCarRequest.o);
        routeCarRequest.addReqParam("carplate", routeCarRequest.p);
        routeCarRequest.addReqParam(IWaStat.KEY_CHECK_COMPRESS, routeCarRequest.q);
        routeCarRequest.addReqParam("use_truck_engine", Integer.toString(routeCarRequest.r));
        routeCarRequest.addReqParam("usepoiquery", routeCarRequest.s);
        routeCarRequest.addReqParam(H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, routeCarRequest.t);
        routeCarRequest.addReqParam("dk_version", routeCarRequest.u);
        routeCarRequest.addReqParam("angle", routeCarRequest.v);
        routeCarRequest.addReqParam("credibility", routeCarRequest.w);
        routeCarRequest.addReqParam("invoker", routeCarRequest.x);
        routeCarRequest.addReqParam("start_typecode", routeCarRequest.y);
        routeCarRequest.addReqParam("end_typecode", routeCarRequest.z);
        routeCarRequest.addReqParam("contentoptions", routeCarRequest.A);
        routeCarRequest.addReqParam("sloc_precision", routeCarRequest.B);
        routeCarRequest.addReqParam("sloc_speed", Double.toString(routeCarRequest.C));
        routeCarRequest.addReqParam("route_version", routeCarRequest.D);
        routeCarRequest.addReqParam("sigshelter", routeCarRequest.E);
        routeCarRequest.addReqParam("threeD", Integer.toString(routeCarRequest.F));
        routeCarRequest.addReqParam("v_type", Integer.toString(routeCarRequest.G));
        routeCarRequest.addReqParam("v_height", Double.toString(routeCarRequest.H));
        routeCarRequest.addReqParam("v_load", Double.toString(routeCarRequest.I));
        routeCarRequest.addReqParam("v_weight", Double.toString(routeCarRequest.J));
        routeCarRequest.addReqParam("v_width", Double.toString(routeCarRequest.K));
        routeCarRequest.addReqParam("v_length", Double.toString(routeCarRequest.L));
        routeCarRequest.addReqParam("v_size", routeCarRequest.M);
        routeCarRequest.addReqParam("v_axis", routeCarRequest.N);
        routeCarRequest.addReqParam("refresh", Integer.toString(routeCarRequest.O));
        routeCarRequest.addReqParam("playstyle", routeCarRequest.P);
        routeCarRequest.addReqParam("soundtype", routeCarRequest.Q);
        routeCarRequest.addReqParam("end_name", routeCarRequest.R);
        routeCarRequest.addReqParam("superid", routeCarRequest.S);
        routeCarRequest.addReqParam("ngle_type", routeCarRequest.T);
        routeCarRequest.addReqParam("angle_gps", routeCarRequest.U);
        routeCarRequest.addReqParam("angle_comp", routeCarRequest.V);
        routeCarRequest.addReqParam("end_parentid", routeCarRequest.W);
        routeCarRequest.addReqParam("end_parentrel", routeCarRequest.X);
        routeCarRequest.addReqParam("end_floor", routeCarRequest.Y);
        routeCarRequest.addReqParam("end_poi_angle", routeCarRequest.Z);
        routeCarRequest.addReqParam("frompage", routeCarRequest.aa);
        routeCarRequest.addReqParam("via_names", routeCarRequest.ab);
        routeCarRequest.addReqParam("angle_fittingdir", routeCarRequest.ac);
        routeCarRequest.addReqParam("angle_matchingdir", routeCarRequest.ad);
        routeCarRequest.addReqParam("angle_radius", routeCarRequest.ae);
        routeCarRequest.addReqParam("angle_sigtype", routeCarRequest.af);
        routeCarRequest.addReqParam("gps_cre", routeCarRequest.ag);
        routeCarRequest.addReqParam("fitting_cre", routeCarRequest.ah);
        routeCarRequest.addReqParam("history_points", routeCarRequest.ai);
        routeCarRequest.addReqParam("end_poi_extension", routeCarRequest.aj);
        routeCarRequest.addReqParam("route_mode", routeCarRequest.ak);
        routeCarRequest.addReqParam("angle_type", routeCarRequest.al);
        if (dkn != null) {
            in.a().a((AosRequest) routeCarRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) routeCarRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendRestrictedArea(RestrictedAreaRequest restrictedAreaRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            restrictedAreaRequest.addHeaders(dkn.d);
            restrictedAreaRequest.setTimeout(dkn.b);
            restrictedAreaRequest.setRetryTimes(dkn.c);
        }
        restrictedAreaRequest.setUrl(RestrictedAreaRequest.a);
        restrictedAreaRequest.addSignParam("channel");
        restrictedAreaRequest.addSignParam("restrict_type");
        restrictedAreaRequest.addSignParam("plate");
        restrictedAreaRequest.addReqParam("restrict_type", Integer.toString(restrictedAreaRequest.b));
        restrictedAreaRequest.addReqParam("plate", restrictedAreaRequest.c);
        restrictedAreaRequest.addReqParam("beijingcard", Integer.toString(restrictedAreaRequest.d));
        restrictedAreaRequest.addReqParam("vehicle_type", Integer.toString(restrictedAreaRequest.e));
        restrictedAreaRequest.addReqParam("truck_type", restrictedAreaRequest.f);
        restrictedAreaRequest.addReqParam("truck_length", restrictedAreaRequest.g);
        restrictedAreaRequest.addReqParam("truck_width", restrictedAreaRequest.h);
        restrictedAreaRequest.addReqParam("truck_height", Double.toString(restrictedAreaRequest.i));
        restrictedAreaRequest.addReqParam("truck_axis", restrictedAreaRequest.j);
        restrictedAreaRequest.addReqParam("truck_load", Double.toString(restrictedAreaRequest.k));
        restrictedAreaRequest.addReqParam("truck_ratifyload", restrictedAreaRequest.l);
        restrictedAreaRequest.addReqParam("truck_label", restrictedAreaRequest.m);
        restrictedAreaRequest.addReqParam("range", restrictedAreaRequest.n);
        restrictedAreaRequest.addReqParam("adcodes", restrictedAreaRequest.o);
        restrictedAreaRequest.addReqParam("restrict_point", restrictedAreaRequest.p);
        restrictedAreaRequest.addReqParam("nocoor", restrictedAreaRequest.q);
        restrictedAreaRequest.addReqParam("startroad", Long.toString(restrictedAreaRequest.r));
        restrictedAreaRequest.addReqParam("endroad", Long.toString(restrictedAreaRequest.s));
        restrictedAreaRequest.addReqParam("via_points", restrictedAreaRequest.t);
        restrictedAreaRequest.addReqParam("via_typecodes", restrictedAreaRequest.u);
        restrictedAreaRequest.addReqParam("ruleids", restrictedAreaRequest.v);
        if (dkn != null) {
            in.a().a((AosRequest) restrictedAreaRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) restrictedAreaRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendBusAlterLine(BusAlterLineRequest busAlterLineRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            busAlterLineRequest.addHeaders(dkn.d);
            busAlterLineRequest.setTimeout(dkn.b);
            busAlterLineRequest.setRetryTimes(dkn.c);
        }
        busAlterLineRequest.setUrl(BusAlterLineRequest.a);
        busAlterLineRequest.addSignParam("channel");
        busAlterLineRequest.addSignParam("line");
        busAlterLineRequest.addSignParam("sstid");
        busAlterLineRequest.addSignParam("tstid");
        busAlterLineRequest.addReqParam("line", busAlterLineRequest.b);
        busAlterLineRequest.addReqParam("sstid", busAlterLineRequest.c);
        busAlterLineRequest.addReqParam("tstid", busAlterLineRequest.d);
        busAlterLineRequest.addReqParam("areacode", busAlterLineRequest.e);
        busAlterLineRequest.addReqParam(LocationParams.PARA_FLP_AUTONAVI_LON, busAlterLineRequest.f);
        busAlterLineRequest.addReqParam("lat", busAlterLineRequest.g);
        busAlterLineRequest.addReqParam("date", busAlterLineRequest.h);
        busAlterLineRequest.addReqParam("time", busAlterLineRequest.i);
        busAlterLineRequest.addReqParam("eta", busAlterLineRequest.j);
        if (dkn != null) {
            in.a().a((AosRequest) busAlterLineRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) busAlterLineRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendEtaRoute(EtaRouteRequest etaRouteRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            etaRouteRequest.addHeaders(dkn.d);
            etaRouteRequest.setTimeout(dkn.b);
            etaRouteRequest.setRetryTimes(dkn.c);
        }
        etaRouteRequest.setUrl(EtaRouteRequest.a);
        etaRouteRequest.addSignParam("channel");
        etaRouteRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        etaRouteRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        etaRouteRequest.addReqParam(LocationParams.PARA_COMMON_DIU, etaRouteRequest.b);
        etaRouteRequest.addReqParam(LocationParams.PARA_FLP_AUTONAVI_LON, etaRouteRequest.c);
        etaRouteRequest.addReqParam("lat", etaRouteRequest.d);
        etaRouteRequest.addReqParam(DictionaryKeys.CTRLXY_X, etaRouteRequest.e);
        etaRouteRequest.addReqParam(DictionaryKeys.CTRLXY_Y, etaRouteRequest.f);
        etaRouteRequest.addReqParam("policy2", etaRouteRequest.g);
        etaRouteRequest.addReqParam("multi_routes", etaRouteRequest.h);
        etaRouteRequest.addReqParam("ver", etaRouteRequest.i);
        etaRouteRequest.addReqParam("sdk_version", etaRouteRequest.j);
        if (dkn != null) {
            in.a().a((AosRequest) etaRouteRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) etaRouteRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendFootRank(FootRankRequest footRankRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            footRankRequest.addHeaders(dkn.d);
            footRankRequest.setTimeout(dkn.b);
            footRankRequest.setRetryTimes(dkn.c);
        }
        footRankRequest.setUrl(FootRankRequest.a);
        footRankRequest.addSignParam("channel");
        footRankRequest.addSignParam("ts");
        footRankRequest.addSignParam("distance");
        footRankRequest.addReqParam("ts", footRankRequest.b);
        footRankRequest.addReqParam("tid", footRankRequest.c);
        footRankRequest.addReqParam("durtion", footRankRequest.d);
        footRankRequest.addReqParam("distance", footRankRequest.e);
        footRankRequest.addReqParam("average_speed", footRankRequest.f);
        footRankRequest.addReqParam("calories", footRankRequest.g);
        footRankRequest.addReqParam("footsource", footRankRequest.h);
        if (dkn != null) {
            in.a().a((AosRequest) footRankRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) footRankRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendHomeCompany(HomeCompanyRequest homeCompanyRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            homeCompanyRequest.addHeaders(dkn.d);
            homeCompanyRequest.setTimeout(dkn.b);
            homeCompanyRequest.setRetryTimes(dkn.c);
        }
        homeCompanyRequest.setUrl(HomeCompanyRequest.a);
        homeCompanyRequest.addSignParam("channel");
        homeCompanyRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        homeCompanyRequest.addSignParam(LocationParams.PARA_FLP_AUTONAVI_LON);
        homeCompanyRequest.addSignParam("lat");
        homeCompanyRequest.addReqParam(LocationParams.PARA_COMMON_DIU, homeCompanyRequest.b);
        homeCompanyRequest.addReqParam(Oauth2AccessToken.KEY_UID, homeCompanyRequest.c);
        homeCompanyRequest.addReqParam(LocationParams.PARA_FLP_AUTONAVI_LON, homeCompanyRequest.d);
        homeCompanyRequest.addReqParam("lat", homeCompanyRequest.e);
        homeCompanyRequest.addReqParam("home_x", homeCompanyRequest.f);
        homeCompanyRequest.addReqParam("home_y", homeCompanyRequest.g);
        homeCompanyRequest.addReqParam("company_x", homeCompanyRequest.h);
        homeCompanyRequest.addReqParam("company_y", homeCompanyRequest.i);
        homeCompanyRequest.addReqParam("policy2", homeCompanyRequest.j);
        homeCompanyRequest.addReqParam("content_options", homeCompanyRequest.k);
        homeCompanyRequest.addReqParam("carplate", homeCompanyRequest.l);
        homeCompanyRequest.addReqParam("multi_routes", homeCompanyRequest.m);
        homeCompanyRequest.addReqParam("vehicle_type", homeCompanyRequest.n);
        homeCompanyRequest.addReqParam("vehichle_height", homeCompanyRequest.o);
        homeCompanyRequest.addReqParam("vehicle_load", homeCompanyRequest.p);
        homeCompanyRequest.addReqParam("sdk_version", homeCompanyRequest.q);
        if (dkn != null) {
            in.a().a((AosRequest) homeCompanyRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) homeCompanyRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendNewFoot(NewFootRequest newFootRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            newFootRequest.addHeaders(dkn.d);
            newFootRequest.setTimeout(dkn.b);
            newFootRequest.setRetryTimes(dkn.c);
        }
        newFootRequest.setUrl(NewFootRequest.a);
        newFootRequest.addSignParam("channel");
        newFootRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        newFootRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        if (dkn != null) {
            in.a().a((AosRequest) newFootRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) newFootRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendAutoEroute(AutoErouteRequest autoErouteRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            autoErouteRequest.addHeaders(dkn.d);
            autoErouteRequest.setTimeout(dkn.b);
            autoErouteRequest.setRetryTimes(dkn.c);
        }
        autoErouteRequest.setUrl(AutoErouteRequest.a);
        autoErouteRequest.addSignParam("channel");
        autoErouteRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        autoErouteRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        String amapEncodeV2 = serverkey.amapEncodeV2(autoErouteRequest.b);
        if (!TextUtils.isEmpty(amapEncodeV2)) {
            autoErouteRequest.setBody(amapEncodeV2.getBytes());
        }
        if (dkn != null) {
            in.a().a((AosRequest) autoErouteRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) autoErouteRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
