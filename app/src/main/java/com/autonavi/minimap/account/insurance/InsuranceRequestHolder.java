package com.autonavi.minimap.account.insurance;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.minimap.account.insurance.model.InsuranceTokenResponse;
import com.autonavi.minimap.account.insurance.param.InsuranceTokenParam;
import com.autonavi.minimap.falcon.base.FalconAosHttpResponseCallBack;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class InsuranceRequestHolder {
    private static volatile InsuranceRequestHolder instance;

    private InsuranceRequestHolder() {
    }

    public static InsuranceRequestHolder getInstance() {
        if (instance == null) {
            synchronized (InsuranceRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new InsuranceRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public AosRequest sendToken(InsuranceTokenParam insuranceTokenParam, dko<InsuranceTokenResponse> dko) {
        AosPostRequest aosPostRequest = new AosPostRequest();
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/provider/insurance-token/");
        aosPostRequest.setUrl(sb.toString());
        aosPostRequest.addSignParam("channel");
        aosPostRequest.addReqParam("env", insuranceTokenParam.env);
        in.a().a((AosRequest) aosPostRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<InsuranceTokenResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new InsuranceTokenResponse();
            }
        });
        return aosPostRequest;
    }

    public void cancelToken(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }
}
