package com.autonavi.minimap.basemap.save.controller;

import android.text.TextUtils;
import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.response.AbstractAOSParser;
import org.json.JSONException;
import org.json.JSONObject;

public class SaveSyncAutoDataController$1 implements AosResponseCallbackOnUi<AosByteResponse> {
    final /* synthetic */ a a;
    final /* synthetic */ cra b;

    public SaveSyncAutoDataController$1(cra cra, a aVar) {
        this.b = cra;
        this.a = aVar;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        if (aosByteResponse != null && aosByteResponse.getResult() != null) {
            try {
                JSONObject aosByteResponseToJSONObject = AbstractAOSParser.aosByteResponseToJSONObject(aosByteResponse);
                boolean optBoolean = aosByteResponseToJSONObject.optBoolean("result", false);
                int optInt = aosByteResponseToJSONObject.optInt("code", 0);
                String optString = aosByteResponseToJSONObject.optString("message", "");
                String optString2 = aosByteResponseToJSONObject.optString("copywrite", "");
                String optString3 = aosByteResponseToJSONObject.optString(ActionConstant.SCHEMA, "");
                if (!optBoolean || optInt != 1 || TextUtils.isEmpty(optString2) || TextUtils.isEmpty(optString3)) {
                    StringBuilder sb = new StringBuilder("Code:");
                    sb.append(optInt);
                    sb.append(";message:");
                    sb.append(optString);
                    AMapLog.w("saveSyncAutoDataController", sb.toString());
                    return;
                }
                this.a.a(optString2, optString3);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        AMapLog.e("SaveSyncAutoDataController", aosResponseException.getLocalizedMessage());
    }
}
