package com.autonavi.minimap.bundle.activities.network;

import com.amap.bundle.network.response.AosParserResponse;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ActivityPreloadResponse extends AosParserResponse {
    public String a;
    public String l;
    public List<ctz> m;

    public final String b() {
        return "";
    }

    /* renamed from: a */
    public final byte[] parseResult() {
        super.parseResult();
        if (this.j) {
            this.a = this.k.optString("checksum");
            this.l = this.k.optString("title");
            JSONArray optJSONArray = this.k.optJSONArray("poilist");
            if (optJSONArray != null && optJSONArray.length() > 0) {
                this.m = new ArrayList();
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        this.m.add(new ctz(optJSONObject));
                    }
                }
            }
        }
        return (byte[]) getResult();
    }
}
