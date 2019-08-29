package com.autonavi.minimap.account.trust_token;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.minimap.account.trust_token.model.TaobaoTrustLoginTokenResponse;
import com.autonavi.minimap.account.trust_token.param.TaobaoTrustLoginTokenParam;
import com.autonavi.minimap.falcon.base.FalconAosHttpResponseCallBack;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class TaobaoTrustLoginTokenRequestHolder {
    private static volatile TaobaoTrustLoginTokenRequestHolder instance;
    private AosRequest mTaobaoTrustLoginTokenRequest;

    private TaobaoTrustLoginTokenRequestHolder() {
    }

    public static TaobaoTrustLoginTokenRequestHolder getInstance() {
        if (instance == null) {
            synchronized (TaobaoTrustLoginTokenRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new TaobaoTrustLoginTokenRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendTaobaoTrustLoginToken(TaobaoTrustLoginTokenParam taobaoTrustLoginTokenParam, dko<TaobaoTrustLoginTokenResponse> dko) {
        this.mTaobaoTrustLoginTokenRequest = new AosPostRequest();
        AosRequest aosRequest = this.mTaobaoTrustLoginTokenRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/provider/trust-login-token/taobao/");
        aosRequest.setUrl(sb.toString());
        this.mTaobaoTrustLoginTokenRequest.addSignParam("channel");
        this.mTaobaoTrustLoginTokenRequest.addReqParam("key", taobaoTrustLoginTokenParam.key);
        in.a().a(this.mTaobaoTrustLoginTokenRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<TaobaoTrustLoginTokenResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new TaobaoTrustLoginTokenResponse();
            }
        });
    }

    public void cancelTaobaoTrustLoginToken() {
        if (this.mTaobaoTrustLoginTokenRequest != null) {
            in.a().a(this.mTaobaoTrustLoginTokenRequest);
            this.mTaobaoTrustLoginTokenRequest = null;
        }
    }
}
