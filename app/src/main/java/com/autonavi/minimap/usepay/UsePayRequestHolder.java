package com.autonavi.minimap.usepay;

import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.tinyappcustom.h5plugin.H5ContactPlugin;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.usepay.param.ApplyCheckRequest;
import com.autonavi.minimap.usepay.param.ListsRequest;
import com.autonavi.minimap.usepay.param.RelateRequest;
import com.autonavi.minimap.usepay.param.UploadRequest;
import com.autonavi.sdk.location.LocationInstrument;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class UsePayRequestHolder {
    private static volatile UsePayRequestHolder instance;

    private UsePayRequestHolder() {
    }

    public static UsePayRequestHolder getInstance() {
        if (instance == null) {
            synchronized (UsePayRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new UsePayRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendLists(ListsRequest listsRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendLists(listsRequest, new dkn(), aosResponseCallback);
    }

    public void sendRelate(RelateRequest relateRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendRelate(relateRequest, new dkn(), aosResponseCallback);
    }

    public void sendUpload(UploadRequest uploadRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendUpload(uploadRequest, new dkn(), aosResponseCallback);
    }

    public void sendApplyCheck(ApplyCheckRequest applyCheckRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendApplyCheck(applyCheckRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendLists(ListsRequest listsRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            listsRequest.addHeaders(dkn.d);
            listsRequest.setTimeout(dkn.b);
            listsRequest.setRetryTimes(dkn.c);
        }
        listsRequest.setUrl(ListsRequest.a);
        listsRequest.addSignParam("channel");
        listsRequest.addSignParam("session");
        listsRequest.addReqParam("session", listsRequest.b);
        listsRequest.addReqParam("page_size", listsRequest.c);
        listsRequest.addReqParam("page_num", listsRequest.d);
        if (dkn != null) {
            in.a().a((AosRequest) listsRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) listsRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendRelate(RelateRequest relateRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            relateRequest.addHeaders(dkn.d);
            relateRequest.setTimeout(dkn.b);
            relateRequest.setRetryTimes(dkn.c);
        }
        relateRequest.setUrl(RelateRequest.a);
        relateRequest.addSignParam("channel");
        relateRequest.addSignParam("record_id");
        relateRequest.addReqParam("record_id", relateRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) relateRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) relateRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendUpload(UploadRequest uploadRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            uploadRequest.addHeaders(dkn.d);
            uploadRequest.setTimeout(dkn.b);
            uploadRequest.setRetryTimes(dkn.c);
        }
        uploadRequest.setUrl(UploadRequest.f);
        uploadRequest.addSignParam("channel");
        uploadRequest.addSignParam(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
        uploadRequest.addSignParam("tid");
        uploadRequest.addReqParam(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, uploadRequest.g);
        uploadRequest.addReqParam("tid", uploadRequest.h);
        uploadRequest.addReqParam("type", uploadRequest.i);
        uploadRequest.addReqParam(H5PageData.KEY_UC_START, uploadRequest.j);
        uploadRequest.addReqParam("end", uploadRequest.k);
        uploadRequest.addReqParam("end_x", uploadRequest.l);
        uploadRequest.addReqParam("end_y", uploadRequest.m);
        uploadRequest.addReqParam("distance", uploadRequest.n);
        uploadRequest.addReqParam("last_time", uploadRequest.o);
        uploadRequest.addReqParam("speed", uploadRequest.p);
        uploadRequest.addReqParam("navigation_time", uploadRequest.q);
        uploadRequest.addReqParam("desc", uploadRequest.r);
        uploadRequest.addReqParam(H5ContactPlugin.CONTACT, uploadRequest.s);
        uploadRequest.addReqParam("correct_x", uploadRequest.t);
        uploadRequest.addReqParam("correct_y", uploadRequest.u);
        uploadRequest.addReqParam("image_x", uploadRequest.v);
        uploadRequest.addReqParam("image_y", uploadRequest.w);
        uploadRequest.addReqParam("accury", uploadRequest.x);
        if (uploadRequest.y != null && uploadRequest.y.exists()) {
            uploadRequest.a((String) "upload_image", uploadRequest.y);
        }
        if (dkn != null) {
            in.a().a((AosRequest) uploadRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) uploadRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendApplyCheck(ApplyCheckRequest applyCheckRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            applyCheckRequest.addHeaders(dkn.d);
            applyCheckRequest.setTimeout(dkn.b);
            applyCheckRequest.setRetryTimes(dkn.c);
        }
        applyCheckRequest.setUrl(ApplyCheckRequest.a);
        applyCheckRequest.addSignParam("channel");
        applyCheckRequest.addSignParam(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
        applyCheckRequest.addSignParam("tid");
        applyCheckRequest.addReqParam(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, applyCheckRequest.b);
        applyCheckRequest.addReqParam("distance", applyCheckRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) applyCheckRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) applyCheckRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
