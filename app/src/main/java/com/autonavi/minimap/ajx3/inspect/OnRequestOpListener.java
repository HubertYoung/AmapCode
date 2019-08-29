package com.autonavi.minimap.ajx3.inspect;

import org.json.JSONObject;

public interface OnRequestOpListener {
    void onLoadingFinished(String str, String str2);

    void onRequestWillBeSend(String str, String str2, String str3, JSONObject jSONObject, String str4, String str5);

    void onResponseReceived(String str, String str2, int i, String str3, JSONObject jSONObject, JSONObject jSONObject2, String str4, String str5);
}
