package defpackage;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ewe reason: default package */
/* compiled from: HParams */
public class ewe {
    private static final String b = "ewe";
    public JSONObject a = new JSONObject();

    public final void a(String str, String str2) {
        try {
            this.a.put(str, str2);
        } catch (JSONException e) {
            euw.a(e.getMessage());
        }
    }
}
