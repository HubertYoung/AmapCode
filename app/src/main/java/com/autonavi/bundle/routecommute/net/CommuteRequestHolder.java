package com.autonavi.bundle.routecommute.net;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.falcon.base.FalconAosHttpResponseCallBack;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class CommuteRequestHolder {
    private static volatile CommuteRequestHolder instance;

    private CommuteRequestHolder() {
    }

    public static CommuteRequestHolder getInstance() {
        if (instance == null) {
            synchronized (CommuteRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new CommuteRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendEtaRoute(azw azw, dko<azv> dko) {
        String keyValue = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.DRIVE_AOS_URL_KEY);
        AosPostRequest aosPostRequest = new AosPostRequest();
        StringBuilder sb = new StringBuilder();
        sb.append(keyValue);
        sb.append("/ws/shield/maps/mapapi/navigation/newetaroute/");
        aosPostRequest.setUrl(sb.toString());
        aosPostRequest.addSignParam("channel");
        aosPostRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        aosPostRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        aosPostRequest.addReqParam(LocationParams.PARA_FLP_AUTONAVI_LON, azw.a);
        aosPostRequest.addReqParam("lat", azw.b);
        aosPostRequest.addReqParam(DictionaryKeys.CTRLXY_X, azw.c);
        aosPostRequest.addReqParam(DictionaryKeys.CTRLXY_Y, azw.d);
        aosPostRequest.addReqParam("policy2", azw.e);
        aosPostRequest.addReqParam("multi_routes", Integer.toString(azw.f));
        aosPostRequest.addReqParam("ver", azw.g);
        aosPostRequest.addReqParam("sdk_version", azw.h);
        aosPostRequest.addReqParam("start_citycode", Integer.toString(azw.i));
        aosPostRequest.addReqParam("end_citycode", Integer.toString(azw.j));
        aosPostRequest.addReqParam("plate", azw.k);
        aosPostRequest.addReqParam("vehicletype", Integer.toString(azw.l));
        aosPostRequest.addReqParam("etd_session_id", azw.m);
        aosPostRequest.addReqParam("uuid", azw.n);
        aosPostRequest.addReqParam("source", azw.o);
        aosPostRequest.addReqParam("target", azw.p);
        aosPostRequest.addReqParam("content_options", Integer.toString(azw.q));
        aosPostRequest.addReqParam("etdvehicle", Integer.toString(azw.r));
        aosPostRequest.addReqParam("etd_info", azw.s);
        in.a().a((AosRequest) aosPostRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<azv, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new azv();
            }
        });
    }

    public void cancelEtaRoute(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }
}
