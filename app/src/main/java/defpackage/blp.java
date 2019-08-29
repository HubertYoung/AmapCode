package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: blp reason: default package */
/* compiled from: SetGobackStepAction */
public class blp extends vz {
    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        if (jSONObject != null) {
            int optInt = jSONObject.optInt("step");
            JsAdapter a = a();
            if (optInt > 0 && a != null) {
                a.getBundle().putInt("gobackStep", optInt);
            }
        }
    }
}
