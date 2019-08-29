package com.autonavi.minimap.ajx3.modules;

import com.amap.bundle.aosservice.response.AosResponse;

public class ReverseGeocodeResponse extends AosResponse<String> {
    /* access modifiers changed from: protected */
    public String parseResult() {
        byte[] responseBodyData = getResponseBodyData();
        if (responseBodyData != null) {
            try {
                return new String(responseBodyData, "UTF-8");
            } catch (Exception unused) {
            }
        }
        return "";
    }
}
