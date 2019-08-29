package com.autonavi.minimap.account.alipay;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.account.alipay.model.AlipayAuthorizeInfoResponse;
import com.autonavi.minimap.account.alipay.param.AlipayAuthorizeInfoParam;
import com.autonavi.minimap.falcon.base.FalconAosHttpResponseCallBack;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class AlipayAuthorizeRequestHolder {
    private static volatile AlipayAuthorizeRequestHolder instance;
    private AosRequest mAlipayAuthorizeInfoRequest;

    private AlipayAuthorizeRequestHolder() {
    }

    public static AlipayAuthorizeRequestHolder getInstance() {
        if (instance == null) {
            synchronized (AlipayAuthorizeRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new AlipayAuthorizeRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendAlipayAuthorizeInfo(AlipayAuthorizeInfoParam alipayAuthorizeInfoParam, dko<AlipayAuthorizeInfoResponse> dko) {
        this.mAlipayAuthorizeInfoRequest = new AosGetRequest();
        AosRequest aosRequest = this.mAlipayAuthorizeInfoRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/provider/alipay/authorize-info/");
        aosRequest.setUrl(sb.toString());
        this.mAlipayAuthorizeInfoRequest.addSignParam("channel");
        this.mAlipayAuthorizeInfoRequest.addSignParam(LocationParams.PARA_COMMON_ADIU);
        this.mAlipayAuthorizeInfoRequest.addReqParam(LocationParams.PARA_COMMON_ADIU, alipayAuthorizeInfoParam.adiu);
        in.a().a(this.mAlipayAuthorizeInfoRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<AlipayAuthorizeInfoResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new AlipayAuthorizeInfoResponse();
            }
        });
    }

    public void cancelAlipayAuthorizeInfo() {
        if (this.mAlipayAuthorizeInfoRequest != null) {
            in.a().a(this.mAlipayAuthorizeInfoRequest);
            this.mAlipayAuthorizeInfoRequest = null;
        }
    }
}
