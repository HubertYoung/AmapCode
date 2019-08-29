package com.autonavi.minimap.account.password;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.minimap.account.password.model.PasswordInitResponse;
import com.autonavi.minimap.account.password.param.PasswordInitParam;
import com.autonavi.minimap.falcon.base.FalconAosHttpResponseCallBack;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class PasswordRequestHolder {
    private static volatile PasswordRequestHolder instance;
    private AosRequest mPasswordInitRequest;

    private PasswordRequestHolder() {
    }

    public static PasswordRequestHolder getInstance() {
        if (instance == null) {
            synchronized (PasswordRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new PasswordRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendPasswordInit(PasswordInitParam passwordInitParam, dko<PasswordInitResponse> dko) {
        this.mPasswordInitRequest = new AosPostRequest();
        AosRequest aosRequest = this.mPasswordInitRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/account/password/init/");
        aosRequest.setUrl(sb.toString());
        this.mPasswordInitRequest.addSignParam("channel");
        this.mPasswordInitRequest.addSignParam("password");
        this.mPasswordInitRequest.addReqParam("password", passwordInitParam.password);
        in.a().a(this.mPasswordInitRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<PasswordInitResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new PasswordInitResponse();
            }
        });
    }

    public void cancelPasswordInit() {
        if (this.mPasswordInitRequest != null) {
            in.a().a(this.mPasswordInitRequest);
            this.mPasswordInitRequest = null;
        }
    }
}
