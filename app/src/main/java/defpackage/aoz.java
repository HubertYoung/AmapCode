package defpackage;

import android.content.Context;
import com.alipay.android.phone.inside.api.model.accountopenauth.MCAccountStatusEnum;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import com.autonavi.minimap.account.unbind.model.UnbindResponse;
import mtopsdk.mtop.intf.Mtop;

/* renamed from: aoz reason: default package */
/* compiled from: UnbindCallback */
public class aoz implements dko<UnbindResponse> {
    private AccountType a;

    public void a(Exception exc) {
    }

    public aoz(AccountType accountType) {
        this.a = accountType;
    }

    public void a(UnbindResponse unbindResponse) {
        if (unbindResponse != null) {
            if (unbindResponse.code == 1 || unbindResponse.result) {
                aos.a();
                a(this.a);
                ant b = aoe.a().b();
                if (b != null) {
                    a.a.a(b);
                }
                return;
            }
            if (unbindResponse.code == 14) {
                aoy.a();
            }
        }
    }

    public static void a(AccountType accountType) {
        if (accountType != null) {
            switch (accountType) {
                case Email:
                    a();
                    return;
                case Mobile:
                    b();
                    return;
                case Weixin:
                    c();
                    return;
                case QQ:
                    d();
                    return;
                case Sina:
                    e();
                    return;
                case Alipay:
                    f();
                    ajp.c(AMapAppGlobal.getApplication().getApplicationContext());
                    aok.a(MCAccountStatusEnum.MC_UNBIND_ALIPAY);
                    Mtop.a((Context) AMapAppGlobal.getApplication()).c();
                    return;
                case Taobao:
                    g();
                    aoo.b();
                    break;
            }
        }
    }

    private static synchronized void a() {
        synchronized (aoz.class) {
            ant b = aoe.a().b();
            if (b != null) {
                b.g = "";
                aoe.a().a(b);
            }
        }
    }

    private static void b() {
        ant b = aoe.a().b();
        if (b != null) {
            b.h = "";
            aoe.a().a(b);
        }
    }

    private static synchronized void c() {
        synchronized (aoz.class) {
            ant b = aoe.a().b();
            if (b != null) {
                b.p = "";
                b.q = "";
                aoe.a().a(b);
            }
        }
    }

    private static synchronized void d() {
        synchronized (aoz.class) {
            ant b = aoe.a().b();
            if (b != null) {
                b.n = "";
                b.o = "";
                aoe.a().a(b);
            }
        }
    }

    private static synchronized void e() {
        synchronized (aoz.class) {
            ant b = aoe.a().b();
            if (b != null) {
                b.i = "";
                b.j = "";
                aoe.a().a(b);
            }
        }
    }

    private static synchronized void f() {
        synchronized (aoz.class) {
            ant b = aoe.a().b();
            if (b != null) {
                b.r.clear();
                b.s = "";
                b.t = "";
                b.u = "";
                aoe.a().a(b);
            }
        }
    }

    private static synchronized void g() {
        synchronized (aoz.class) {
            ant b = aoe.a().b();
            if (b != null) {
                b.k = "";
                b.m = "";
                b.l = "";
                aoe.a().a(b);
            }
        }
    }
}
