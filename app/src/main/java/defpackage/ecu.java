package defpackage;

import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ecu reason: default package */
/* compiled from: FootFavoriteUtil */
public final class ecu {
    public static String a(String str) {
        String a = ebk.a();
        cos b = b(str);
        if (b == null) {
            return null;
        }
        com com2 = (com) ank.a(com.class);
        if (com2 != null) {
            coq a2 = com2.a(a);
            if (a2 != null) {
                cor b2 = a2.b(b);
                if (b2 != null) {
                    return b2.f();
                }
            }
        }
        return null;
    }

    public static cos b(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            POI a = bnx.a(jSONObject.getString("startPoi"));
            POI a2 = bnx.a(jSONObject.getString("endPoi"));
            String addr = eay.a(R.string.my_location).equalsIgnoreCase(a.getName()) ? a.getAddr() : a.getName();
            String addr2 = eay.a(R.string.my_location).equalsIgnoreCase(a2.getName()) ? a2.getAddr() : a2.getName();
            a.setName(addr);
            a2.setName(addr2);
            cos cos = new cos();
            cos.a = 3;
            cos.l = null;
            cos.j = jSONObject.optInt("routeLength");
            cos.p = jSONObject.optInt("costTime");
            cos.f = "0";
            cos.m = a;
            cos.b = a.getPoint().x;
            cos.c = a.getPoint().y;
            cos.n = a2;
            cos.d = a2.getPoint().x;
            cos.e = a2.getPoint().y;
            StringBuilder sb = new StringBuilder();
            sb.append(addr);
            sb.append("→");
            sb.append(addr2);
            cos.h = sb.toString();
            return cos;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
