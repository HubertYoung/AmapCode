package defpackage;

import android.content.Intent;
import com.alipay.android.phone.zoloz.R;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IThirdAuth.b;
import com.autonavi.server.aos.serverkey;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelmsg.SendAuth.Resp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import java.util.ArrayList;
import java.util.List;

/* renamed from: aoj reason: default package */
/* compiled from: AmapWxApi */
public final class aoj implements com.autonavi.bundle.account.api.IThirdAuth.a {
    public b a;
    public IWXAPI b;
    private List<aou> c;

    /* renamed from: aoj$a */
    /* compiled from: AmapWxApi */
    static class a {
        static aoj a = new aoj(0);
    }

    /* synthetic */ aoj(byte b2) {
        this();
    }

    private aoj() {
        this.c = new ArrayList();
        this.b = WXAPIFactory.createWXAPI(AMapAppGlobal.getApplication(), serverkey.getWXCustomKey());
        this.b.registerApp(serverkey.getWXCustomKey());
    }

    public static aoj d() {
        return a.a;
    }

    public final boolean b() {
        return this.b.isWXAppInstalled();
    }

    public final boolean c() {
        return this.b.getWXAppSupportAPI() >= R.drawable.bio_custom_dialog_close;
    }

    public final boolean a(Intent intent, IWXAPIEventHandler iWXAPIEventHandler) {
        return this.b.handleIntent(intent, iWXAPIEventHandler);
    }

    public final void e() {
        if (this.c != null) {
            this.c.clear();
        }
    }

    public final void a(b bVar) {
        this.a = bVar;
    }

    public final boolean a(BaseReq baseReq) {
        return this.b.sendReq(baseReq);
    }

    public final boolean a() {
        return this.b.openWXApp();
    }

    public final synchronized void a(Resp resp) {
        if (this.c != null) {
            for (aou next : this.c) {
                if (next != null) {
                    next.a(resp);
                }
            }
            this.c.clear();
        }
    }

    public final synchronized void a(aou aou) {
        if (aou != null) {
            if (this.c != null) {
                this.c.add(aou);
            }
        }
    }
}
