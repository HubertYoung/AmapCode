package com.autonavi.minimap.account.appeal;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.minimap.account.appeal.model.AppealBindMobileResponse;
import com.autonavi.minimap.account.appeal.model.AppealCheckResponse;
import com.autonavi.minimap.account.appeal.param.AppealBindMobileParam;
import com.autonavi.minimap.account.appeal.param.AppeallCheckParam;
import com.autonavi.minimap.falcon.base.FalconAosHttpResponseCallBack;
import com.taobao.accs.common.Constants;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class AppealRequestHolder {
    private static volatile AppealRequestHolder instance;
    private AosRequest mAppealBindMobileRequest;
    private AosRequest mAppealCheckRequest;

    private AppealRequestHolder() {
    }

    public static AppealRequestHolder getInstance() {
        if (instance == null) {
            synchronized (AppealRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new AppealRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendAppealCheck(AppeallCheckParam appeallCheckParam, dko<AppealCheckResponse> dko) {
        this.mAppealCheckRequest = new AosGetRequest();
        AosRequest aosRequest = this.mAppealCheckRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/account/appeal/check");
        aosRequest.setUrl(sb.toString());
        this.mAppealCheckRequest.addSignParam("channel");
        this.mAppealCheckRequest.addSignParam("sign_id");
        this.mAppealCheckRequest.addReqParam("sign_id", appeallCheckParam.signId);
        in.a().a(this.mAppealCheckRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<AppealCheckResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new AppealCheckResponse();
            }
        });
    }

    public void cancelAppealCheck() {
        if (this.mAppealCheckRequest != null) {
            in.a().a(this.mAppealCheckRequest);
            this.mAppealCheckRequest = null;
        }
    }

    public void sendAppealBindMobile(AppealBindMobileParam appealBindMobileParam, dko<AppealBindMobileResponse> dko) {
        this.mAppealBindMobileRequest = new AosPostRequest();
        AosRequest aosRequest = this.mAppealBindMobileRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("ws/pp/account/appeal/bind-mobile");
        aosRequest.setUrl(sb.toString());
        this.mAppealBindMobileRequest.addSignParam("channel");
        this.mAppealBindMobileRequest.addSignParam("mobile");
        this.mAppealBindMobileRequest.addSignParam("code");
        this.mAppealBindMobileRequest.addSignParam("sign_id");
        this.mAppealBindMobileRequest.addReqParam("mobile", appealBindMobileParam.mobile);
        this.mAppealBindMobileRequest.addReqParam("code", appealBindMobileParam.code);
        this.mAppealBindMobileRequest.addReqParam("sign_id", appealBindMobileParam.signId);
        this.mAppealBindMobileRequest.addReqParam("replace_type", Integer.toString(appealBindMobileParam.replaceType));
        this.mAppealBindMobileRequest.addReqParam(Constants.KEY_MODE, Integer.toString(appealBindMobileParam.mode));
        in.a().a(this.mAppealBindMobileRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<AppealBindMobileResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new AppealBindMobileResponse();
            }
        });
    }

    public void cancelAppealBindMobile() {
        if (this.mAppealBindMobileRequest != null) {
            in.a().a(this.mAppealBindMobileRequest);
            this.mAppealBindMobileRequest = null;
        }
    }
}
