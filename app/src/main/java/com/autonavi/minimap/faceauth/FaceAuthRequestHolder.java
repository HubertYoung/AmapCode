package com.autonavi.minimap.faceauth;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.minimap.falcon.base.FalconAosHttpResponseCallBack;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class FaceAuthRequestHolder {
    private static volatile FaceAuthRequestHolder instance;

    private FaceAuthRequestHolder() {
    }

    public static FaceAuthRequestHolder getInstance() {
        if (instance == null) {
            synchronized (FaceAuthRequestHolder.class) {
                if (instance == null) {
                    instance = new FaceAuthRequestHolder();
                }
            }
        }
        return instance;
    }

    public AosRequest sendFaceAuthQuery(dkl dkl, dko<dkj> dko) {
        return sendFaceAuthQuery(dkl, new dkn(), dko);
    }

    public AosRequest sendFaceAuthQuery(dkl dkl, dkn dkn, dko<dkj> dko) {
        AosGetRequest aosGetRequest = new AosGetRequest();
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("ws/pp/faceauth/query");
        aosGetRequest.setUrl(sb.toString());
        aosGetRequest.addSignParam("channel");
        aosGetRequest.addSignParam("zim_id");
        if (dkn != null) {
            aosGetRequest.addHeaders(dkn.d);
            aosGetRequest.setTimeout(dkn.b);
            aosGetRequest.setRetryTimes(dkn.c);
        }
        aosGetRequest.addReqParam("zim_id", dkl.a);
        if (dkn != null) {
            in.a().a((AosRequest) aosGetRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack(dko, dkn.a) {
                public final /* synthetic */ dkm a() {
                    return new dkj();
                }
            });
        } else {
            in.a().a((AosRequest) aosGetRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack(dko) {
                public final /* synthetic */ dkm a() {
                    return new dkj();
                }
            });
        }
        return aosGetRequest;
    }

    public void cancelFaceAuthQuery(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public AosRequest sendFaceAuthInit(dkk dkk, dko<dkh> dko) {
        return sendFaceAuthInit(dkk, new dkn(), dko);
    }

    public AosRequest sendFaceAuthInit(dkk dkk, dkn dkn, dko<dkh> dko) {
        AosPostRequest aosPostRequest = new AosPostRequest();
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("ws/pp/faceauth/init");
        aosPostRequest.setUrl(sb.toString());
        aosPostRequest.addSignParam("channel");
        aosPostRequest.addSignParam("product");
        if (dkn != null) {
            aosPostRequest.addHeaders(dkn.d);
            aosPostRequest.setTimeout(dkn.b);
            aosPostRequest.setRetryTimes(dkn.c);
        }
        aosPostRequest.addReqParam("product", Integer.toString(dkk.a));
        aosPostRequest.addReqParam("meta_info", dkk.b);
        if (dkn != null) {
            in.a().a((AosRequest) aosPostRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack(dko, dkn.a) {
                public final /* synthetic */ dkm a() {
                    return new dkh();
                }
            });
        } else {
            in.a().a((AosRequest) aosPostRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack(dko) {
                public final /* synthetic */ dkm a() {
                    return new dkh();
                }
            });
        }
        return aosPostRequest;
    }

    public void cancelFaceAuthInit(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }
}
