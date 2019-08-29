package defpackage;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ezr reason: default package */
/* compiled from: UnvarnishedMessage */
public final class ezr {
    public int a;
    public String b;
    public long c;
    private String d;
    private Map<String, String> e = new HashMap();

    public ezr() {
    }

    public ezr(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                fat.a((String) "UnvarnishedMessage", (String) "unvarnishedMsg pack to obj is null");
                return;
            }
            JSONArray jSONArray = new JSONArray(str);
            this.a = jSONArray.optInt(0);
            this.b = jSONArray.getString(1);
            this.d = jSONArray.getString(2);
            this.e = faq.a(new JSONObject(jSONArray.getString(3)));
        } catch (JSONException e2) {
            e2.printStackTrace();
            fat.a("UnvarnishedMessage", "unvarnishedMsg pack to obj error", e2);
        }
    }

    public final String a() {
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(this.a);
        jSONArray.put(this.b);
        jSONArray.put(this.d);
        jSONArray.put(this.e == null ? new HashMap() : this.e);
        return jSONArray.toString();
    }
}
