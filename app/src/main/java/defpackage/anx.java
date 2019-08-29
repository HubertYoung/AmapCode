package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: anx reason: default package */
/* compiled from: IsAlipayBound */
public class anx extends vz {
    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        JsAdapter a = a();
        if (a != null) {
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                String str = iAccountService.a(AccountType.Alipay) ? "1" : "0";
                try {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("status", str);
                    jSONObject2.put("_action", waVar.b);
                    a.callJs(waVar.a, jSONObject2.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
