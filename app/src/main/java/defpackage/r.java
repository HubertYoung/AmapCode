package defpackage;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.LruCache;
import anet.channel.NoAvailStrategyException;
import anet.channel.entity.ConnType;
import anet.channel.entity.ConnType.TypeLevel;
import anet.channel.entity.ENV;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.status.NetworkStatusHelper.NetworkStatus;
import anet.channel.strategy.ConnProtocol;
import com.alipay.mobile.h5container.api.H5Param;
import com.amap.location.common.model.AmapLoc;
import com.autonavi.minimap.ajx3.util.RomUtil;
import com.autonavi.widget.ui.BalloonLayout;
import java.net.ConnectException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;

/* renamed from: r reason: default package */
/* compiled from: SessionCenter */
public final class r {
    static Map<k, r> a = new HashMap();
    /* access modifiers changed from: private */
    public static boolean j = false;
    Context b = m.a();
    String c;
    k d;
    final u e = new u();
    final LruCache<String, v> f = new LruCache<>(32);
    public final q g = new q();
    public final i h;
    final a i = new a(this, 0);

    /* renamed from: r$a */
    /* compiled from: SessionCenter */
    class a implements anet.channel.status.NetworkStatusHelper.a, br, defpackage.cm.a {
        boolean a;

        private a() {
            this.a = false;
        }

        /* synthetic */ a(r rVar, byte b2) {
            this();
        }

        public final void a(NetworkStatus networkStatus) {
            cl.d("awcn.SessionCenter", "onNetworkStatusChanged.", r.this.c, "networkStatus", networkStatus);
            List<v> a2 = r.this.e.a();
            if (!a2.isEmpty()) {
                for (v a3 : a2) {
                    cl.a("awcn.SessionCenter", "network change, try recreate session", r.this.c, new Object[0]);
                    a3.a();
                }
            }
            r.this.h.a();
        }

        public final void a(d dVar) {
            r.a(r.this, dVar);
            r.this.h.a();
        }

        public final void a() {
            cl.b("awcn.SessionCenter", "[forground]", r.this.c, new Object[0]);
            if (r.this.b != null && !this.a) {
                this.a = true;
                if (!r.j) {
                    cl.d("awcn.SessionCenter", "forground not inited!", r.this.c, new Object[0]);
                    return;
                }
                try {
                    if (cm.a == 0 || System.currentTimeMillis() - cm.a <= 60000) {
                        r.this.h.a();
                    } else {
                        r.this.h.a(true);
                    }
                    this.a = false;
                } catch (Exception unused) {
                    this.a = false;
                } catch (Exception unused2) {
                } catch (Throwable th) {
                    this.a = false;
                    throw th;
                }
            }
        }

        public final void b() {
            cl.b("awcn.SessionCenter", "[background]", r.this.c, new Object[0]);
            if (!r.j) {
                cl.d("awcn.SessionCenter", "background not inited!", r.this.c, new Object[0]);
                return;
            }
            try {
                bu.a().b();
                if (RomUtil.ROM_OPPO.equalsIgnoreCase(Build.BRAND)) {
                    cl.b("awcn.SessionCenter", "close session for OPPO", r.this.c, new Object[0]);
                    r.this.h.a(false);
                }
            } catch (Exception unused) {
            }
        }
    }

    public static synchronized void a(Context context) {
        synchronized (r.class) {
            if (context == null) {
                try {
                    cl.d("awcn.SessionCenter", "context is null!", null, new Object[0]);
                    throw new NullPointerException("init failed. context is null");
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                m.a(context.getApplicationContext());
                if (!j) {
                    a.put(k.a, new r(k.a));
                    cm.a();
                    NetworkStatusHelper.a(context);
                    bu.a().a(m.a());
                    if (m.b()) {
                        ae.a();
                        av.a();
                    }
                    j = true;
                }
            }
        }
    }

    public static synchronized void a(Context context, String str, ENV env) {
        synchronized (r.class) {
            if (context == null) {
                try {
                    cl.d("awcn.SessionCenter", "context is null!", null, new Object[0]);
                    throw new NullPointerException("init failed. context is null");
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                k a2 = k.a(str, env);
                if (a2 == null) {
                    defpackage.k.a aVar = new defpackage.k.a();
                    aVar.b = str;
                    aVar.c = env;
                    a2 = aVar.a();
                }
                a(context, a2);
            }
        }
    }

    public static synchronized void a(Context context, k kVar) {
        synchronized (r.class) {
            if (context == null) {
                try {
                    cl.d("awcn.SessionCenter", "context is null!", null, new Object[0]);
                    throw new NullPointerException("init failed. context is null");
                } catch (Throwable th) {
                    throw th;
                }
            } else if (kVar == null) {
                cl.d("awcn.SessionCenter", "paramter config is null!", null, new Object[0]);
                throw new NullPointerException("init failed. config is null");
            } else {
                a(context);
                if (!a.containsKey(kVar)) {
                    a.put(kVar, new r(kVar));
                }
            }
        }
    }

    private r(k kVar) {
        this.d = kVar;
        this.c = kVar.b;
        a aVar = this.i;
        cm.a((defpackage.cm.a) aVar);
        NetworkStatusHelper.a((anet.channel.status.NetworkStatusHelper.a) aVar);
        bu.a().a((br) aVar);
        this.h = new i(this);
        if (!kVar.b.equals("[default]")) {
            final ba baVar = kVar.d;
            final String str = kVar.b;
            bz.a((cg) new cg() {
                public final String a() {
                    return str;
                }

                public final String a(String str) {
                    return baVar.a(r.this.b, (String) "HMAC_SHA1", str, str);
                }

                public final boolean b() {
                    return !baVar.a();
                }
            });
        }
    }

    public static synchronized void a(ENV env) {
        synchronized (r.class) {
            try {
                if (m.d() != env) {
                    cl.b("awcn.SessionCenter", "switch env", null, "old", m.d(), AmapLoc.TYPE_NEW, env);
                    m.a(env);
                    bu.a().a();
                    SpdyAgent.getInstance(m.a(), SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION).switchAccsServer(env == ENV.TEST ? 0 : 1);
                }
                Iterator<Entry<k, r>> it = a.entrySet().iterator();
                while (it.hasNext()) {
                    r rVar = (r) it.next().getValue();
                    if (rVar.d.c != env) {
                        cl.b("awcn.SessionCenter", "remove instance", rVar.c, "ENVIRONMENT", rVar.d.c);
                        rVar.h.a(false);
                        a aVar = rVar.i;
                        bu.a().b((br) aVar);
                        cm.b(aVar);
                        NetworkStatusHelper.b(aVar);
                        it.remove();
                    }
                }
            } catch (Throwable unused) {
                cl.e("awcn.SessionCenter", "switch env error.", null, new Object[0]);
            }
        }
    }

    public static synchronized r a(String str) {
        r a2;
        synchronized (r.class) {
            k a3 = k.a(str);
            if (a3 == null) {
                throw new RuntimeException("tag not exist!");
            }
            a2 = a(a3);
        }
        return a2;
    }

    public static synchronized r a(k kVar) {
        r rVar;
        synchronized (r.class) {
            if (kVar == null) {
                try {
                    throw new NullPointerException("config is null!");
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                if (!j) {
                    Context a2 = db.a();
                    if (a2 != null) {
                        a(a2);
                    }
                }
                rVar = a.get(kVar);
                if (rVar == null) {
                    rVar = new r(kVar);
                    a.put(kVar, rVar);
                }
            }
        }
        return rVar;
    }

    @Deprecated
    public static synchronized r a() {
        synchronized (r.class) {
            if (!j) {
                Context a2 = db.a();
                if (a2 != null) {
                    a(a2);
                }
            }
            r rVar = null;
            for (Entry next : a.entrySet()) {
                r rVar2 = (r) next.getValue();
                if (next.getKey() != k.a) {
                    return rVar2;
                }
                rVar = rVar2;
            }
            return rVar;
        }
    }

    public final p b(String str) throws Exception {
        return a(cs.a(str), ai.c, 60000, null);
    }

    public final p a(cs csVar, int i2) throws Exception {
        return a(csVar, i2, 0, null);
    }

    @Deprecated
    public final p a(String str, TypeLevel typeLevel, long j2) {
        return a(cs.a(str), typeLevel == TypeLevel.SPDY ? ai.a : ai.b, j2);
    }

    public final p a(cs csVar, int i2, long j2) {
        try {
            return a(csVar, i2, j2, null);
        } catch (InvalidParameterException unused) {
            cl.e("awcn.SessionCenter", "[Get]param url is invalid", this.c, "url", csVar);
            return null;
        } catch (TimeoutException unused2) {
            cl.e("awcn.SessionCenter", "[Get]timeout exception", this.c, "url", csVar.e);
            return null;
        } catch (ConnectException e2) {
            cl.d("awcn.SessionCenter", "[Get]connect exception", this.c, "errMsg", e2.getMessage(), "url", csVar.e);
            return null;
        } catch (NoAvailStrategyException e3) {
            StringBuilder sb = new StringBuilder("[Get]");
            sb.append(e3.getMessage());
            cl.b("awcn.SessionCenter", sb.toString(), this.c, null, "url", csVar.e);
            return null;
        } catch (Exception e4) {
            StringBuilder sb2 = new StringBuilder("[Get]");
            sb2.append(e4.getMessage());
            cl.e("awcn.SessionCenter", sb2.toString(), this.c, "url", csVar.e);
            return null;
        }
    }

    public final void a(cs csVar, int i2, s sVar) {
        try {
            p a2 = a(csVar, i2, BalloonLayout.DEFAULT_DISPLAY_DURATION, sVar);
            if (a2 != null) {
                sVar.a(a2);
            }
        } catch (Exception unused) {
            sVar.a();
        }
    }

    public final void a(t tVar) {
        q qVar = this.g;
        if (tVar == null) {
            throw new NullPointerException("info is null");
        } else if (TextUtils.isEmpty(tVar.a)) {
            throw new IllegalArgumentException("host cannot be null or empty");
        } else {
            qVar.b.put(tVar.a, tVar);
            if (tVar.b) {
                this.h.a();
            }
        }
    }

    private v a(cs csVar) {
        String b2 = bu.a().b(csVar.b);
        if (b2 == null) {
            b2 = csVar.b;
        }
        String str = csVar.a;
        if (!csVar.g) {
            str = bu.a().a(b2, str);
        }
        return c(cz.a(str, "://", b2));
    }

    private p a(cs csVar, int i2, long j2, s sVar) throws Exception {
        cs csVar2 = csVar;
        int i3 = i2;
        long j3 = j2;
        if (!j) {
            cl.d("awcn.SessionCenter", "getInternal not inited!", this.c, new Object[0]);
            throw new IllegalStateException("getInternal not inited");
        } else if (csVar2 == null) {
            throw new InvalidParameterException("httpUrl is null");
        } else {
            String str = this.c;
            Object[] objArr = new Object[6];
            objArr[0] = H5Param.URL;
            objArr[1] = csVar2.e;
            objArr[2] = "sessionType";
            objArr[3] = i3 == ai.a ? "LongLink" : "ShortLink";
            objArr[4] = "timeout";
            objArr[5] = Long.valueOf(j2);
            cl.a("awcn.SessionCenter", "getInternal", str, objArr);
            v a2 = a(csVar);
            p a3 = this.e.a(a2, i3);
            if (a3 != null) {
                cl.a("awcn.SessionCenter", "get internal hit cache session", this.c, "session", a3);
            } else if (this.d != k.a || i3 == ai.b) {
                if (m.h() && i3 == ai.a && j.a()) {
                    t a4 = this.g.a(csVar2.b);
                    if (a4 != null && a4.c) {
                        cl.c("awcn.SessionCenter", "app background, forbid to create accs session", this.c, new Object[0]);
                        throw new ConnectException("accs session connecting forbidden in background");
                    }
                }
                a2.a(this.b, i3, cy.a(this.c), sVar, j3);
                if (sVar == null && j3 > 0 && (i3 == ai.c || a2.b() == i3)) {
                    a2.a(j3);
                    a3 = this.e.a(a2, i3);
                    if (a3 == null) {
                        throw new ConnectException("session connecting failed or timeout");
                    }
                }
            } else {
                if (sVar != null) {
                    sVar.a();
                }
                return null;
            }
            return a3;
        }
    }

    /* access modifiers changed from: protected */
    public final v c(String str) {
        v vVar;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        synchronized (this.f) {
            vVar = this.f.get(str);
            if (vVar == null) {
                vVar = new v(str, this);
                this.f.put(str, vVar);
            }
        }
        return vVar;
    }

    static /* synthetic */ void a(r rVar, d dVar) {
        boolean z;
        boolean z2;
        r rVar2 = rVar;
        char c2 = 0;
        try {
            b[] bVarArr = dVar.b;
            int i2 = 0;
            while (i2 < bVarArr.length) {
                b bVar = bVarArr[i2];
                if (bVar.k) {
                    String str = rVar2.c;
                    Object[] objArr = new Object[2];
                    objArr[c2] = "host";
                    objArr[1] = bVar.a;
                    cl.b("awcn.SessionCenter", "find effectNow", str, objArr);
                    defpackage.bw.a[] aVarArr = bVar.h;
                    String[] strArr = bVar.f;
                    for (p next : rVar2.e.a(rVar2.c(cz.b(bVar.c, bVar.a)))) {
                        if (!next.h().b()) {
                            int i3 = 0;
                            while (true) {
                                if (i3 >= strArr.length) {
                                    z = false;
                                    break;
                                } else if (next.f().equals(strArr[i3])) {
                                    z = true;
                                    break;
                                } else {
                                    i3++;
                                }
                            }
                            if (!z) {
                                if (cl.a(2)) {
                                    String str2 = next.p;
                                    Object[] objArr2 = new Object[4];
                                    objArr2[c2] = "session ip";
                                    objArr2[1] = next.f();
                                    objArr2[2] = "ips";
                                    objArr2[3] = Arrays.toString(strArr);
                                    cl.b("awcn.SessionCenter", "ip not match", str2, objArr2);
                                }
                                next.a(true);
                            } else {
                                int i4 = 0;
                                while (true) {
                                    if (i4 < aVarArr.length) {
                                        if (next.g() == aVarArr[i4].a && next.h().equals(ConnType.a(ConnProtocol.valueOf(aVarArr[i4])))) {
                                            z2 = true;
                                            break;
                                        }
                                        i4++;
                                    } else {
                                        z2 = false;
                                        break;
                                    }
                                }
                                if (!z2) {
                                    if (cl.a(2)) {
                                        String str3 = next.p;
                                        Object[] objArr3 = new Object[6];
                                        objArr3[c2] = "port";
                                        objArr3[1] = Integer.valueOf(next.g());
                                        objArr3[2] = "connType";
                                        objArr3[3] = next.h();
                                        objArr3[4] = "aisle";
                                        objArr3[5] = Arrays.toString(aVarArr);
                                        cl.b("awcn.SessionCenter", "aisle not match", str3, objArr3);
                                    }
                                    next.a(true);
                                }
                                c2 = 0;
                            }
                        }
                    }
                }
                if (bVar.e != null) {
                    for (p next2 : rVar2.e.a(rVar2.c(cz.b(bVar.c, bVar.a)))) {
                        if (!cz.c(next2.l, bVar.e)) {
                            cl.b("awcn.SessionCenter", "unit change", next2.p, "session unit", next2.l, "unit", bVar.e);
                            next2.a(true);
                        }
                    }
                }
                i2++;
                c2 = 0;
            }
        } catch (Exception unused) {
            cl.e("awcn.SessionCenter", "checkStrategy failed", rVar2.c, new Object[0]);
        }
    }
}
