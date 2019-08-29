package defpackage;

import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.account.api.IAccountService;
import java.io.File;
import java.util.Iterator;

@BundleInterface(atf.class)
/* renamed from: atk reason: default package */
/* compiled from: CarLogoService */
public class atk extends esi implements atf {
    public final void a(String str, String str2, String str3, String str4) {
        ati.a(str, str2, str4, "normalType", new ath(str, "normalType", str4));
        ati.a(str, str3, str4, "weakType", new ath(str, "weakType", str4));
    }

    public final atj a() {
        String str;
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            str = "";
        } else {
            ant e = iAccountService.e();
            if (e == null) {
                str = "";
            } else {
                str = e.a;
            }
        }
        if (str == null) {
            str = "public";
        }
        String a = agy.a(str);
        IAccountService iAccountService2 = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService2 == null || !iAccountService2.a() || a == null) {
            return null;
        }
        Iterator<atj> it = atg.a(atg.a()).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            atj next = it.next();
            if (next.c.equals(a)) {
                if (next.d && "0".equals(next.a)) {
                    break;
                } else if (next.d) {
                    if (new File(next.g).exists() && new File(next.f).exists()) {
                        return next;
                    }
                }
            }
        }
        return null;
    }
}
