package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.bundle.account.api.IAccountService;
import org.json.JSONObject;

/* renamed from: aoa reason: default package */
/* compiled from: ShowLoginPannelAction */
public class aoa extends vz {
    public final void a(JSONObject jSONObject, final wa waVar) {
        final JsAdapter a = a();
        if (a != null) {
            final IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                String optString = jSONObject.optString("type");
                AnonymousClass1 r2 = new anq() {
                    public final void loginOrBindCancel() {
                    }

                    public final void onComplete(boolean z) {
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("_action", waVar.b);
                            jSONObject.put("userid", iAccountService.b());
                            a.callJs(waVar.a, jSONObject.toString());
                        } catch (Exception e) {
                            kf.a((Throwable) e);
                        }
                    }
                };
                JsAdapter a2 = a();
                if (a2 != null) {
                    "phone".equals(optString);
                    iAccountService.a(a2.mPageContext, (anq) r2);
                }
            }
        }
    }
}
