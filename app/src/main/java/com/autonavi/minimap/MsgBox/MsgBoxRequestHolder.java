package com.autonavi.minimap.MsgBox;

import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.MsgBox.param.PullRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class MsgBoxRequestHolder {
    private static volatile MsgBoxRequestHolder instance;

    private MsgBoxRequestHolder() {
    }

    public static MsgBoxRequestHolder getInstance() {
        if (instance == null) {
            synchronized (MsgBoxRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new MsgBoxRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendPull(PullRequest pullRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendPull(pullRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendPull(PullRequest pullRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            pullRequest.addHeaders(dkn.d);
            pullRequest.setTimeout(dkn.b);
            pullRequest.setRetryTimes(dkn.c);
        }
        pullRequest.setUrl(PullRequest.a);
        pullRequest.addSignParam("channel");
        pullRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        pullRequest.addSignParam("token");
        pullRequest.addReqParam("pagesize", pullRequest.b);
        pullRequest.addReqParam("vernier", pullRequest.c);
        pullRequest.addReqParam(AutoJsonUtils.JSON_ADCODE, pullRequest.d);
        pullRequest.addReqParam("tid", pullRequest.e);
        pullRequest.addReqParam(LocationParams.PARA_COMMON_DIV, pullRequest.f);
        pullRequest.addReqParam("token", pullRequest.g);
        pullRequest.addReqParam("category_conf_md5", pullRequest.h);
        pullRequest.addReqParam("latitude", pullRequest.i);
        pullRequest.addReqParam("longitude", pullRequest.j);
        pullRequest.addReqParam(H5AppUtil.scene, pullRequest.k);
        if (dkn != null) {
            in.a().a((AosRequest) pullRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) pullRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
