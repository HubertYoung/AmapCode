package defpackage;

import com.autonavi.map.search.data.DateEntity;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dqb reason: default package */
/* compiled from: OrderHotelParserNew */
public final class dqb {
    public static ArrayList<dpl> a(JSONArray jSONArray) {
        ArrayList<dpl> arrayList = new ArrayList<>();
        if (jSONArray != null) {
            for (int i = 0; i < jSONArray.length(); i++) {
                try {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    dpz dpz = new dpz();
                    if (jSONObject != null) {
                        dpz.n = true;
                        dpz.a = jSONObject.optInt("status");
                        dpz.b = jSONObject.optString("order_id");
                        dpz.d = jSONObject.optString("date_leave");
                        dpz.g = jSONObject.optString("hotel_name");
                        dpz.e = jSONObject.optString("date_enter");
                        dpz.f = jSONObject.optString("room_number");
                        dpz.i = jSONObject.optString("hotel_phone");
                        dpz.h = jSONObject.optString("hotel_address");
                        dpz.k = jSONObject.optString("latitude");
                        dpz.j = jSONObject.optString("longitude");
                        dpz.l = jSONObject.optString("cp_source");
                        dpz.m = DateEntity.DATETYPE_HOTEL;
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
