package com.autonavi.minimap.ajx3.modules;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;

public class ReverseCallback implements AosResponseCallback<ReverseGeocodeResponse> {
    JsFunctionCallback mJsCallBack;

    public ReverseCallback(JsFunctionCallback jsFunctionCallback) {
        this.mJsCallBack = jsFunctionCallback;
    }

    public void onSuccess(ReverseGeocodeResponse reverseGeocodeResponse) {
        String str = "";
        if (reverseGeocodeResponse != null) {
            str = (String) reverseGeocodeResponse.getResult();
        }
        if (this.mJsCallBack != null) {
            this.mJsCallBack.callback(str);
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.mJsCallBack != null) {
            this.mJsCallBack.callback("");
        }
    }
}
