package com.autonavi.minimap.account.deactivate;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.account.deactivate.model.DeactivateCheckResponse;
import com.autonavi.minimap.account.deactivate.model.DeactivateHelpResponse;
import com.autonavi.minimap.account.deactivate.model.DeactivateResponse;
import com.autonavi.minimap.account.deactivate.param.DeactivateHelpParam;
import com.autonavi.minimap.account.deactivate.param.DeactivateParam;
import com.autonavi.minimap.falcon.base.FalconAosHttpResponseCallBack;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class DeactivateRequestHolder {
    private static volatile DeactivateRequestHolder instance;
    private AosRequest mDeactivateCheckRequest;
    private AosRequest mDeactivateHelpRequest;
    private AosRequest mDeactivateRequest;

    private DeactivateRequestHolder() {
    }

    public static DeactivateRequestHolder getInstance() {
        if (instance == null) {
            synchronized (DeactivateRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new DeactivateRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendDeactivate(DeactivateParam deactivateParam, dko<DeactivateResponse> dko) {
        this.mDeactivateRequest = new AosPostRequest();
        AosRequest aosRequest = this.mDeactivateRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/account/deactivate/");
        aosRequest.setUrl(sb.toString());
        this.mDeactivateRequest.addSignParam("channel");
        this.mDeactivateRequest.addSignParam("mobile");
        this.mDeactivateRequest.addSignParam("code");
        this.mDeactivateRequest.addReqParam("mobile", deactivateParam.mobile);
        this.mDeactivateRequest.addReqParam("code", deactivateParam.code);
        in.a().a(this.mDeactivateRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<DeactivateResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new DeactivateResponse();
            }
        });
    }

    public void cancelDeactivate() {
        if (this.mDeactivateRequest != null) {
            in.a().a(this.mDeactivateRequest);
            this.mDeactivateRequest = null;
        }
    }

    public void sendDeactivateHelp(DeactivateHelpParam deactivateHelpParam, dko<DeactivateHelpResponse> dko) {
        this.mDeactivateHelpRequest = new AosGetRequest();
        AosRequest aosRequest = this.mDeactivateHelpRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/account/deactivate/help/");
        aosRequest.setUrl(sb.toString());
        this.mDeactivateHelpRequest.addSignParam("channel");
        this.mDeactivateHelpRequest.addSignParam(LocationParams.PARA_COMMON_ADIU);
        in.a().a(this.mDeactivateHelpRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<DeactivateHelpResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new DeactivateHelpResponse();
            }
        });
    }

    public void cancelDeactivateHelp() {
        if (this.mDeactivateHelpRequest != null) {
            in.a().a(this.mDeactivateHelpRequest);
            this.mDeactivateHelpRequest = null;
        }
    }

    public void sendDeactivateCheck(DeactivateParam deactivateParam, dko<DeactivateCheckResponse> dko) {
        this.mDeactivateCheckRequest = new AosPostRequest();
        AosRequest aosRequest = this.mDeactivateCheckRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/account/deactivate/check/");
        aosRequest.setUrl(sb.toString());
        this.mDeactivateCheckRequest.addSignParam("channel");
        this.mDeactivateCheckRequest.addSignParam("mobile");
        this.mDeactivateCheckRequest.addSignParam("code");
        this.mDeactivateCheckRequest.addReqParam("mobile", deactivateParam.mobile);
        this.mDeactivateCheckRequest.addReqParam("code", deactivateParam.code);
        in.a().a(this.mDeactivateCheckRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<DeactivateCheckResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new DeactivateCheckResponse();
            }
        });
    }

    public void cancelDeactivateCheck() {
        if (this.mDeactivateCheckRequest != null) {
            in.a().a(this.mDeactivateCheckRequest);
            this.mDeactivateCheckRequest = null;
        }
    }
}
