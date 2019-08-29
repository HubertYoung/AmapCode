package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.bundle.account.api.IAccountService;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: anz reason: default package */
/* compiled from: LogoutAction */
public class anz extends vz {
    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        JsAdapter a = a();
        if (a != null) {
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                iAccountService.d();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("_action", waVar.b);
                jSONObject2.put("status", 1);
                a.callJs(waVar.a, jSONObject2.toString());
            }
        }
    }
}
