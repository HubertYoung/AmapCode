package com.amap.bundle.schoolbus.request;

import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public class SchoolbusStatusRequest$1 implements AosResponseCallbackOnUi<AosByteResponse> {
    final /* synthetic */ ado a;

    public SchoolbusStatusRequest$1(ado ado) {
        this.a = ado;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        adq adq = new adq(this.a.d);
        try {
            adq.parser(aosByteResponse.getResponseBodyData());
        } catch (UnsupportedEncodingException e) {
            kf.a((Throwable) e);
        } catch (JSONException e2) {
            kf.a((Throwable) e2);
        }
        if (adq.a != null) {
            adk adk = adq.a;
            StringBuilder sb = new StringBuilder("[");
            sb.append(this.a.g);
            sb.append("] onSuccess: ");
            sb.append(adk.c);
            sb.append("-");
            sb.append(adk.b);
            ads.a("SchoolBus", sb.toString());
            if (!(this.a.a == null || adk == null || !adl.a().a.equals(adk.c))) {
                this.a.a.callback(adk.b);
            }
            if (this.a.b != null) {
                this.a.b.a(adk);
            }
        } else {
            StringBuilder sb2 = new StringBuilder("[");
            sb2.append(this.a.g);
            sb2.append("] onSuccess: baseResponse is null");
            ads.a("SchoolBus", sb2.toString());
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        ads.a("schoolbus_polling", "onFailure");
        JSONObject jSONObject = new JSONObject();
        if (this.a.b != null) {
            adk adk = new adk();
            adk.c = this.a.d;
            adk.d = -1;
            this.a.b.a(adk);
        }
        try {
            jSONObject.put("message", UserTrackerConstants.EM_NETWORK_ERROR);
            jSONObject.put("code", 0);
            jSONObject.put("status", 0);
            if (this.a.a != null) {
                this.a.a.callback(jSONObject.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        StringBuilder sb = new StringBuilder("[");
        sb.append(this.a.g);
        sb.append("] onFail: ");
        sb.append(jSONObject.toString());
        ads.a("SchoolBus", sb.toString());
    }
}
