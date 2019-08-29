package com.amap.bundle.headunit;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import org.json.JSONObject;

public class HeadunitServiceImpl$4 implements AosResponseCallbackOnUi<AosByteResponse> {
    final /* synthetic */ vo a;
    final /* synthetic */ vm b;

    public HeadunitServiceImpl$4(vm vmVar, vo voVar) {
        this.b = vmVar;
        this.a = voVar;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        if (aosByteResponse == null || aosByteResponse.getResult() == null) {
            this.a.onError("", "");
            return;
        }
        try {
            String str = new String((byte[]) aosByteResponse.getResult(), "UTF-8");
            if (TextUtils.isEmpty(str)) {
                this.a.onError("", "");
                return;
            }
            JSONObject jSONObject = new JSONObject(str);
            StringBuilder sb = new StringBuilder("sendPoiToHeadunitByAos  AMapHttpSDK.post  callback:");
            sb.append(jSONObject.toString());
            vl.a("HeadunitServiceImpl", sb.toString());
            if (this.a != null) {
                String optString = jSONObject.optString("code");
                if ("1".equals(optString)) {
                    this.a.onSuccess(1);
                } else {
                    this.a.onError(optString, jSONObject.optString("message"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        vl.a("HeadunitServiceImpl", "sendPoiToHeadunitByAos  AMapHttpSDK.post  error: ".concat(String.valueOf(aosResponseException)) != null ? aosResponseException.getMessage() : "");
        if (this.a != null) {
            this.a.onError("", "");
        }
    }
}
