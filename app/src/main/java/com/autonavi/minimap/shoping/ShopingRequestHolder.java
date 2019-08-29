package com.autonavi.minimap.shoping;

import com.alipay.android.phone.mobilecommon.multimedia.video.data.APVideoEffect;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.shoping.param.PoiListRequest;
import com.autonavi.sdk.location.LocationInstrument;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class ShopingRequestHolder {
    private static volatile ShopingRequestHolder instance;

    private ShopingRequestHolder() {
    }

    public static ShopingRequestHolder getInstance() {
        if (instance == null) {
            synchronized (ShopingRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new ShopingRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendPoiList(PoiListRequest poiListRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendPoiList(poiListRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendPoiList(PoiListRequest poiListRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            poiListRequest.addHeaders(dkn.d);
            poiListRequest.setTimeout(dkn.b);
            poiListRequest.setRetryTimes(dkn.c);
        }
        poiListRequest.setUrl(PoiListRequest.a);
        poiListRequest.addSignParam("channel");
        poiListRequest.addSignParam(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
        poiListRequest.addReqParam("pagenum", Integer.toString(poiListRequest.b));
        poiListRequest.addReqParam("pagesize", Integer.toString(poiListRequest.c));
        poiListRequest.addReqParam(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, poiListRequest.d);
        poiListRequest.addReqParam("floor", poiListRequest.e);
        poiListRequest.addReqParam("classify", poiListRequest.f);
        poiListRequest.addReqParam(APVideoEffect.TYPE_FILTER, poiListRequest.g);
        if (dkn != null) {
            in.a().a((AosRequest) poiListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) poiListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
