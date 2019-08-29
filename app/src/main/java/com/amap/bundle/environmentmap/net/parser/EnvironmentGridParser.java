package com.amap.bundle.environmentmap.net.parser;

import android.text.TextUtils;
import com.amap.bundle.network.response.AosParserResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class EnvironmentGridParser extends AosParserResponse {
    public byte[] a = null;

    public final String b() {
        return null;
    }

    /* renamed from: a */
    public final byte[] parseResult() {
        byte[] a2 = super.parseResult();
        try {
            if ("1".equals(this.k.optString("code"))) {
                JSONObject jSONObject = this.k.getJSONObject("data");
                if (jSONObject == null) {
                    return a2;
                }
                String optString = jSONObject.optString("grid");
                if (!TextUtils.isEmpty(optString)) {
                    this.a = agv.a(optString);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return a2;
    }
}
