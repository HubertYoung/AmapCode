package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.jsaction.SetNoPasswordForAlipay$1;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dnf reason: default package */
/* compiled from: SetNoPasswordForAlipay */
public class dnf extends vz {
    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        boolean z;
        JsAdapter a = a();
        if (a != null) {
            final String optString = jSONObject != null ? jSONObject.optString("_action", "") : "";
            final SetNoPasswordForAlipay$1 setNoPasswordForAlipay$1 = new SetNoPasswordForAlipay$1(this, a, waVar);
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService == null) {
                z = false;
            } else {
                z = iAccountService.a();
            }
            if (!z) {
                JSONObject b = b(optString, "14");
                if (b != null) {
                    setNoPasswordForAlipay$1.callback(b);
                }
            } else if (!aaw.c(AMapPageUtil.getAppContext())) {
                JSONObject b2 = b(optString, "0");
                if (b2 != null) {
                    setNoPasswordForAlipay$1.callback(b2);
                }
            } else {
                fhg.a().a(new ans<String>() {
                    public final /* synthetic */ void a(Object obj) {
                        JSONObject a2 = dnf.b(optString, (String) obj);
                        if (a2 != null) {
                            setNoPasswordForAlipay$1.callback(a2);
                        }
                    }
                }, DoNotUseTool.getActivity());
            }
        }
    }

    /* access modifiers changed from: private */
    public static JSONObject b(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("_action", str);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("code", str2);
            jSONObject.put("content", jSONObject2);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
