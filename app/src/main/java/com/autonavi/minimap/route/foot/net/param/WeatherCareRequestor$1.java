package com.autonavi.minimap.route.foot.net.param;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherCareRequestor$1 extends FalconAosPrepareResponseCallback<String> {
    final /* synthetic */ a a;

    public WeatherCareRequestor$1(a aVar) {
        this.a = aVar;
    }

    public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
        return b(aosByteResponse);
    }

    public final /* synthetic */ void a(Object obj) {
        String str = (String) obj;
        if (this.a != null) {
            if (TextUtils.isEmpty(str)) {
                this.a.a();
                return;
            }
            try {
                this.a.a(new JSONObject(str).optString("weather_condition", ""));
            } catch (JSONException e) {
                this.a.a();
                e.printStackTrace();
            }
        }
    }

    private static String b(AosByteResponse aosByteResponse) {
        String responseBodyString = aosByteResponse.getResponseBodyString();
        eao.a((String) "WeatherRequest", "moji: ".concat(String.valueOf(responseBodyString)));
        try {
            if (new JSONObject(responseBodyString).optInt("code", 0) == 1) {
                return responseBodyString;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.a != null) {
            a aVar = this.a;
            aosResponseException.getMessage();
            aVar.a();
        }
    }
}
