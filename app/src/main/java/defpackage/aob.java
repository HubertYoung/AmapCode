package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import org.json.JSONObject;

/* renamed from: aob reason: default package */
/* compiled from: TaoIfLoginAction */
public class aob extends vz {
    public final void a(JSONObject jSONObject, final wa waVar) {
        final JsAdapter a = a();
        if (a != null) {
            AnonymousClass1 r0 = new anq() {
                public final void loginOrBindCancel() {
                }

                public final void onComplete(boolean z) {
                    if (z) {
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("_action", waVar.b);
                            String b2 = aob.b();
                            jSONObject.put("taoId", b2);
                            jSONObject.put("taoToken", b2);
                            a.callJs(waVar.a, jSONObject.toString());
                        } catch (Exception e) {
                            kf.a((Throwable) e);
                        }
                    }
                }
            };
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                if (!iAccountService.a()) {
                    IAccountService iAccountService2 = (IAccountService) a.a.a(IAccountService.class);
                    if (iAccountService2 != null) {
                        iAccountService2.a(AccountType.Taobao, (anq) r0);
                    }
                } else if (iAccountService.a(AccountType.Taobao)) {
                    r0.onComplete(true);
                } else {
                    IAccountService iAccountService3 = (IAccountService) a.a.a(IAccountService.class);
                    if (iAccountService3 != null) {
                        iAccountService3.a(AMapPageUtil.getPageContext(), AccountType.Taobao, (anq) r0);
                    }
                }
            }
        }
    }

    static /* synthetic */ String b() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        return e.k;
    }
}
