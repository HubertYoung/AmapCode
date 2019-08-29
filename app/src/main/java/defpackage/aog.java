package defpackage;

import com.autonavi.bundle.account.api.IAccountService.AccountType;

/* renamed from: aog reason: default package */
/* compiled from: AccountThirdPartyModel */
public final class aog {
    public static aor a(AccountType accountType) {
        switch (accountType) {
            case Weixin:
                return new aou();
            case Sina:
                return new aot();
            case Taobao:
                return new aoo();
            case Alipay:
                return new aok();
            case QQ:
                return new aom();
            default:
                return null;
        }
    }

    public static void a(AccountType accountType, String str, aox aox, boolean z) {
        aor a = a(accountType);
        if (a instanceof aok) {
            ((aok) a).d = aoq.a;
        }
        if (a != null) {
            a.a(str, aox, z);
        } else {
            aox.a(new Exception(""));
        }
    }

    public static void a(AccountType accountType, int i, aox aox) {
        aor a = a(accountType);
        if (a != null) {
            a.a(i, aox);
        } else {
            aox.a(new Exception(""));
        }
    }
}
