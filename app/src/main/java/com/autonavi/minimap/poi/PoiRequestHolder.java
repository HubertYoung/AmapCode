package com.autonavi.minimap.poi;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.poi.param.BusLiteRequest;
import com.autonavi.minimap.poi.param.BusRequest;
import com.autonavi.minimap.poi.param.CodePointRequest;
import com.autonavi.minimap.poi.param.DetailMpsRequest;
import com.autonavi.minimap.poi.param.IllegalparkingRequest;
import com.autonavi.minimap.poi.param.InfoRequest;
import com.autonavi.minimap.poi.param.LineaRoundRequest;
import com.autonavi.minimap.poi.param.NaviinfoRequest;
import com.autonavi.minimap.poi.param.NewBusRequest;
import com.autonavi.minimap.poi.param.TipsLiteRequest;
import com.autonavi.minimap.poi.param.VoiceSearchRequest;
import com.autonavi.sdk.location.LocationInstrument;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class PoiRequestHolder {
    private static volatile PoiRequestHolder instance;

    private PoiRequestHolder() {
    }

    public static PoiRequestHolder getInstance() {
        if (instance == null) {
            synchronized (PoiRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new PoiRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendBus(BusRequest busRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendBus(busRequest, new dkn(), aosResponseCallback);
    }

    public void sendBusLite(BusLiteRequest busLiteRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendBusLite(busLiteRequest, new dkn(), aosResponseCallback);
    }

    public void sendCodePoint(CodePointRequest codePointRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendCodePoint(codePointRequest, new dkn(), aosResponseCallback);
    }

    public void sendDetailMps(DetailMpsRequest detailMpsRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendDetailMps(detailMpsRequest, new dkn(), aosResponseCallback);
    }

    public void sendIllegalparking(IllegalparkingRequest illegalparkingRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendIllegalparking(illegalparkingRequest, new dkn(), aosResponseCallback);
    }

    public void sendInfo(InfoRequest infoRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendInfo(infoRequest, new dkn(), aosResponseCallback);
    }

    public void sendLineaRound(LineaRoundRequest lineaRoundRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendLineaRound(lineaRoundRequest, new dkn(), aosResponseCallback);
    }

    public void sendNaviinfo(NaviinfoRequest naviinfoRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendNaviinfo(naviinfoRequest, new dkn(), aosResponseCallback);
    }

    public void sendNewBus(NewBusRequest newBusRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendNewBus(newBusRequest, new dkn(), aosResponseCallback);
    }

    public void sendTipsLite(TipsLiteRequest tipsLiteRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendTipsLite(tipsLiteRequest, new dkn(), aosResponseCallback);
    }

    public void sendVoiceSearch(VoiceSearchRequest voiceSearchRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendVoiceSearch(voiceSearchRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendBus(BusRequest busRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            busRequest.addHeaders(dkn.d);
            busRequest.setTimeout(dkn.b);
            busRequest.setRetryTimes(dkn.c);
        }
        busRequest.a();
        if (dkn != null) {
            in.a().a((AosRequest) busRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) busRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendBusLite(BusLiteRequest busLiteRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            busLiteRequest.addHeaders(dkn.d);
            busLiteRequest.setTimeout(dkn.b);
            busLiteRequest.setRetryTimes(dkn.c);
        }
        busLiteRequest.a();
        if (dkn != null) {
            in.a().a((AosRequest) busLiteRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) busLiteRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendCodePoint(CodePointRequest codePointRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            codePointRequest.addHeaders(dkn.d);
            codePointRequest.setTimeout(dkn.b);
            codePointRequest.setRetryTimes(dkn.c);
        }
        codePointRequest.setUrl(CodePointRequest.a);
        codePointRequest.addSignParam("channel");
        codePointRequest.addSignParam("keywords");
        codePointRequest.addSignParam("category");
        codePointRequest.addSignParam("blocks");
        codePointRequest.addReqParam("blocks", codePointRequest.b);
        codePointRequest.addReqParam("category", codePointRequest.c);
        codePointRequest.addReqParam("maxnum_limit", codePointRequest.d);
        codePointRequest.addReqParam("slayer_brand", codePointRequest.e);
        codePointRequest.addReqParam("keywords", codePointRequest.f);
        if (dkn != null) {
            in.a().a((AosRequest) codePointRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) codePointRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendDetailMps(DetailMpsRequest detailMpsRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            detailMpsRequest.addHeaders(dkn.d);
            detailMpsRequest.setTimeout(dkn.b);
            detailMpsRequest.setRetryTimes(dkn.c);
        }
        detailMpsRequest.setUrl(DetailMpsRequest.a);
        detailMpsRequest.addSignParam("channel");
        detailMpsRequest.addSignParam("id");
        detailMpsRequest.addSignParam("name");
        detailMpsRequest.addSignParam("longitude");
        detailMpsRequest.addSignParam("latitude");
        detailMpsRequest.addReqParam("id", detailMpsRequest.b);
        detailMpsRequest.addReqParam("id_flag", detailMpsRequest.c);
        detailMpsRequest.addReqParam("need_utd", detailMpsRequest.d);
        detailMpsRequest.addReqParam("cluster_state", detailMpsRequest.e);
        detailMpsRequest.addReqParam("utd_sceneid", detailMpsRequest.f);
        detailMpsRequest.addReqParam(LocationParams.PARA_COMMON_DIV, detailMpsRequest.g);
        detailMpsRequest.addReqParam("user_loc", detailMpsRequest.h);
        detailMpsRequest.addReqParam("city", detailMpsRequest.i);
        detailMpsRequest.addReqParam("geoobj", detailMpsRequest.j);
        detailMpsRequest.addReqParam("name", detailMpsRequest.k);
        detailMpsRequest.addReqParam("longitude", detailMpsRequest.l);
        detailMpsRequest.addReqParam("latitude", detailMpsRequest.m);
        detailMpsRequest.addReqParam("version", detailMpsRequest.n);
        detailMpsRequest.addReqParam("superid", detailMpsRequest.o);
        if (dkn != null) {
            in.a().a((AosRequest) detailMpsRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) detailMpsRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendIllegalparking(IllegalparkingRequest illegalparkingRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            illegalparkingRequest.addHeaders(dkn.d);
            illegalparkingRequest.setTimeout(dkn.b);
            illegalparkingRequest.setRetryTimes(dkn.c);
        }
        illegalparkingRequest.setUrl(IllegalparkingRequest.a);
        illegalparkingRequest.addSignParam("channel");
        illegalparkingRequest.addSignParam("longitude");
        illegalparkingRequest.addSignParam("latitude");
        illegalparkingRequest.addReqParam("longitude", illegalparkingRequest.b);
        illegalparkingRequest.addReqParam("latitude", illegalparkingRequest.c);
        illegalparkingRequest.addReqParam(AutoJsonUtils.JSON_ADCODE, illegalparkingRequest.d);
        illegalparkingRequest.addReqParam("category", illegalparkingRequest.e);
        illegalparkingRequest.addReqParam("range", illegalparkingRequest.f);
        if (dkn != null) {
            in.a().a((AosRequest) illegalparkingRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) illegalparkingRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendInfo(InfoRequest infoRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            infoRequest.addHeaders(dkn.d);
            infoRequest.setTimeout(dkn.b);
            infoRequest.setRetryTimes(dkn.c);
        }
        infoRequest.setUrl(InfoRequest.a);
        infoRequest.addSignParam("channel");
        infoRequest.addSignParam("id");
        infoRequest.addReqParam("query_type", infoRequest.b);
        infoRequest.addReqParam("id", infoRequest.c);
        infoRequest.addReqParam("data_type", infoRequest.d);
        infoRequest.addReqParam("sugpoiname", infoRequest.e);
        infoRequest.addReqParam("sugadcode", infoRequest.f);
        infoRequest.addReqParam("cmspoi", infoRequest.g);
        infoRequest.addReqParam("search_operate", infoRequest.h);
        infoRequest.addReqParam("version", infoRequest.i);
        if (dkn != null) {
            in.a().a((AosRequest) infoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) infoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendLineaRound(LineaRoundRequest lineaRoundRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            lineaRoundRequest.addHeaders(dkn.d);
            lineaRoundRequest.setTimeout(dkn.b);
            lineaRoundRequest.setRetryTimes(dkn.c);
        }
        lineaRoundRequest.setUrl(LineaRoundRequest.a);
        lineaRoundRequest.addSignParam("channel");
        lineaRoundRequest.addSignParam("category");
        lineaRoundRequest.addSignParam("geoline");
        lineaRoundRequest.addReqParam("category", lineaRoundRequest.b);
        lineaRoundRequest.addReqParam("geoline", lineaRoundRequest.c);
        lineaRoundRequest.addReqParam("need_gasprice", lineaRoundRequest.d);
        lineaRoundRequest.addReqParam("need_naviinfo", lineaRoundRequest.e);
        lineaRoundRequest.addReqParam("need_eta", lineaRoundRequest.f);
        lineaRoundRequest.addReqParam("routepoints", lineaRoundRequest.g);
        lineaRoundRequest.addReqParam("eta_type", lineaRoundRequest.h);
        lineaRoundRequest.addReqParam("eta_flag", lineaRoundRequest.i);
        lineaRoundRequest.addReqParam("route_range", lineaRoundRequest.j);
        lineaRoundRequest.addReqParam("custom", lineaRoundRequest.k);
        lineaRoundRequest.addReqParam("custom_and", lineaRoundRequest.l);
        lineaRoundRequest.addReqParam("superid", lineaRoundRequest.m);
        if (dkn != null) {
            in.a().a((AosRequest) lineaRoundRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) lineaRoundRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendNaviinfo(NaviinfoRequest naviinfoRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            naviinfoRequest.addHeaders(dkn.d);
            naviinfoRequest.setTimeout(dkn.b);
            naviinfoRequest.setRetryTimes(dkn.c);
        }
        naviinfoRequest.setUrl(NaviinfoRequest.a);
        naviinfoRequest.addSignParam("channel");
        naviinfoRequest.addSignParam(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
        naviinfoRequest.addReqParam(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, naviinfoRequest.b);
        naviinfoRequest.addReqParam("rel_type", naviinfoRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) naviinfoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) naviinfoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendNewBus(NewBusRequest newBusRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            newBusRequest.addHeaders(dkn.d);
            newBusRequest.setTimeout(dkn.b);
            newBusRequest.setRetryTimes(dkn.c);
        }
        newBusRequest.a();
        if (dkn != null) {
            in.a().a((AosRequest) newBusRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) newBusRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendTipsLite(TipsLiteRequest tipsLiteRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            tipsLiteRequest.addHeaders(dkn.d);
            tipsLiteRequest.setTimeout(dkn.b);
            tipsLiteRequest.setRetryTimes(dkn.c);
        }
        tipsLiteRequest.setUrl(TipsLiteRequest.a);
        tipsLiteRequest.addSignParam("channel");
        tipsLiteRequest.addSignParam("city");
        tipsLiteRequest.addSignParam("words");
        tipsLiteRequest.addReqParam("words", tipsLiteRequest.b);
        tipsLiteRequest.addReqParam("city", tipsLiteRequest.c);
        tipsLiteRequest.addReqParam("longitude", tipsLiteRequest.d);
        tipsLiteRequest.addReqParam("latitude", tipsLiteRequest.e);
        tipsLiteRequest.addReqParam("category", tipsLiteRequest.f);
        tipsLiteRequest.addReqParam("datatype", tipsLiteRequest.g);
        tipsLiteRequest.addReqParam(AutoJsonUtils.JSON_ADCODE, tipsLiteRequest.h);
        tipsLiteRequest.addReqParam("user_loc", tipsLiteRequest.i);
        tipsLiteRequest.addReqParam("geoobj", tipsLiteRequest.j);
        tipsLiteRequest.addReqParam("session", tipsLiteRequest.k);
        tipsLiteRequest.addReqParam("stepid", tipsLiteRequest.l);
        tipsLiteRequest.addReqParam("user_city", tipsLiteRequest.m);
        tipsLiteRequest.addReqParam("need_virtualtip", tipsLiteRequest.n);
        tipsLiteRequest.addReqParam("need_xy", tipsLiteRequest.o);
        if (dkn != null) {
            in.a().a((AosRequest) tipsLiteRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) tipsLiteRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendVoiceSearch(VoiceSearchRequest voiceSearchRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            voiceSearchRequest.addHeaders(dkn.d);
            voiceSearchRequest.setTimeout(dkn.b);
            voiceSearchRequest.setRetryTimes(dkn.c);
        }
        voiceSearchRequest.setUrl(VoiceSearchRequest.a);
        voiceSearchRequest.addSignParam("channel");
        voiceSearchRequest.addSignParam("id");
        voiceSearchRequest.addSignParam("longitude");
        voiceSearchRequest.addSignParam("latitude");
        voiceSearchRequest.addSignParam("keywords");
        voiceSearchRequest.addSignParam("geoobj");
        voiceSearchRequest.addReqParam("keywords", voiceSearchRequest.b);
        voiceSearchRequest.addReqParam("original_keywords", voiceSearchRequest.c);
        voiceSearchRequest.addReqParam("query_type", voiceSearchRequest.d);
        voiceSearchRequest.addReqParam("pagesize", Integer.toString(voiceSearchRequest.e));
        voiceSearchRequest.addReqParam("pagenum", Integer.toString(voiceSearchRequest.f));
        voiceSearchRequest.addReqParam("city", voiceSearchRequest.g);
        voiceSearchRequest.addReqParam("geoobj", voiceSearchRequest.h);
        voiceSearchRequest.addReqParam("utd_sceneid", voiceSearchRequest.i);
        voiceSearchRequest.addReqParam("pcluster_state", Integer.toString(voiceSearchRequest.j));
        voiceSearchRequest.addReqParam("need_utd", voiceSearchRequest.k);
        voiceSearchRequest.addReqParam("user_loc", voiceSearchRequest.l);
        voiceSearchRequest.addReqParam("user_city", voiceSearchRequest.m);
        voiceSearchRequest.addReqParam("pnput_method", voiceSearchRequest.n);
        voiceSearchRequest.addReqParam("driving", voiceSearchRequest.o ? "true" : "false");
        voiceSearchRequest.addReqParam("direct_jump", voiceSearchRequest.p ? "true" : "false");
        voiceSearchRequest.addReqParam("version", voiceSearchRequest.q);
        voiceSearchRequest.addReqParam("is_classify", voiceSearchRequest.r ? "true" : "false");
        voiceSearchRequest.addReqParam("search_operate", Integer.toString(voiceSearchRequest.s));
        voiceSearchRequest.addReqParam("citysuggestion", voiceSearchRequest.t ? "true" : "false");
        voiceSearchRequest.addReqParam("need_magicbox", voiceSearchRequest.u ? "true" : "false");
        voiceSearchRequest.addReqParam("need_parkinfo", voiceSearchRequest.v ? "true" : "false");
        voiceSearchRequest.addReqParam("addr_poi_merge", voiceSearchRequest.w ? "true" : "false");
        voiceSearchRequest.addReqParam("superid", voiceSearchRequest.x);
        voiceSearchRequest.addReqParam("need_recommend", voiceSearchRequest.y);
        voiceSearchRequest.addReqParam("need_locate", voiceSearchRequest.z ? "true" : "false");
        voiceSearchRequest.addReqParam("need_codepoint", voiceSearchRequest.A ? "true" : "false");
        voiceSearchRequest.addReqParam("need_navidata", voiceSearchRequest.B ? "true" : "false");
        voiceSearchRequest.addReqParam("prot_ver", voiceSearchRequest.C);
        voiceSearchRequest.addReqParam("transfer_session_id", voiceSearchRequest.D);
        voiceSearchRequest.addReqParam("transfer_select_pos", voiceSearchRequest.E);
        voiceSearchRequest.addReqParam("transfer_selected_start_id", voiceSearchRequest.F);
        voiceSearchRequest.addReqParam("transfer_selected_start_name", voiceSearchRequest.G);
        voiceSearchRequest.addReqParam("interior_scene", voiceSearchRequest.H);
        voiceSearchRequest.addReqParam("interior_poi", voiceSearchRequest.I);
        voiceSearchRequest.addReqParam("interior_floor", voiceSearchRequest.J);
        voiceSearchRequest.addReqParam("longitude", voiceSearchRequest.K);
        voiceSearchRequest.addReqParam("latitude", voiceSearchRequest.L);
        voiceSearchRequest.addReqParam("hid", voiceSearchRequest.M);
        voiceSearchRequest.addReqParam("sound_type", voiceSearchRequest.N);
        voiceSearchRequest.addReqParam("user_action", voiceSearchRequest.O);
        if (dkn != null) {
            in.a().a((AosRequest) voiceSearchRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) voiceSearchRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
