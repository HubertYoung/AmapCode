package com.autonavi.minimap.account.logout;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.minimap.account.logout.model.LogoutResponse;
import com.autonavi.minimap.account.logout.param.LogoutParam;
import com.autonavi.minimap.falcon.base.FalconAosHttpResponseCallBack;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class LogoutRequestHolder {
    private static volatile LogoutRequestHolder instance;
    private AosRequest mLogoutRequest;

    private LogoutRequestHolder() {
    }

    public static LogoutRequestHolder getInstance() {
        if (instance == null) {
            synchronized (LogoutRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new LogoutRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendLogout(LogoutParam logoutParam, dko<LogoutResponse> dko) {
        this.mLogoutRequest = new AosPostRequest();
        AosRequest aosRequest = this.mLogoutRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/account/logout/");
        aosRequest.setUrl(sb.toString());
        this.mLogoutRequest.addSignParam("channel");
        this.mLogoutRequest.addReqParam("push_token", logoutParam.pushToken);
        in.a().a(this.mLogoutRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<LogoutResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new LogoutResponse();
            }
        });
    }

    public void cancelLogout() {
        if (this.mLogoutRequest != null) {
            in.a().a(this.mLogoutRequest);
            this.mLogoutRequest = null;
        }
    }
}
