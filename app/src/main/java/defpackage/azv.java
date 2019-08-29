package defpackage;

import android.graphics.Color;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: azv reason: default package */
/* compiled from: EtaEtdRestricResponse */
public final class azv extends dkm {
    public azu a = new azu();

    public final void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            JSONObject optJSONObject = jSONObject.optJSONObject("data");
            if (optJSONObject != null) {
                azu azu = new azu();
                if (optJSONObject != null) {
                    JSONArray optJSONArray = optJSONObject.optJSONArray("info");
                    if (optJSONArray != null && optJSONArray.length() > 0) {
                        for (int i = 0; i < optJSONArray.length(); i++) {
                            JSONObject optJSONObject2 = optJSONArray.optJSONObject(i);
                            if (optJSONObject2 != null) {
                                azt azt = new azt();
                                if (optJSONObject2 != null) {
                                    azt.a = optJSONObject2.optString("distance");
                                    azt.b = optJSONObject2.optInt("travel_time");
                                    JSONArray optJSONArray2 = optJSONObject2.optJSONArray("detail");
                                    if (optJSONArray2 != null && optJSONArray2.length() > 0) {
                                        for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                                            JSONObject optJSONObject3 = optJSONArray2.optJSONObject(i2);
                                            if (optJSONObject3 != null) {
                                                azx azx = new azx();
                                                if (optJSONObject3 != null) {
                                                    azx.a = optJSONObject3.optInt("status");
                                                    azx.b = optJSONObject3.optDouble("ratio");
                                                    StringBuilder sb = new StringBuilder(MetaRecord.LOG_SEPARATOR);
                                                    sb.append(optJSONObject3.optString("color"));
                                                    azx.c = Color.parseColor(sb.toString());
                                                }
                                                azt.c.add(azx);
                                            }
                                        }
                                    }
                                }
                                azu.a.add(azt);
                            }
                        }
                    }
                    azu.b = optJSONObject.optInt("restrict");
                    azu.c = optJSONObject.optString("etdinfo");
                }
                this.a = azu;
            }
        }
    }

    public final JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        azu azu = this.a;
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        if (azu.a != null && azu.a.size() > 0) {
            for (int i = 0; i < azu.a.size(); i++) {
                jSONArray.put(azu.a.get(i).a());
            }
        }
        jSONObject.put("info", jSONArray);
        jSONObject.put("restrict", azu.b);
        jSONObject.put("etdinfo", azu.c);
        json.put("data", jSONObject);
        return json;
    }
}
