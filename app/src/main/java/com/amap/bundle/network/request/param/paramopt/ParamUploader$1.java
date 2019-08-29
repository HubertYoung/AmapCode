package com.amap.bundle.network.request.param.paramopt;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.aosservice.response.AosStringResponse;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.logs.AMapLog;
import org.json.JSONObject;

public class ParamUploader$1 implements AosResponseCallback<AosStringResponse> {
    final /* synthetic */ String a;
    final /* synthetic */ aap b;

    public ParamUploader$1(aap aap, String str) {
        this.b = aap;
        this.a = str;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        String str;
        try {
            str = (String) ((AosStringResponse) aosResponse).getResult();
            if (!TextUtils.isEmpty(str)) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.getInt("code") == 1) {
                        String string = jSONObject.getString("timestamp");
                        long currentTimeMillis = System.currentTimeMillis() + (jSONObject.getLong("valid_time") * 60 * 1000);
                        String b2 = aaf.b(ConfigerHelper.AOS_URL_KEY);
                        StringBuilder sb = new StringBuilder();
                        sb.append(b2);
                        sb.append("/api/parameter/upload");
                        String b3 = aaq.b(sb.toString());
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("ver", string);
                        jSONObject2.put("status", 0);
                        jSONObject2.put("ex_time", currentTimeMillis);
                        jSONObject2.put("pub_param", b3);
                        aao.d().b(jSONObject2.toString());
                    }
                } catch (Exception unused) {
                }
            }
        } catch (Exception e) {
            str = e.getMessage();
        }
        AMapLog.e("ParamUploader", "onFaided ".concat(String.valueOf(str)));
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        StringBuilder sb = new StringBuilder("onFaided ");
        sb.append(aosResponseException.getMessage());
        AMapLog.e("ParamUploader", sb.toString());
    }
}
