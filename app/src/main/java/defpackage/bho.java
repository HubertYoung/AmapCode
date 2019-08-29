package defpackage;

import com.alipay.mobile.security.bio.api.BioDetector;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bho reason: default package */
/* compiled from: PaymentType */
public final class bho {
    public String a;
    public String b;
    public String c;

    private bho() {
    }

    public static List<bho> a(JSONObject jSONObject) {
        List<bho> list = null;
        if (!jSONObject.has("typelist")) {
            return null;
        }
        try {
            ArrayList arrayList = new ArrayList();
            try {
                JSONArray jSONArray = jSONObject.getJSONArray("typelist");
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    String string = jSONObject2.getString("desc");
                    String string2 = jSONObject2.getString("typecode");
                    String string3 = jSONObject2.getString(BioDetector.EXT_KEY_AMOUNT);
                    bho bho = new bho();
                    bho.a = string;
                    bho.b = string2;
                    bho.c = string3;
                    arrayList.add(bho);
                }
                return arrayList;
            } catch (JSONException e) {
                e = e;
                list = arrayList;
                e.printStackTrace();
                return list;
            }
        } catch (JSONException e2) {
            e = e2;
            e.printStackTrace();
            return list;
        }
    }
}
