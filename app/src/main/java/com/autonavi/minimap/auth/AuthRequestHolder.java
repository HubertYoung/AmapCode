package com.autonavi.minimap.auth;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.auth.param.AlipayInfoRequest;
import com.autonavi.minimap.auth.param.RequestVerifycodeRequest;
import com.autonavi.minimap.auth.param.UserDeviceRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.taobao.accs.common.Constants;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class AuthRequestHolder {
    private static volatile AuthRequestHolder instance;

    private AuthRequestHolder() {
    }

    public static AuthRequestHolder getInstance() {
        if (instance == null) {
            synchronized (AuthRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new AuthRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendAlipayInfo(AlipayInfoRequest alipayInfoRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendAlipayInfo(alipayInfoRequest, new dkn(), aosResponseCallback);
    }

    public void sendRequestVerifycode(RequestVerifycodeRequest requestVerifycodeRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendRequestVerifycode(requestVerifycodeRequest, new dkn(), aosResponseCallback);
    }

    public void sendUserDevice(UserDeviceRequest userDeviceRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendUserDevice(userDeviceRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendAlipayInfo(AlipayInfoRequest alipayInfoRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            alipayInfoRequest.addHeaders(dkn.d);
            alipayInfoRequest.setTimeout(dkn.b);
            alipayInfoRequest.setRetryTimes(dkn.c);
        }
        alipayInfoRequest.setUrl(AlipayInfoRequest.a);
        alipayInfoRequest.addSignParam("channel");
        alipayInfoRequest.addReqParam("type", alipayInfoRequest.b);
        alipayInfoRequest.addReqParam("top_token", alipayInfoRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) alipayInfoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) alipayInfoRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendRequestVerifycode(RequestVerifycodeRequest requestVerifycodeRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            requestVerifycodeRequest.addHeaders(dkn.d);
            requestVerifycodeRequest.setTimeout(dkn.b);
            requestVerifycodeRequest.setRetryTimes(dkn.c);
        }
        requestVerifycodeRequest.setUrl(RequestVerifycodeRequest.a);
        requestVerifycodeRequest.addSignParam("channel");
        requestVerifycodeRequest.addSignParam("type");
        requestVerifycodeRequest.addSignParam("delivery");
        requestVerifycodeRequest.addSignParam("relater");
        requestVerifycodeRequest.addReqParam("type", requestVerifycodeRequest.b);
        requestVerifycodeRequest.addReqParam("delivery", requestVerifycodeRequest.c);
        requestVerifycodeRequest.addReqParam("relater", requestVerifycodeRequest.d);
        requestVerifycodeRequest.addReqParam(Constants.KEY_MODE, requestVerifycodeRequest.e);
        requestVerifycodeRequest.addReqParam("skip_new", requestVerifycodeRequest.f);
        if (dkn != null) {
            in.a().a((AosRequest) requestVerifycodeRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) requestVerifycodeRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendUserDevice(UserDeviceRequest userDeviceRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            userDeviceRequest.addHeaders(dkn.d);
            userDeviceRequest.setTimeout(dkn.b);
            userDeviceRequest.setRetryTimes(dkn.c);
        }
        userDeviceRequest.setUrl(UserDeviceRequest.a);
        userDeviceRequest.addSignParam("channel");
        userDeviceRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        userDeviceRequest.addReqParam("os", userDeviceRequest.b);
        userDeviceRequest.addReqParam("token", userDeviceRequest.c);
        userDeviceRequest.addReqParam(LocationParams.PARA_FLP_AUTONAVI_LON, userDeviceRequest.d);
        userDeviceRequest.addReqParam("lat", userDeviceRequest.e);
        userDeviceRequest.addReqParam("sa", userDeviceRequest.f);
        userDeviceRequest.addReqParam("push_url", userDeviceRequest.g);
        userDeviceRequest.addReqParam("city_switched", userDeviceRequest.h);
        userDeviceRequest.addReqParam("cache_expired", userDeviceRequest.i);
        userDeviceRequest.addReqParam("flag", userDeviceRequest.j);
        userDeviceRequest.addReqParam("pushopen", userDeviceRequest.k);
        userDeviceRequest.addReqParam("rom_ver", userDeviceRequest.l);
        userDeviceRequest.addReqParam("dai", userDeviceRequest.m);
        userDeviceRequest.addReqParam("dsn", userDeviceRequest.n);
        userDeviceRequest.addReqParam("dcs", userDeviceRequest.o);
        userDeviceRequest.addReqParam("collect_info", userDeviceRequest.p);
        userDeviceRequest.addReqParam(Constants.KEY_IMSI, userDeviceRequest.q);
        userDeviceRequest.addReqParam("dbrand", userDeviceRequest.r);
        userDeviceRequest.addReqParam("ddevice", userDeviceRequest.s);
        userDeviceRequest.addReqParam("dmf", userDeviceRequest.t);
        userDeviceRequest.addReqParam("dmodel", userDeviceRequest.u);
        userDeviceRequest.addReqParam("dproduct", userDeviceRequest.v);
        userDeviceRequest.addReqParam("dfp", userDeviceRequest.w);
        userDeviceRequest.addReqParam("dhw", userDeviceRequest.x);
        userDeviceRequest.addReqParam("dhost", userDeviceRequest.y);
        userDeviceRequest.addReqParam("dvid", userDeviceRequest.z);
        userDeviceRequest.addReqParam("dtags", userDeviceRequest.A);
        userDeviceRequest.addReqParam("dtime", userDeviceRequest.B);
        userDeviceRequest.addReqParam("dvcode", userDeviceRequest.C);
        userDeviceRequest.addReqParam("dvinc", userDeviceRequest.D);
        userDeviceRequest.addReqParam("dvrel", userDeviceRequest.E);
        userDeviceRequest.addReqParam("dvsdk", userDeviceRequest.F);
        if (dkn != null) {
            in.a().a((AosRequest) userDeviceRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) userDeviceRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
