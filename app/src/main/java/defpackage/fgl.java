package defpackage;

import android.content.Context;
import anet.channel.request.BodyEntry;
import com.alipay.mobile.security.bio.workspace.Env;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.network.domain.ParcelableRequestBodyImpl;
import mtopsdk.network.domain.Request;
import mtopsdk.network.impl.ParcelableRequestBodyEntry;

/* renamed from: fgl reason: default package */
/* compiled from: ANetworkCallImpl */
public final class fgl extends fgd {
    static volatile de i;
    static volatile de j;
    de k;

    public fgl(Request request, Context context) {
        super(request, context);
        fff.a();
        if (!fff.d()) {
            if (j == null) {
                j = new ea(this.b);
            }
            this.k = j;
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                TBSdkLog.b((String) "mtopsdk.ANetworkCallImpl", this.h, (String) "mNetwork=HttpNetwork in ANetworkCallImpl");
            }
            return;
        }
        if (i == null) {
            i = new du(this.b);
        }
        this.k = i;
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            TBSdkLog.b((String) "mtopsdk.ANetworkCallImpl", this.h, (String) "mNetwork=DegradableNetwork in ANetworkCallImpl");
        }
    }

    public final void a(final fgf fgf) {
        ffc ffc;
        Request a = a();
        ArrayList arrayList = null;
        if (!f || !e) {
            ffc = null;
        } else {
            ffc = a(a.n);
            if (ffc != null) {
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    TBSdkLog.b((String) "mtopsdk.ANetworkCallImpl", this.h, "[enqueue]get MockResponse succeed.mockResponse=".concat(String.valueOf(ffc)));
                }
                final fgi a2 = a(a, ffc.b, null, ffc.c, ffc.d);
                ffy.a(this.h != null ? this.h.hashCode() : hashCode(), new Runnable() {
                    public final void run() {
                        try {
                            fgf.a(a2);
                        } catch (Exception e) {
                            TBSdkLog.b("mtopsdk.ANetworkCallImpl", fgl.this.h, "[enqueue]call NetworkCallback.onResponse error.", e);
                        }
                    }
                });
                return;
            }
        }
        if (ffc == null) {
            de deVar = this.k;
            dz dzVar = new dz(a.a);
            dzVar.c(a.e);
            dzVar.a(a.h);
            dzVar.b(a.f);
            dzVar.c(a.g);
            dzVar.d(a.i);
            dzVar.a(a.b);
            Map<String, String> map = a.c;
            if (map != null && map.size() > 0) {
                arrayList = new ArrayList();
                for (Entry next : map.entrySet()) {
                    if (next != null && fdd.a((String) next.getKey())) {
                        arrayList.add(new dv((String) next.getKey(), (String) next.getValue()));
                    }
                }
            }
            dzVar.a((List<dc>) arrayList);
            dzVar.b("APPKEY", a.j);
            dzVar.b("AuthCode", a.k);
            switch (a.l) {
                case 0:
                    dzVar.b("ENVIRONMENT", "online");
                    break;
                case 1:
                    dzVar.b("ENVIRONMENT", Env.NAME_PRE);
                    break;
                case 2:
                    dzVar.b("ENVIRONMENT", "test");
                    break;
            }
            if ("POST".equalsIgnoreCase(a.b)) {
                ParcelableRequestBodyImpl parcelableRequestBodyImpl = (ParcelableRequestBodyImpl) a.d;
                dzVar.a((BodyEntry) new ParcelableRequestBodyEntry(parcelableRequestBodyImpl));
                dzVar.a("Content-Type", parcelableRequestBodyImpl.a);
                long b = parcelableRequestBodyImpl.b();
                if (b > 0) {
                    dzVar.a("Content-Length", String.valueOf(b));
                }
            }
            this.d = deVar.a(dzVar, a.m, new fgo(this, fgf, a.e));
        }
    }
}
