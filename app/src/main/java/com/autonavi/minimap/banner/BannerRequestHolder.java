package com.autonavi.minimap.banner;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.banner.param.BannerListRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.sdk.location.LocationInstrument;
import com.taobao.accs.common.Constants;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class BannerRequestHolder {
    private static volatile BannerRequestHolder instance;

    private BannerRequestHolder() {
    }

    public static BannerRequestHolder getInstance() {
        if (instance == null) {
            synchronized (BannerRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new BannerRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendList(BannerListRequest bannerListRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendList(bannerListRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendList(BannerListRequest bannerListRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            bannerListRequest.addHeaders(dkn.d);
            bannerListRequest.setTimeout(dkn.b);
            bannerListRequest.setRetryTimes(dkn.c);
        }
        bannerListRequest.setUrl(BannerListRequest.a);
        bannerListRequest.addSignParam("channel");
        bannerListRequest.addSignParam(DictionaryKeys.CTRLXY_X);
        bannerListRequest.addSignParam(DictionaryKeys.CTRLXY_Y);
        bannerListRequest.addReqParam(DictionaryKeys.CTRLXY_X, bannerListRequest.b);
        bannerListRequest.addReqParam(DictionaryKeys.CTRLXY_Y, bannerListRequest.c);
        bannerListRequest.addReqParam("carrier", bannerListRequest.d);
        bannerListRequest.addReqParam(LocationParams.PARA_COMMON_CIFA, bannerListRequest.e);
        bannerListRequest.addReqParam(LocationParams.PARA_COMMON_DIV, bannerListRequest.f);
        bannerListRequest.addReqParam("md5", bannerListRequest.g);
        bannerListRequest.addReqParam("page_id", Integer.toString(bannerListRequest.h));
        bannerListRequest.addReqParam("carrier_name", bannerListRequest.i);
        bannerListRequest.addReqParam("carrier_code", bannerListRequest.j);
        bannerListRequest.addReqParam(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, bannerListRequest.k);
        bannerListRequest.addReqParam("poi_type", bannerListRequest.l);
        bannerListRequest.addReqParam("width", bannerListRequest.m);
        bannerListRequest.addReqParam(Constants.KEY_MODEL, bannerListRequest.n);
        bannerListRequest.addReqParam("naviType", Integer.toString(bannerListRequest.o));
        if (dkn != null) {
            in.a().a((AosRequest) bannerListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) bannerListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
