package com.autonavi.minimap.archive;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogUnAvailbleItem;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
import com.autonavi.minimap.archive.param.FavoritesNaviRemindRequest;
import com.autonavi.minimap.archive.param.TrafficeventCommentRequest;
import com.autonavi.minimap.archive.param.TrafficeventDetailMoreRequest;
import com.autonavi.minimap.archive.param.TrafficeventRoadInfoRequest;
import com.autonavi.minimap.archive.param.TrafficeventUpdateRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.taobao.accs.common.Constants;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class ArchiveRequestHolder {
    private static volatile ArchiveRequestHolder instance;

    private ArchiveRequestHolder() {
    }

    public static ArchiveRequestHolder getInstance() {
        if (instance == null) {
            synchronized (ArchiveRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new ArchiveRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendFavoritesNaviRemind(FavoritesNaviRemindRequest favoritesNaviRemindRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendFavoritesNaviRemind(favoritesNaviRemindRequest, new dkn(), aosResponseCallback);
    }

    public void sendTrafficeventComment(TrafficeventCommentRequest trafficeventCommentRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendTrafficeventComment(trafficeventCommentRequest, new dkn(), aosResponseCallback);
    }

    public void sendTrafficeventDetailMore(TrafficeventDetailMoreRequest trafficeventDetailMoreRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendTrafficeventDetailMore(trafficeventDetailMoreRequest, new dkn(), aosResponseCallback);
    }

    public void sendTrafficeventRoadInfo(TrafficeventRoadInfoRequest trafficeventRoadInfoRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendTrafficeventRoadInfo(trafficeventRoadInfoRequest, new dkn(), aosResponseCallback);
    }

    public void sendTrafficeventUpdate(TrafficeventUpdateRequest trafficeventUpdateRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendTrafficeventUpdate(trafficeventUpdateRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendFavoritesNaviRemind(FavoritesNaviRemindRequest favoritesNaviRemindRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            favoritesNaviRemindRequest.addHeaders(dkn.d);
            favoritesNaviRemindRequest.setTimeout(dkn.b);
            favoritesNaviRemindRequest.setRetryTimes(dkn.c);
        }
        favoritesNaviRemindRequest.setUrl(FavoritesNaviRemindRequest.a);
        favoritesNaviRemindRequest.addSignParam("channel");
        favoritesNaviRemindRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        favoritesNaviRemindRequest.addReqParam(LocationParams.PARA_COMMON_DIV, favoritesNaviRemindRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) favoritesNaviRemindRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) favoritesNaviRemindRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendTrafficeventComment(TrafficeventCommentRequest trafficeventCommentRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            trafficeventCommentRequest.addHeaders(dkn.d);
            trafficeventCommentRequest.setTimeout(dkn.b);
            trafficeventCommentRequest.setRetryTimes(dkn.c);
        }
        trafficeventCommentRequest.setUrl(TrafficeventCommentRequest.a);
        trafficeventCommentRequest.addSignParam("channel");
        trafficeventCommentRequest.addSignParam("type");
        trafficeventCommentRequest.addReqParam("type", trafficeventCommentRequest.b);
        trafficeventCommentRequest.addReqParam("id", trafficeventCommentRequest.c);
        trafficeventCommentRequest.addReqParam(DictionaryKeys.CTRLXY_X, trafficeventCommentRequest.d);
        trafficeventCommentRequest.addReqParam(DictionaryKeys.CTRLXY_Y, trafficeventCommentRequest.e);
        trafficeventCommentRequest.addReqParam("comment_from", Integer.toString(trafficeventCommentRequest.f));
        trafficeventCommentRequest.addReqParam("report_link", trafficeventCommentRequest.g);
        trafficeventCommentRequest.addReqParam("comment_cost", Integer.toString(trafficeventCommentRequest.h));
        trafficeventCommentRequest.addReqParam("navi_type", Integer.toString(trafficeventCommentRequest.i));
        if (dkn != null) {
            in.a().a((AosRequest) trafficeventCommentRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) trafficeventCommentRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendTrafficeventDetailMore(TrafficeventDetailMoreRequest trafficeventDetailMoreRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            trafficeventDetailMoreRequest.addHeaders(dkn.d);
            trafficeventDetailMoreRequest.setTimeout(dkn.b);
            trafficeventDetailMoreRequest.setRetryTimes(dkn.c);
        }
        trafficeventDetailMoreRequest.setUrl(TrafficeventDetailMoreRequest.a);
        trafficeventDetailMoreRequest.addSignParam("channel");
        trafficeventDetailMoreRequest.addSignParam("eventid");
        trafficeventDetailMoreRequest.addReqParam("eventid", trafficeventDetailMoreRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) trafficeventDetailMoreRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) trafficeventDetailMoreRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendTrafficeventRoadInfo(TrafficeventRoadInfoRequest trafficeventRoadInfoRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            trafficeventRoadInfoRequest.addHeaders(dkn.d);
            trafficeventRoadInfoRequest.setTimeout(dkn.b);
            trafficeventRoadInfoRequest.setRetryTimes(dkn.c);
        }
        trafficeventRoadInfoRequest.setUrl(TrafficeventRoadInfoRequest.a);
        trafficeventRoadInfoRequest.addSignParam("channel");
        trafficeventRoadInfoRequest.addSignParam(DictionaryKeys.CTRLXY_X);
        trafficeventRoadInfoRequest.addSignParam(DictionaryKeys.CTRLXY_Y);
        trafficeventRoadInfoRequest.addReqParam(DictionaryKeys.CTRLXY_X, trafficeventRoadInfoRequest.b);
        trafficeventRoadInfoRequest.addReqParam(DictionaryKeys.CTRLXY_Y, trafficeventRoadInfoRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) trafficeventRoadInfoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) trafficeventRoadInfoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendTrafficeventUpdate(TrafficeventUpdateRequest trafficeventUpdateRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            trafficeventUpdateRequest.addHeaders(dkn.d);
            trafficeventUpdateRequest.setTimeout(dkn.b);
            trafficeventUpdateRequest.setRetryTimes(dkn.c);
        }
        trafficeventUpdateRequest.setUrl(TrafficeventUpdateRequest.f);
        trafficeventUpdateRequest.addSignParam("channel");
        trafficeventUpdateRequest.addSignParam("longitude");
        trafficeventUpdateRequest.addSignParam("latitude");
        trafficeventUpdateRequest.addReqParam("appid", Integer.toString(trafficeventUpdateRequest.g));
        trafficeventUpdateRequest.addReqParam("action", Integer.toString(trafficeventUpdateRequest.h));
        trafficeventUpdateRequest.addReqParam("event_id", trafficeventUpdateRequest.i);
        trafficeventUpdateRequest.addReqParam("displayname", trafficeventUpdateRequest.j);
        trafficeventUpdateRequest.addReqParam("longitude", trafficeventUpdateRequest.k);
        trafficeventUpdateRequest.addReqParam("latitude", trafficeventUpdateRequest.l);
        trafficeventUpdateRequest.addReqParam("gpsx", trafficeventUpdateRequest.m);
        trafficeventUpdateRequest.addReqParam("gpsy", trafficeventUpdateRequest.n);
        trafficeventUpdateRequest.addReqParam(Constants.KEY_MODE, Integer.toString(trafficeventUpdateRequest.o));
        trafficeventUpdateRequest.addReqParam(CameraControllerManager.MY_POILOCATION_ACR, Integer.toString(trafficeventUpdateRequest.p));
        trafficeventUpdateRequest.addReqParam("ontbt", Integer.toString(trafficeventUpdateRequest.q));
        trafficeventUpdateRequest.addReqParam("ismainroad", Integer.toString(trafficeventUpdateRequest.r));
        trafficeventUpdateRequest.addReqParam("speed", trafficeventUpdateRequest.s);
        trafficeventUpdateRequest.addReqParam("direction", trafficeventUpdateRequest.t);
        trafficeventUpdateRequest.addReqParam("gpstime", trafficeventUpdateRequest.u);
        trafficeventUpdateRequest.addReqParam("address", trafficeventUpdateRequest.v);
        trafficeventUpdateRequest.addReqParam("content", trafficeventUpdateRequest.w);
        trafficeventUpdateRequest.addReqParam("layerid", trafficeventUpdateRequest.x);
        trafficeventUpdateRequest.addReqParam("layertag", trafficeventUpdateRequest.y);
        trafficeventUpdateRequest.addReqParam("direct", trafficeventUpdateRequest.z);
        trafficeventUpdateRequest.addReqParam("way", trafficeventUpdateRequest.A);
        trafficeventUpdateRequest.addReqParam("pictype", trafficeventUpdateRequest.B);
        trafficeventUpdateRequest.addReqParam("file", trafficeventUpdateRequest.C);
        trafficeventUpdateRequest.addReqParam("audio", trafficeventUpdateRequest.D);
        trafficeventUpdateRequest.addReqParam("audiolen", Integer.toString(trafficeventUpdateRequest.E));
        trafficeventUpdateRequest.addReqParam("rawid", trafficeventUpdateRequest.F);
        trafficeventUpdateRequest.addReqParam("source", trafficeventUpdateRequest.G);
        trafficeventUpdateRequest.addReqParam(H5PermissionManager.level, Integer.toString(trafficeventUpdateRequest.H));
        trafficeventUpdateRequest.addReqParam("expiretime", trafficeventUpdateRequest.I);
        trafficeventUpdateRequest.addReqParam(LogUnAvailbleItem.EXTRA_KEY_EXTEND, trafficeventUpdateRequest.J);
        trafficeventUpdateRequest.addReqParam("anonymous", trafficeventUpdateRequest.K);
        trafficeventUpdateRequest.addReqParam("opposite", Integer.toString(trafficeventUpdateRequest.L));
        trafficeventUpdateRequest.addReqParam("videourl", trafficeventUpdateRequest.M);
        trafficeventUpdateRequest.addReqParam("driveway", Integer.toString(trafficeventUpdateRequest.N));
        trafficeventUpdateRequest.addReqParam("label", trafficeventUpdateRequest.O);
        trafficeventUpdateRequest.addReqParam("reportfrom", Integer.toString(trafficeventUpdateRequest.P));
        trafficeventUpdateRequest.addReqParam("report_type", Integer.toString(trafficeventUpdateRequest.Q));
        trafficeventUpdateRequest.addReqParam("audio_content", trafficeventUpdateRequest.R);
        if (dkn != null) {
            in.a().a((AosRequest) trafficeventUpdateRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) trafficeventUpdateRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
