package defpackage;

import com.autonavi.minimap.bundle.activities.jsaction.FetchActivityAction$1;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cto reason: default package */
/* compiled from: FetchActivityAction */
public class cto extends vz {
    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        String optString = jSONObject.optString("type");
        String optString2 = jSONObject.optString("_action");
        ctl ctl = (ctl) a.a.a(ctl.class);
        if (ctl != null) {
            ctl.a(optString, new FetchActivityAction$1(this, optString2, waVar));
        }
    }
}
