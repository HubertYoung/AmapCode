package defpackage;

import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IThirdAuth.a;

/* renamed from: ddo reason: default package */
/* compiled from: ShareToWX */
public final class ddo {
    private static ddo a;

    private ddo() {
    }

    public static ddo a() {
        if (a == null) {
            a = new ddo();
        }
        return a;
    }

    public static void b() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            a a2 = iAccountService.c().a();
            if (a2 != null) {
                a2.a();
            }
        }
    }
}
