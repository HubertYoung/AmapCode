package defpackage;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: eid reason: default package */
/* compiled from: OpenSubWayPageAction */
public class eid extends vz {
    private static int a = 2;

    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        if (jSONObject != null && a == jSONObject.getInt("pageType")) {
            eko.a(20045);
        }
    }
}
