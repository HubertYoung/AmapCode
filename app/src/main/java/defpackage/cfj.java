package defpackage;

import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cfj reason: default package */
/* compiled from: WalletImpl */
public class cfj implements cpq {
    public final void a() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            if (iAccountService.a()) {
                cfl.a();
                return;
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("from", 5);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00400", "B001", jSONObject);
            iAccountService.a(AMapPageUtil.getPageContext(), (anq) new anq() {
                public final void loginOrBindCancel() {
                }

                public final void onComplete(boolean z) {
                    if (z) {
                        cfl.a();
                    }
                }
            });
        }
    }
}
