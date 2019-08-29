package com.autonavi.minimap.account.verify;

import android.support.v4.app.NotificationCompat;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.minimap.account.verify.model.VerifycodeResponse;
import com.autonavi.minimap.account.verify.param.VerifyCheckParam;
import com.autonavi.minimap.account.verify.param.VerifyGetParam;
import com.autonavi.minimap.falcon.base.FalconAosHttpResponseCallBack;
import com.taobao.accs.common.Constants;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class VerifyRequestHolder {
    private static volatile VerifyRequestHolder instance;
    private AosRequest mVerifyCheckRequest;
    private AosRequest mVerifyGetRequest;

    private VerifyRequestHolder() {
    }

    public static VerifyRequestHolder getInstance() {
        if (instance == null) {
            synchronized (VerifyRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new VerifyRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendVerifyCheck(VerifyCheckParam verifyCheckParam, dko<VerifycodeResponse> dko) {
        this.mVerifyCheckRequest = new AosPostRequest();
        AosRequest aosRequest = this.mVerifyCheckRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/verifycode/check/");
        aosRequest.setUrl(sb.toString());
        this.mVerifyCheckRequest.addSignParam("channel");
        this.mVerifyCheckRequest.addSignParam("code");
        this.mVerifyCheckRequest.addReqParam("mobile", verifyCheckParam.mobile);
        this.mVerifyCheckRequest.addReqParam(NotificationCompat.CATEGORY_EMAIL, verifyCheckParam.email);
        this.mVerifyCheckRequest.addReqParam("code", verifyCheckParam.code);
        this.mVerifyCheckRequest.addReqParam("code_type", verifyCheckParam.code_type);
        this.mVerifyCheckRequest.addReqParam("apply", Integer.toString(verifyCheckParam.apply));
        in.a().a(this.mVerifyCheckRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<VerifycodeResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new VerifycodeResponse();
            }
        });
    }

    public void cancelVerifyCheck() {
        if (this.mVerifyCheckRequest != null) {
            in.a().a(this.mVerifyCheckRequest);
            this.mVerifyCheckRequest = null;
        }
    }

    public void sendVerifyGet(VerifyGetParam verifyGetParam, dko<VerifycodeResponse> dko) {
        this.mVerifyGetRequest = new AosPostRequest();
        AosRequest aosRequest = this.mVerifyGetRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/verifycode/get/");
        aosRequest.setUrl(sb.toString());
        this.mVerifyGetRequest.addSignParam("channel");
        this.mVerifyGetRequest.addSignParam("code_type");
        this.mVerifyGetRequest.addSignParam("target_type");
        this.mVerifyGetRequest.addSignParam("target_value");
        this.mVerifyGetRequest.addReqParam("code_type", verifyGetParam.code_type);
        this.mVerifyGetRequest.addReqParam("target_type", verifyGetParam.target_type);
        this.mVerifyGetRequest.addReqParam("target_value", verifyGetParam.target_value);
        this.mVerifyGetRequest.addReqParam("skip_new", Integer.toString(verifyGetParam.skip_new));
        this.mVerifyGetRequest.addReqParam(Constants.KEY_MODE, Integer.toString(verifyGetParam.mode));
        in.a().a(this.mVerifyGetRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<VerifycodeResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new VerifycodeResponse();
            }
        });
    }

    public void cancelVerifyGet() {
        if (this.mVerifyGetRequest != null) {
            in.a().a(this.mVerifyGetRequest);
            this.mVerifyGetRequest = null;
        }
    }
}
