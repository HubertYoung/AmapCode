package com.autonavi.minimap.archivement;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.archivement.param.AlipayInfoRequest;
import com.autonavi.minimap.archivement.param.ReportRequest;
import com.autonavi.minimap.archivement.param.RequestVerifycodeRequest;
import com.autonavi.minimap.archivement.param.UserDeviceRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import com.taobao.accs.common.Constants;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class ArchivementRequestHolder {
    private static volatile ArchivementRequestHolder instance;

    private ArchivementRequestHolder() {
    }

    public static ArchivementRequestHolder getInstance() {
        if (instance == null) {
            synchronized (ArchivementRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new ArchivementRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendReport(ReportRequest reportRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendReport(reportRequest, new dkn(), aosResponseCallback);
    }

    public void sendRequestVerifycode(RequestVerifycodeRequest requestVerifycodeRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendRequestVerifycode(requestVerifycodeRequest, new dkn(), aosResponseCallback);
    }

    public void sendAlipayInfo(AlipayInfoRequest alipayInfoRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendAlipayInfo(alipayInfoRequest, new dkn(), aosResponseCallback);
    }

    public void sendUserDevice(UserDeviceRequest userDeviceRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendUserDevice(userDeviceRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendReport(ReportRequest reportRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            reportRequest.addHeaders(dkn.d);
            reportRequest.setTimeout(dkn.b);
            reportRequest.setRetryTimes(dkn.c);
        }
        reportRequest.setUrl(ReportRequest.a);
        reportRequest.addSignParam("channel");
        reportRequest.addSignParam("tid");
        reportRequest.addSignParam("type");
        reportRequest.addReqParam("tid", reportRequest.b);
        reportRequest.addReqParam("type", reportRequest.c);
        reportRequest.addReqParam(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME, reportRequest.d);
        reportRequest.addReqParam(GirfFavoriteRoute.JSON_FIELD_ROUTE_END_TIME, reportRequest.e);
        reportRequest.addReqParam("distance", reportRequest.f);
        reportRequest.addReqParam("order", reportRequest.g);
        reportRequest.addReqParam(AutoJsonUtils.JSON_ADCODE, reportRequest.h);
        reportRequest.addReqParam("biz_flag", reportRequest.i);
        if (dkn != null) {
            in.a().a((AosRequest) reportRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) reportRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
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
        if (dkn != null) {
            in.a().a((AosRequest) userDeviceRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) userDeviceRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
