package com.autonavi.minimap.provider;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.provider.param.InsuranceTokenRequest;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class ProviderRequestHolder {
    private static volatile ProviderRequestHolder instance;

    private ProviderRequestHolder() {
    }

    public static ProviderRequestHolder getInstance() {
        if (instance == null) {
            synchronized (ProviderRequestHolder.class) {
                if (instance == null) {
                    instance = new ProviderRequestHolder();
                }
            }
        }
        return instance;
    }

    public void sendInsuranceToken(InsuranceTokenRequest insuranceTokenRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendInsuranceToken(insuranceTokenRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendInsuranceToken(InsuranceTokenRequest insuranceTokenRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            insuranceTokenRequest.addHeaders(dkn.d);
            insuranceTokenRequest.setTimeout(dkn.b);
            insuranceTokenRequest.setRetryTimes(dkn.c);
        }
        insuranceTokenRequest.setUrl(InsuranceTokenRequest.a);
        insuranceTokenRequest.addSignParam("channel");
        if (dkn != null) {
            in.a().a((AosRequest) insuranceTokenRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) insuranceTokenRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
