package defpackage;

import com.alipay.android.phone.inside.api.model.accountopenauth.MCAccountStatusEnum;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.minimap.account.logout.model.LogoutResponse;

/* renamed from: aoy reason: default package */
/* compiled from: LogoutCallback */
public class aoy implements dko<LogoutResponse> {
    public void a(Exception exc) {
    }

    public void a(LogoutResponse logoutResponse) {
        if (logoutResponse != null) {
            if (logoutResponse.code == 1 || logoutResponse.result || logoutResponse.code == 14 || logoutResponse.code == 24 || logoutResponse.code == 32) {
                a();
            }
        }
    }

    public static void a() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        boolean z = iAccountService != null && iAccountService.a();
        aos.a();
        aoe.a().c();
        aoo.b();
        bim.aa().B();
        ajp.c(AMapAppGlobal.getApplication());
        if (z) {
            aok.a(MCAccountStatusEnum.MC_LOGOUT);
            a.a.a(true, false);
        }
    }
}
