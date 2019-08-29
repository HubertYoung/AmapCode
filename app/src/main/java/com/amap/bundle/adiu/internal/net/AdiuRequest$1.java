package com.amap.bundle.adiu.internal.net;

import android.text.TextUtils;
import android.util.Log;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.aosservice.response.AosStringResponse;
import com.amap.bundle.logs.AMapLog;
import com.amap.location.sdk.fusion.LocationParams;
import org.json.JSONException;
import org.json.JSONObject;

public class AdiuRequest$1 implements AosResponseCallback<AosStringResponse> {
    final /* synthetic */ im a;

    public AdiuRequest$1(im imVar) {
        this.a = imVar;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        String str = (String) ((AosStringResponse) aosResponse).getResult();
        if (bno.a) {
            AMapLog.debug("paas.adiu", "AdiuRequest", "onSuccess:".concat(String.valueOf(str)));
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt("code") == 1) {
                String optString = new JSONObject(jSONObject.optString("data")).optString(LocationParams.PARA_COMMON_ADIU);
                if (!TextUtils.isEmpty(optString) && this.a.a != null) {
                    this.a.a.a(optString);
                }
                return;
            }
            AMapLog.error("paas.adiu", "AdiuRequest", str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("onFailure:");
            sb.append(Log.getStackTraceString(aosResponseException));
            AMapLog.debug("paas.adiu", "AdiuRequest", sb.toString());
        }
        aosResponseException.printStackTrace();
    }
}
