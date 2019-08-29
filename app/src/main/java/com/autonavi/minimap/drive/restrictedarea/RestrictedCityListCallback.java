package com.autonavi.minimap.drive.restrictedarea;

import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

abstract class RestrictedCityListCallback implements AosResponseCallback {

    public static class a extends AbstractAOSParser {
        List<dhr> a;
        List<dhr> b;

        public final String getErrorDesc(int i) {
            return null;
        }

        public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
            try {
                JSONObject parseHeader = parseHeader(bArr);
                if (parseHeader != null) {
                    this.result = parseHeader.optBoolean("result");
                    if (this.result) {
                        JSONObject optJSONObject = parseHeader.optJSONObject("data");
                        this.a = a(optJSONObject.optJSONArray("carlist"));
                        this.b = a(optJSONObject.optJSONArray("trucklist"));
                    }
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }

        private static List<dhr> a(JSONArray jSONArray) {
            ArrayList arrayList = new ArrayList();
            if (jSONArray != null && jSONArray.length() > 0) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject optJSONObject = jSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        arrayList.add(new dhr(optJSONObject.optString("name"), optJSONObject.optString(AutoJsonUtils.JSON_ADCODE), optJSONObject.optString(AutoJsonUtils.JSON_PINYIN)));
                    }
                }
            }
            return arrayList;
        }
    }

    RestrictedCityListCallback() {
    }
}
