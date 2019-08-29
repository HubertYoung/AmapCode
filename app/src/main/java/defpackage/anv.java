package defpackage;

import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: anv reason: default package */
/* compiled from: AlipayAuthAction */
public class anv extends vz {
    public final void a(JSONObject jSONObject, final wa waVar) throws JSONException {
        final JsAdapter a = a();
        if (a != null) {
            final JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("_action", waVar.b);
            String optString = jSONObject.optString("scope");
            if (TextUtils.isEmpty(optString)) {
                jSONObject2.put("error", "params error");
                a.callJs(waVar.a, jSONObject2.toString());
                return;
            }
            aok aok = new aok();
            if (!TextUtils.isEmpty(optString)) {
                aok.a(Arrays.asList(optString.split("\\|\\|\\|")));
            }
            aol.a().a(aok.c(), aok.b(), aok.d(), aok.b, aok.c, (a) new a() {
                public final void a() {
                    try {
                        jSONObject2.put("error", "cancel");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    a.callJs(waVar.a, jSONObject2.toString());
                }

                public final void a(boolean z, aoh aoh) {
                    if (z) {
                        try {
                            jSONObject2.put("auth_code", aoh.b);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        a.callJs(waVar.a, jSONObject2.toString());
                        return;
                    }
                    try {
                        jSONObject2.put("error", "auth failed");
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    a.callJs(waVar.a, jSONObject2.toString());
                }
            });
        }
    }
}
