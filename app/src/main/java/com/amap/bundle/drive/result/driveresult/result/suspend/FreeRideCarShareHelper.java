package com.amap.bundle.drive.result.driveresult.result.suspend;

import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public final class FreeRideCarShareHelper {

    public static class FreeRideCarShareResponse extends AosResponse<b> {
        /* access modifiers changed from: private */
        /* renamed from: a */
        public b parseResult() {
            b bVar = new b();
            byte[] responseBodyData = getResponseBodyData();
            if (responseBodyData == null) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject(new String(responseBodyData, "UTF-8"));
                bVar.a = jSONObject.optBoolean("result");
                bVar.b = jSONObject.optInt("code");
                bVar.d = jSONObject.optLong("timestamp");
                bVar.c = jSONObject.optString("message");
                bVar.e = jSONObject.optString("version");
                JSONObject optJSONObject = jSONObject.optJSONObject("data");
                if (optJSONObject != null) {
                    bVar.f.b = optJSONObject.optInt("total");
                    bVar.f.a = optJSONObject.optString("showText");
                    JSONArray jSONArray = optJSONObject.getJSONArray("imageList");
                    if (jSONArray != null && jSONArray.length() > 0) {
                        for (int i = 0; i < jSONArray.length(); i++) {
                            bVar.f.c.add(jSONArray.get(i).toString());
                        }
                    }
                }
            } catch (Exception unused) {
                bVar = null;
            }
            return bVar;
        }
    }

    @Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"tid", "adiu", "startLat", "startLng"}, url = "ws/travel/car-share/recommend/plan?")
    public static class FreeRideCardShareWrapper implements ParamEntity {
        public String endAdcode;
        public String endLat;
        public String endLng;
        public String startAdcode;
        public String startLat;
        public String startLng;
    }

    public static class a {
        public String a = "";
        public int b = 0;
        public List<String> c = new ArrayList();
    }

    public static class b {
        public boolean a;
        public int b;
        public String c;
        public long d;
        public String e;
        public a f = new a();
    }
}
