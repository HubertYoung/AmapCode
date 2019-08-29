package defpackage;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dqa reason: default package */
/* compiled from: OrderHotelParserByCategory */
public final class dqa {
    public static ArrayList<dpl> a(JSONArray jSONArray) {
        ArrayList<dpl> arrayList = new ArrayList<>();
        if (jSONArray != null) {
            for (int i = 0; i < jSONArray.length(); i++) {
                try {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    dpz dpz = new dpz();
                    if (jSONObject != null) {
                        dpz.a = jSONObject.optInt("status");
                        dpz.b = jSONObject.optString("oid");
                        dpz.l = jSONObject.optString("src_type");
                        dpz.m = jSONObject.optString("type");
                        JSONObject optJSONObject = jSONObject.optJSONObject("summary");
                        if (optJSONObject != null) {
                            dpz.d = optJSONObject.optString("leaveDate");
                            dpz.g = optJSONObject.optString("hotelName");
                            dpz.e = optJSONObject.optString("comeDate");
                            dpz.c = optJSONObject.optString("serialId");
                        }
                    }
                    arrayList.add(dpz);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return arrayList;
    }
}
