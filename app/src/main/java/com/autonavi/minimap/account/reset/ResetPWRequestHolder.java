package com.autonavi.minimap.account.reset;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.minimap.account.reset.model.ResetPWResponse;
import com.autonavi.minimap.account.reset.param.ResetPWParam;
import com.autonavi.minimap.falcon.base.FalconAosHttpResponseCallBack;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class ResetPWRequestHolder {
    private static volatile ResetPWRequestHolder instance;
    private AosRequest mResetPWRequest;

    private ResetPWRequestHolder() {
    }

    public static ResetPWRequestHolder getInstance() {
        if (instance == null) {
            synchronized (ResetPWRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new ResetPWRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendResetPW(ResetPWParam resetPWParam, dko<ResetPWResponse> dko) {
        this.mResetPWRequest = new AosPostRequest();
        AosRequest aosRequest = this.mResetPWRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/account/password/reset/");
        aosRequest.setUrl(sb.toString());
        this.mResetPWRequest.addSignParam("channel");
        this.mResetPWRequest.addSignParam("code");
        this.mResetPWRequest.addSignParam("target_value");
        this.mResetPWRequest.addReqParam("code", resetPWParam.code);
        this.mResetPWRequest.addReqParam("target_value", resetPWParam.target_value);
        this.mResetPWRequest.addReqParam("password", resetPWParam.password);
        in.a().a(this.mResetPWRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<ResetPWResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new ResetPWResponse();
            }
        });
    }

    public void cancelResetPW() {
        if (this.mResetPWRequest != null) {
            in.a().a(this.mResetPWRequest);
            this.mResetPWRequest = null;
        }
    }
}
