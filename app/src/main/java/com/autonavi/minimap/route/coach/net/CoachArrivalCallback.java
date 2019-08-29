package com.autonavi.minimap.route.coach.net;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.common.Callback;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public class CoachArrivalCallback extends CoachArrivalCacheCallback<JSONObject> {
    public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
        return b(aosByteResponse);
    }

    public final /* synthetic */ void a(Object obj) {
        JSONObject jSONObject = (JSONObject) obj;
        if (this.a != null) {
            this.a.callback(jSONObject);
        }
    }

    public CoachArrivalCallback(Callback<JSONObject> callback, String str) {
        super(callback, str);
    }

    private static JSONObject b(AosByteResponse aosByteResponse) {
        JSONObject jSONObject;
        byte[] bArr = (byte[]) aosByteResponse.getResult();
        if (bArr != null && bArr.length > 0) {
            try {
                jSONObject = new JSONObject(new String(bArr, "UTF-8").trim());
            } catch (UnsupportedEncodingException | JSONException e) {
                e.printStackTrace();
            }
            eao.a((String) "CoachStation", String.valueOf(jSONObject));
            return jSONObject;
        }
        jSONObject = null;
        eao.a((String) "CoachStation", String.valueOf(jSONObject));
        return jSONObject;
    }

    public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.a != null) {
            this.a.error(aosResponseException, aosResponseException != null && aosResponseException.isCallbackError);
        }
    }
}
