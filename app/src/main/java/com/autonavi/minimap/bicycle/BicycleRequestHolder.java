package com.autonavi.minimap.bicycle;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.bicycle.param.BicycleStatusCmd1Param;
import com.autonavi.minimap.bicycle.param.BicycleStatusCmd2Param;
import com.autonavi.minimap.bicycle.param.BicycleStatusCmd3Request;
import com.autonavi.minimap.bicycle.param.ShareBikeBalanceRequest;
import com.autonavi.minimap.bicycle.param.ShareBikeCityInfoRequest;
import com.autonavi.minimap.bicycle.param.ShareBikeCpconfRequest;
import com.autonavi.minimap.bicycle.param.ShareBikeIConconfRequest;
import com.autonavi.minimap.bicycle.param.ShareBikeRideStateRequest;
import com.autonavi.minimap.bicycle.param.ShareBikeTokenRequest;
import com.autonavi.minimap.bicycle.param.ShareBikeUploadRequest;
import com.autonavi.minimap.bicycle.param.ShareBikeUserInfoRequest;
import com.autonavi.minimap.bicycle.param.ShareBikeUserTagRequest;
import com.autonavi.minimap.bicycle.param.ShareBikeWalletInfoRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.taobao.agoo.control.data.BaseDO;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class BicycleRequestHolder {
    private static volatile BicycleRequestHolder instance;

    private BicycleRequestHolder() {
    }

    public static BicycleRequestHolder getInstance() {
        if (instance == null) {
            synchronized (BicycleRequestHolder.class) {
                if (instance == null) {
                    instance = new BicycleRequestHolder();
                }
            }
        }
        return instance;
    }

    public void sendSharebBikeBalance(ShareBikeBalanceRequest shareBikeBalanceRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendSharebBikeBalance(shareBikeBalanceRequest, new dkn(), aosResponseCallback);
    }

    public void sendBicycleStatus(BicycleStatusCmd3Request bicycleStatusCmd3Request, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendBicycleStatus(bicycleStatusCmd3Request, new dkn(), aosResponseCallback);
    }

    public void sendBicycleStatus(BicycleStatusCmd1Param bicycleStatusCmd1Param, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendBicycleStatus(bicycleStatusCmd1Param, new dkn(), aosResponseCallback);
    }

    public void sendBicycleStatus(BicycleStatusCmd2Param bicycleStatusCmd2Param, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendBicycleStatus(bicycleStatusCmd2Param, new dkn(), aosResponseCallback);
    }

    public void sendShareBikeCityInfo(ShareBikeCityInfoRequest shareBikeCityInfoRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendShareBikeCityInfo(shareBikeCityInfoRequest, new dkn(), aosResponseCallback);
    }

    public void sendShareBikeCpconf(ShareBikeCpconfRequest shareBikeCpconfRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendShareBikeCpconf(shareBikeCpconfRequest, new dkn(), aosResponseCallback);
    }

    public void sendShareBikeIConconf(ShareBikeIConconfRequest shareBikeIConconfRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendShareBikeIConconf(shareBikeIConconfRequest, new dkn(), aosResponseCallback);
    }

    public void sendShareBikeRideState(ShareBikeRideStateRequest shareBikeRideStateRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendShareBikeRideState(shareBikeRideStateRequest, new dkn(), aosResponseCallback);
    }

    public void sendShareBikeToken(ShareBikeTokenRequest shareBikeTokenRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendShareBikeToken(shareBikeTokenRequest, new dkn(), aosResponseCallback);
    }

    public void sendShareBikeUpload(ShareBikeUploadRequest shareBikeUploadRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendShareBikeUpload(shareBikeUploadRequest, new dkn(), aosResponseCallback);
    }

    public void sendShareBikeUserInfo(ShareBikeUserInfoRequest shareBikeUserInfoRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendShareBikeUserInfo(shareBikeUserInfoRequest, new dkn(), aosResponseCallback);
    }

    public void sendShareBikeUserTag(ShareBikeUserTagRequest shareBikeUserTagRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendShareBikeUserTag(shareBikeUserTagRequest, new dkn(), aosResponseCallback);
    }

    public void sendShareBikeWalletInfo(ShareBikeWalletInfoRequest shareBikeWalletInfoRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendShareBikeWalletInfo(shareBikeWalletInfoRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendSharebBikeBalance(ShareBikeBalanceRequest shareBikeBalanceRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            shareBikeBalanceRequest.addHeaders(dkn.d);
            shareBikeBalanceRequest.setTimeout(dkn.b);
            shareBikeBalanceRequest.setRetryTimes(dkn.c);
        }
        shareBikeBalanceRequest.setUrl(ShareBikeBalanceRequest.a);
        shareBikeBalanceRequest.addSignParam("channel");
        shareBikeBalanceRequest.addSignParam("ts");
        shareBikeBalanceRequest.addSignParam("source");
        shareBikeBalanceRequest.addReqParam("ts", shareBikeBalanceRequest.b);
        shareBikeBalanceRequest.addReqParam("source", shareBikeBalanceRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) shareBikeBalanceRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) shareBikeBalanceRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendBicycleStatus(BicycleStatusCmd3Request bicycleStatusCmd3Request, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            bicycleStatusCmd3Request.addHeaders(dkn.d);
            bicycleStatusCmd3Request.setTimeout(dkn.b);
            bicycleStatusCmd3Request.setRetryTimes(dkn.c);
        }
        bicycleStatusCmd3Request.setUrl(BicycleStatusCmd3Request.a);
        bicycleStatusCmd3Request.addSignParam("channel");
        bicycleStatusCmd3Request.addSignParam(LocationParams.PARA_COMMON_DIU);
        bicycleStatusCmd3Request.addSignParam(LocationParams.PARA_COMMON_DIV);
        bicycleStatusCmd3Request.addSignParam(BaseDO.JSON_CMD);
        bicycleStatusCmd3Request.addReqParam("city", bicycleStatusCmd3Request.b);
        bicycleStatusCmd3Request.addReqParam(DictionaryKeys.CTRLXY_X, bicycleStatusCmd3Request.c);
        bicycleStatusCmd3Request.addReqParam(DictionaryKeys.CTRLXY_Y, bicycleStatusCmd3Request.d);
        bicycleStatusCmd3Request.addReqParam("source", bicycleStatusCmd3Request.e);
        bicycleStatusCmd3Request.addReqParam("id", bicycleStatusCmd3Request.f);
        bicycleStatusCmd3Request.addReqParam("scope", bicycleStatusCmd3Request.g);
        bicycleStatusCmd3Request.addReqParam("version", bicycleStatusCmd3Request.h);
        bicycleStatusCmd3Request.addReqParam(BaseDO.JSON_CMD, bicycleStatusCmd3Request.i);
        bicycleStatusCmd3Request.addReqParam("token", bicycleStatusCmd3Request.j);
        bicycleStatusCmd3Request.addReqParam("frm", bicycleStatusCmd3Request.k);
        if (dkn != null) {
            in.a().a((AosRequest) bicycleStatusCmd3Request, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) bicycleStatusCmd3Request, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendBicycleStatus(BicycleStatusCmd1Param bicycleStatusCmd1Param, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            bicycleStatusCmd1Param.addHeaders(dkn.d);
            bicycleStatusCmd1Param.setTimeout(dkn.b);
            bicycleStatusCmd1Param.setRetryTimes(dkn.c);
        }
        bicycleStatusCmd1Param.setUrl(BicycleStatusCmd1Param.a);
        bicycleStatusCmd1Param.addSignParam("channel");
        bicycleStatusCmd1Param.addSignParam(LocationParams.PARA_COMMON_DIU);
        bicycleStatusCmd1Param.addSignParam(LocationParams.PARA_COMMON_DIV);
        bicycleStatusCmd1Param.addSignParam(BaseDO.JSON_CMD);
        bicycleStatusCmd1Param.addReqParam("city", bicycleStatusCmd1Param.b);
        bicycleStatusCmd1Param.addReqParam(DictionaryKeys.CTRLXY_X, bicycleStatusCmd1Param.c);
        bicycleStatusCmd1Param.addReqParam(DictionaryKeys.CTRLXY_Y, bicycleStatusCmd1Param.d);
        bicycleStatusCmd1Param.addReqParam("source", bicycleStatusCmd1Param.e);
        bicycleStatusCmd1Param.addReqParam("id", bicycleStatusCmd1Param.f);
        bicycleStatusCmd1Param.addReqParam("scope", bicycleStatusCmd1Param.g);
        bicycleStatusCmd1Param.addReqParam("version", bicycleStatusCmd1Param.h);
        bicycleStatusCmd1Param.addReqParam(BaseDO.JSON_CMD, bicycleStatusCmd1Param.i);
        bicycleStatusCmd1Param.addReqParam("token", bicycleStatusCmd1Param.j);
        bicycleStatusCmd1Param.addReqParam("frm", bicycleStatusCmd1Param.k);
        if (dkn != null) {
            in.a().a((AosRequest) bicycleStatusCmd1Param, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) bicycleStatusCmd1Param, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendBicycleStatus(BicycleStatusCmd2Param bicycleStatusCmd2Param, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            bicycleStatusCmd2Param.addHeaders(dkn.d);
            bicycleStatusCmd2Param.setTimeout(dkn.b);
            bicycleStatusCmd2Param.setRetryTimes(dkn.c);
        }
        bicycleStatusCmd2Param.setUrl(BicycleStatusCmd2Param.a);
        bicycleStatusCmd2Param.addSignParam("channel");
        bicycleStatusCmd2Param.addSignParam(LocationParams.PARA_COMMON_DIU);
        bicycleStatusCmd2Param.addSignParam(LocationParams.PARA_COMMON_DIV);
        bicycleStatusCmd2Param.addSignParam(BaseDO.JSON_CMD);
        bicycleStatusCmd2Param.addReqParam("city", bicycleStatusCmd2Param.b);
        bicycleStatusCmd2Param.addReqParam(DictionaryKeys.CTRLXY_X, bicycleStatusCmd2Param.c);
        bicycleStatusCmd2Param.addReqParam(DictionaryKeys.CTRLXY_Y, bicycleStatusCmd2Param.d);
        bicycleStatusCmd2Param.addReqParam("source", bicycleStatusCmd2Param.e);
        bicycleStatusCmd2Param.addReqParam("id", bicycleStatusCmd2Param.f);
        bicycleStatusCmd2Param.addReqParam("scope", bicycleStatusCmd2Param.g);
        bicycleStatusCmd2Param.addReqParam("version", bicycleStatusCmd2Param.h);
        bicycleStatusCmd2Param.addReqParam(BaseDO.JSON_CMD, bicycleStatusCmd2Param.i);
        bicycleStatusCmd2Param.addReqParam("token", bicycleStatusCmd2Param.j);
        bicycleStatusCmd2Param.addReqParam("frm", bicycleStatusCmd2Param.k);
        if (dkn != null) {
            in.a().a((AosRequest) bicycleStatusCmd2Param, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) bicycleStatusCmd2Param, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendShareBikeCityInfo(ShareBikeCityInfoRequest shareBikeCityInfoRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            shareBikeCityInfoRequest.addHeaders(dkn.d);
            shareBikeCityInfoRequest.setTimeout(dkn.b);
            shareBikeCityInfoRequest.setRetryTimes(dkn.c);
        }
        shareBikeCityInfoRequest.setUrl(ShareBikeCityInfoRequest.a);
        shareBikeCityInfoRequest.addSignParam("channel");
        shareBikeCityInfoRequest.addSignParam("ts");
        shareBikeCityInfoRequest.addSignParam("citycode");
        shareBikeCityInfoRequest.addReqParam("ts", shareBikeCityInfoRequest.b);
        shareBikeCityInfoRequest.addReqParam("citycode", shareBikeCityInfoRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) shareBikeCityInfoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) shareBikeCityInfoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendShareBikeCpconf(ShareBikeCpconfRequest shareBikeCpconfRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            shareBikeCpconfRequest.addHeaders(dkn.d);
            shareBikeCpconfRequest.setTimeout(dkn.b);
            shareBikeCpconfRequest.setRetryTimes(dkn.c);
        }
        shareBikeCpconfRequest.setUrl(ShareBikeCpconfRequest.a);
        shareBikeCpconfRequest.addSignParam("channel");
        shareBikeCpconfRequest.addSignParam("ts");
        shareBikeCpconfRequest.addSignParam("source");
        shareBikeCpconfRequest.addReqParam("ts", shareBikeCpconfRequest.b);
        shareBikeCpconfRequest.addReqParam(AutoJsonUtils.JSON_ADCODE, shareBikeCpconfRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) shareBikeCpconfRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) shareBikeCpconfRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendShareBikeIConconf(ShareBikeIConconfRequest shareBikeIConconfRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            shareBikeIConconfRequest.addHeaders(dkn.d);
            shareBikeIConconfRequest.setTimeout(dkn.b);
            shareBikeIConconfRequest.setRetryTimes(dkn.c);
        }
        shareBikeIConconfRequest.setUrl(ShareBikeIConconfRequest.a);
        shareBikeIConconfRequest.addSignParam("channel");
        shareBikeIConconfRequest.addSignParam("ts");
        shareBikeIConconfRequest.addReqParam("ts", shareBikeIConconfRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) shareBikeIConconfRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) shareBikeIConconfRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendShareBikeRideState(ShareBikeRideStateRequest shareBikeRideStateRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            shareBikeRideStateRequest.addHeaders(dkn.d);
            shareBikeRideStateRequest.setTimeout(dkn.b);
            shareBikeRideStateRequest.setRetryTimes(dkn.c);
        }
        shareBikeRideStateRequest.setUrl(ShareBikeRideStateRequest.a);
        shareBikeRideStateRequest.addSignParam("channel");
        shareBikeRideStateRequest.addSignParam("source");
        shareBikeRideStateRequest.addSignParam(DictionaryKeys.CTRLXY_X);
        shareBikeRideStateRequest.addSignParam(DictionaryKeys.CTRLXY_Y);
        shareBikeRideStateRequest.addReqParam("source", shareBikeRideStateRequest.b);
        shareBikeRideStateRequest.addReqParam(DictionaryKeys.CTRLXY_X, shareBikeRideStateRequest.c);
        shareBikeRideStateRequest.addReqParam(DictionaryKeys.CTRLXY_Y, shareBikeRideStateRequest.d);
        shareBikeRideStateRequest.addReqParam("citycode", shareBikeRideStateRequest.e);
        shareBikeRideStateRequest.addReqParam("orderid", shareBikeRideStateRequest.f);
        if (dkn != null) {
            in.a().a((AosRequest) shareBikeRideStateRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) shareBikeRideStateRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendShareBikeToken(ShareBikeTokenRequest shareBikeTokenRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            shareBikeTokenRequest.addHeaders(dkn.d);
            shareBikeTokenRequest.setTimeout(dkn.b);
            shareBikeTokenRequest.setRetryTimes(dkn.c);
        }
        shareBikeTokenRequest.setUrl(ShareBikeTokenRequest.a);
        shareBikeTokenRequest.addSignParam("channel");
        shareBikeTokenRequest.addSignParam("ts");
        shareBikeTokenRequest.addReqParam("ts", shareBikeTokenRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) shareBikeTokenRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) shareBikeTokenRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendShareBikeUpload(ShareBikeUploadRequest shareBikeUploadRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            shareBikeUploadRequest.addHeaders(dkn.d);
            shareBikeUploadRequest.setTimeout(dkn.b);
            shareBikeUploadRequest.setRetryTimes(dkn.c);
        }
        shareBikeUploadRequest.setUrl(ShareBikeUploadRequest.f);
        shareBikeUploadRequest.addSignParam("channel");
        shareBikeUploadRequest.addSignParam("source");
        shareBikeUploadRequest.addSignParam("orderid");
        shareBikeUploadRequest.addReqParam("source", shareBikeUploadRequest.g);
        shareBikeUploadRequest.addReqParam("orderid", shareBikeUploadRequest.h);
        shareBikeUploadRequest.addReqParam("distance", shareBikeUploadRequest.i);
        if (shareBikeUploadRequest.j != null && shareBikeUploadRequest.j.exists()) {
            shareBikeUploadRequest.a((String) "file", shareBikeUploadRequest.j);
        }
        if (dkn != null) {
            in.a().a((AosRequest) shareBikeUploadRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) shareBikeUploadRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendShareBikeUserInfo(ShareBikeUserInfoRequest shareBikeUserInfoRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            shareBikeUserInfoRequest.addHeaders(dkn.d);
            shareBikeUserInfoRequest.setTimeout(dkn.b);
            shareBikeUserInfoRequest.setRetryTimes(dkn.c);
        }
        shareBikeUserInfoRequest.setUrl(ShareBikeUserInfoRequest.a);
        shareBikeUserInfoRequest.addSignParam("channel");
        shareBikeUserInfoRequest.addSignParam("ts");
        shareBikeUserInfoRequest.addReqParam("ts", shareBikeUserInfoRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) shareBikeUserInfoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) shareBikeUserInfoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendShareBikeUserTag(ShareBikeUserTagRequest shareBikeUserTagRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            shareBikeUserTagRequest.addHeaders(dkn.d);
            shareBikeUserTagRequest.setTimeout(dkn.b);
            shareBikeUserTagRequest.setRetryTimes(dkn.c);
        }
        shareBikeUserTagRequest.setUrl(ShareBikeUserTagRequest.a);
        shareBikeUserTagRequest.addSignParam("channel");
        shareBikeUserTagRequest.addSignParam("ts");
        shareBikeUserTagRequest.addReqParam("ts", shareBikeUserTagRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) shareBikeUserTagRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) shareBikeUserTagRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendShareBikeWalletInfo(ShareBikeWalletInfoRequest shareBikeWalletInfoRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            shareBikeWalletInfoRequest.addHeaders(dkn.d);
            shareBikeWalletInfoRequest.setTimeout(dkn.b);
            shareBikeWalletInfoRequest.setRetryTimes(dkn.c);
        }
        shareBikeWalletInfoRequest.setUrl(ShareBikeWalletInfoRequest.a);
        shareBikeWalletInfoRequest.addSignParam("channel");
        shareBikeWalletInfoRequest.addSignParam("ts");
        shareBikeWalletInfoRequest.addSignParam("source");
        shareBikeWalletInfoRequest.addReqParam("ts", shareBikeWalletInfoRequest.b);
        shareBikeWalletInfoRequest.addReqParam("source", shareBikeWalletInfoRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) shareBikeWalletInfoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) shareBikeWalletInfoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
