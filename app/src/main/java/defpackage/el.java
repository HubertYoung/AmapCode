package defpackage;

import android.text.TextUtils;
import anet.channel.NoAvailStrategyException;
import anet.channel.entity.ENV;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.RequestStatistic;
import anet.channel.statist.StatObject;
import anet.channel.status.NetworkStatusHelper;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.cache.Cache;
import anetwork.channel.cache.Cache.Entry;
import anetwork.channel.http.NetworkSdkSetting;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.alipay.mobile.security.bio.workspace.Env;
import com.autonavi.widget.ui.BalloonLayout;
import com.tencent.open.utils.HttpUtils.NetworkUnavailableException;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* renamed from: el reason: default package */
/* compiled from: NetworkTask */
final class el implements ek {
    em a;
    Cache b = null;
    Entry c = null;
    ByteArrayOutputStream d = null;
    String e = H5ResourceHandlerUtil.OTHER;
    volatile aw f = null;
    volatile boolean g = false;
    volatile AtomicBoolean h = null;
    int i = 0;
    int j = 0;
    boolean k = false;
    boolean l = false;
    a m = null;

    /* renamed from: el$a */
    /* compiled from: NetworkTask */
    static class a {
        int a;
        Map<String, List<String>> b;
        List<aa> c = new ArrayList();

        a(int i, Map<String, List<String>> map) {
            this.a = i;
            this.b = map;
        }

        /* access modifiers changed from: 0000 */
        public final int a(eb ebVar, int i) {
            ebVar.a(this.a, this.b);
            int i2 = 1;
            for (aa a2 : this.c) {
                ebVar.a(i2, i, a2);
                i2++;
            }
            return i2;
        }
    }

    el(em emVar, Cache cache, Entry entry) {
        this.a = emVar;
        this.h = emVar.d;
        this.b = cache;
        this.c = entry;
        this.e = emVar.a.c().get("f-refer");
    }

    public final void a() {
        this.g = true;
        if (this.f != null) {
            this.f.a();
        }
    }

    public final void run() {
        if (!this.g) {
            RequestStatistic requestStatistic = this.a.a.f;
            requestStatistic.f_refer = this.e;
            if (!NetworkStatusHelper.h()) {
                if (!ds.o() || requestStatistic.statusCode == -200) {
                    if (cl.a(2)) {
                        cl.b("anet.NetworkTask", NetworkUnavailableException.ERROR_INFO, this.a.c, "NetworkStatus", NetworkStatusHelper.a());
                    }
                    this.h.set(true);
                    this.a.a();
                    requestStatistic.isDone.set(true);
                    requestStatistic.statusCode = -200;
                    requestStatistic.msg = co.a(-200);
                    requestStatistic.rspEnd = System.currentTimeMillis();
                    this.a.b.a(new DefaultFinishEvent(-200, null, requestStatistic));
                    return;
                }
                requestStatistic.statusCode = -200;
                ck.a(new Runnable() {
                    public final void run() {
                        ck.a(el.this, c.a);
                    }
                }, 1000, TimeUnit.MILLISECONDS);
            } else if (!ds.k() || !m.h() || cm.a <= 0 || System.currentTimeMillis() - cm.a <= ((long) ds.n()) || ds.a(this.a.a.b.a)) {
                if (cl.a(2)) {
                    cl.b("anet.NetworkTask", "exec request", this.a.c, "retryTimes", Integer.valueOf(this.a.a.e));
                }
                if (ds.m()) {
                    r b2 = b();
                    final cs csVar = this.a.a.b.a;
                    final boolean b3 = csVar.b();
                    final RequestStatistic requestStatistic2 = this.a.a.f;
                    final ay ayVar = this.a.a.b;
                    if (this.a.a.j != 1 || !ds.d() || this.a.a.e != 0 || b3) {
                        a(a(null, b2, csVar, b3), ayVar);
                        return;
                    }
                    cs a2 = a(csVar);
                    final long currentTimeMillis = System.currentTimeMillis();
                    int i2 = ai.a;
                    final r rVar = b2;
                    AnonymousClass3 r6 = new s() {
                        public final void a(p pVar) {
                            cl.b("anet.NetworkTask", "onSessionGetSuccess", el.this.a.c, "Session", pVar);
                            requestStatistic2.connWaitTime = System.currentTimeMillis() - currentTimeMillis;
                            requestStatistic2.spdyRequestSend = true;
                            el.this.a(pVar, ayVar);
                        }

                        public final void a() {
                            cl.d("anet.NetworkTask", "onSessionGetFail", el.this.a.c, "url", requestStatistic2.url);
                            requestStatistic2.connWaitTime = System.currentTimeMillis() - currentTimeMillis;
                            el.this.a(el.this.a(null, rVar, csVar, b3), ayVar);
                        }
                    };
                    b2.a(a2, i2, (s) r6);
                    return;
                }
                try {
                    p c2 = c();
                    if (c2 != null) {
                        a(c2, this.a.a.b);
                    }
                } catch (Exception unused) {
                    cl.e("anet.NetworkTask", "send request failed.", this.a.c, new Object[0]);
                }
            } else {
                this.h.set(true);
                this.a.a();
                if (cl.a(2)) {
                    cl.b("anet.NetworkTask", "request forbidden in background", this.a.c, "url", this.a.a.b.a);
                }
                requestStatistic.isDone.set(true);
                requestStatistic.statusCode = -205;
                requestStatistic.msg = co.a(-205);
                requestStatistic.rspEnd = System.currentTimeMillis();
                this.a.b.a(new DefaultFinishEvent(-205, null, requestStatistic));
                ExceptionStatistic exceptionStatistic = new ExceptionStatistic(-205, null, "rt");
                exceptionStatistic.host = this.a.a.b.a.b;
                exceptionStatistic.url = this.a.a.b.a.e;
                x.a().a((StatObject) exceptionStatistic);
            }
        }
    }

    private cs a(cs csVar) {
        String str = this.a.a.c().get("x-host-cname");
        if (TextUtils.isEmpty(str)) {
            return csVar;
        }
        cs a2 = cs.a(csVar.e.replaceFirst(csVar.b, str));
        return a2 != null ? a2 : csVar;
    }

    private r b() {
        String a2 = this.a.a.a((String) "APPKEY");
        if (TextUtils.isEmpty(a2)) {
            return r.a();
        }
        ENV env = ENV.ONLINE;
        String a3 = this.a.a.a((String) "ENVIRONMENT");
        if (Env.NAME_PRE.equalsIgnoreCase(a3)) {
            env = ENV.PREPARE;
        } else if ("test".equalsIgnoreCase(a3)) {
            env = ENV.TEST;
        }
        if (env != NetworkSdkSetting.CURRENT_ENV) {
            NetworkSdkSetting.CURRENT_ENV = env;
            r.a(env);
        }
        k a4 = k.a(a2, env);
        if (a4 == null) {
            defpackage.k.a aVar = new defpackage.k.a();
            aVar.b = a2;
            aVar.c = env;
            aVar.d = this.a.a.a((String) "AuthCode");
            a4 = aVar.a();
        }
        return r.a(a4);
    }

    private p c() {
        p pVar;
        final r b2 = b();
        final cs csVar = this.a.a.b.a;
        final boolean b3 = csVar.b();
        final RequestStatistic requestStatistic = this.a.a.f;
        if (this.a.a.j != 1 || !ds.d() || this.a.a.e != 0 || b3) {
            return a(null, b2, csVar, b3);
        }
        final cs a2 = a(csVar);
        try {
            pVar = b2.a(a2, ai.a);
        } catch (NoAvailStrategyException unused) {
            return a(null, b2, csVar, b3);
        } catch (Exception unused2) {
            pVar = null;
        }
        if (pVar == null) {
            AnonymousClass2 r0 = new Runnable() {
                public final void run() {
                    long currentTimeMillis = System.currentTimeMillis();
                    p a2 = b2.a(a2, ai.a, (long) BalloonLayout.DEFAULT_DISPLAY_DURATION);
                    requestStatistic.connWaitTime = System.currentTimeMillis() - currentTimeMillis;
                    requestStatistic.spdyRequestSend = a2 != null;
                    el.this.a(el.this.a(a2, b2, csVar, b3), el.this.a.a.b);
                }
            };
            ck.a(r0, c.b);
            return null;
        }
        cl.b("anet.NetworkTask", "tryGetSession", this.a.c, "Session", pVar);
        requestStatistic.spdyRequestSend = true;
        return pVar;
    }

    /* access modifiers changed from: private */
    public p a(p pVar, r rVar, cs csVar, boolean z) {
        RequestStatistic requestStatistic = this.a.a.f;
        if (pVar == null && this.a.a.b() && !z && !NetworkStatusHelper.i()) {
            pVar = rVar.a(csVar, ai.b, 0);
        }
        if (pVar == null) {
            cl.b("anet.NetworkTask", "create HttpSession with local DNS", this.a.c, new Object[0]);
            pVar = new bh(m.a(), new af(cz.a(csVar.a, "://", csVar.b), this.a.c, null));
        }
        if (requestStatistic.spdyRequestSend) {
            requestStatistic.degraded = 1;
        }
        cl.b("anet.NetworkTask", "tryGetHttpSession", this.a.c, "Session", pVar);
        return pVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0093 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0094  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private defpackage.ay a(defpackage.ay r7) {
        /*
            r6 = this;
            em r0 = r6.a
            dy r0 = r0.a
            boolean r0 = r0.d()
            if (r0 == 0) goto L_0x0042
            em r0 = r6.a
            dy r0 = r0.a
            ay r0 = r0.b
            cs r0 = r0.a
            java.lang.String r0 = r0.e
            java.lang.String r0 = defpackage.dt.a(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x0042
            ay$a r1 = r7.a()
            java.util.Map<java.lang.String, java.lang.String> r2 = r7.c
            java.util.Map r2 = java.util.Collections.unmodifiableMap(r2)
            java.lang.String r3 = "Cookie"
            java.lang.Object r2 = r2.get(r3)
            java.lang.String r2 = (java.lang.String) r2
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x003c
            java.lang.String r3 = "; "
            java.lang.String r0 = defpackage.cz.a(r2, r3, r0)
        L_0x003c:
            java.lang.String r2 = "Cookie"
            r1.a(r2, r0)
            goto L_0x0043
        L_0x0042:
            r1 = 0
        L_0x0043:
            anetwork.channel.cache.Cache$Entry r0 = r6.c
            if (r0 == 0) goto L_0x0073
            if (r1 != 0) goto L_0x004d
            ay$a r1 = r7.a()
        L_0x004d:
            anetwork.channel.cache.Cache$Entry r0 = r6.c
            java.lang.String r0 = r0.etag
            if (r0 == 0) goto L_0x005c
            java.lang.String r0 = "If-None-Match"
            anetwork.channel.cache.Cache$Entry r2 = r6.c
            java.lang.String r2 = r2.etag
            r1.a(r0, r2)
        L_0x005c:
            anetwork.channel.cache.Cache$Entry r0 = r6.c
            long r2 = r0.lastModified
            r4 = 0
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 <= 0) goto L_0x0073
            java.lang.String r0 = "If-Modified-Since"
            anetwork.channel.cache.Cache$Entry r2 = r6.c
            long r2 = r2.lastModified
            java.lang.String r2 = defpackage.CacheHelper.a(r2)
            r1.a(r0, r2)
        L_0x0073:
            em r0 = r6.a
            dy r0 = r0.a
            int r0 = r0.e
            if (r0 != 0) goto L_0x0091
            java.lang.String r0 = "weex"
            java.lang.String r2 = r6.e
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x0091
            if (r1 != 0) goto L_0x008c
            ay$a r1 = r7.a()
        L_0x008c:
            r0 = 3000(0xbb8, float:4.204E-42)
            r1.a(r0)
        L_0x0091:
            if (r1 != 0) goto L_0x0094
            return r7
        L_0x0094:
            ay r7 = r1.a()
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.el.a(ay):ay");
    }

    /* access modifiers changed from: 0000 */
    public final void a(p pVar, ay ayVar) {
        if (pVar != null && !this.g) {
            final ay a2 = a(ayVar);
            final RequestStatistic requestStatistic = this.a.a.f;
            requestStatistic.reqStart = System.currentTimeMillis();
            this.f = pVar.a(a2, (o) new o() {
                public final void onResponseCode(int i, Map<String, List<String>> map) {
                    if (!el.this.h.get()) {
                        if (cl.a(2)) {
                            cl.b("anet.NetworkTask", "onResponseCode", a2.e, "code", Integer.valueOf(i));
                            cl.b("anet.NetworkTask", "onResponseCode", a2.e, "headers", map);
                        }
                        if (cq.a(a2, i)) {
                            String a2 = cq.a(map, (String) "Location");
                            if (a2 != null) {
                                cs a3 = cs.a(a2);
                                if (a3 != null) {
                                    if (el.this.h.compareAndSet(false, true)) {
                                        a3.g = true;
                                        dy dyVar = el.this.a.a;
                                        cl.b("anet.RequestConfig", "redirect", dyVar.i, "to url", a3.toString());
                                        dyVar.c++;
                                        dyVar.f.url = a3.e;
                                        dyVar.b = dyVar.a(a3);
                                        el.this.a.d = new AtomicBoolean();
                                        el.this.a.e = new el(el.this.a, null, null);
                                        requestStatistic.recordRedirect(i, a3.f);
                                        ck.a(el.this.a.e, c.a);
                                    }
                                    return;
                                }
                                cl.d("anet.NetworkTask", "redirect url is invalid!", a2.e, "redirect url", a2);
                            }
                        }
                        try {
                            el.this.a.a();
                            dt.a(el.this.a.a.b.a.e, map);
                            el.this.i = cq.b(map);
                            String str = el.this.a.a.b.a.e;
                            if (el.this.c == null || i != 304) {
                                if (el.this.b != null) {
                                    if ("no-store".equals(cq.a(map, (String) "Cache-Control"))) {
                                        el.this.b.b(str);
                                    } else {
                                        el elVar = el.this;
                                        Entry a4 = CacheHelper.a(map);
                                        elVar.c = a4;
                                        if (a4 != null) {
                                            cq.b(map, "Cache-Control");
                                            map.put("Cache-Control", Arrays.asList(new String[]{"no-store"}));
                                            el.this.d = new ByteArrayOutputStream(el.this.i != 0 ? el.this.i : 5120);
                                        }
                                    }
                                }
                                map.put("x-protocol", Arrays.asList(new String[]{requestStatistic.protocolType}));
                                if (!ds.l() || el.this.i > 131072) {
                                    el.this.a.b.a(i, map);
                                    el.this.k = true;
                                    return;
                                }
                                el.this.m = new a(i, map);
                                return;
                            }
                            el.this.c.responseHeaders.putAll(map);
                            el.this.a.b.a(200, el.this.c.responseHeaders);
                            el.this.a.b.a(1, el.this.c.data.length, aa.a(el.this.c.data));
                            long currentTimeMillis = System.currentTimeMillis();
                            el.this.b.a(str, el.this.c);
                            cl.b("anet.NetworkTask", "update cache", el.this.a.c, "cost", Long.valueOf(System.currentTimeMillis() - currentTimeMillis), "key", str);
                        } catch (Exception e) {
                            cl.a("anet.NetworkTask", "[onResponseCode] error.", el.this.a.c, e, new Object[0]);
                        }
                    }
                }

                public final void onDataReceive(aa aaVar, boolean z) {
                    if (!el.this.h.get()) {
                        if (el.this.j == 0) {
                            cl.b("anet.NetworkTask", "[onDataReceive] receive first data chunk!", el.this.a.c, new Object[0]);
                        }
                        if (z) {
                            cl.b("anet.NetworkTask", "[onDataReceive] receive last data chunk!", el.this.a.c, new Object[0]);
                        }
                        el.this.j++;
                        try {
                            if (el.this.m != null) {
                                el.this.m.c.add(aaVar);
                                if (requestStatistic.recDataSize > IjkMediaMeta.AV_CH_TOP_BACK_RIGHT || z) {
                                    el.this.j = el.this.m.a(el.this.a.b, el.this.i);
                                    el.this.k = true;
                                    el.this.l = el.this.j > 1;
                                    el.this.m = null;
                                }
                            } else {
                                el.this.a.b.a(el.this.j, el.this.i, aaVar);
                                el.this.l = true;
                            }
                            if (el.this.d != null) {
                                el.this.d.write(aaVar.a, 0, aaVar.c);
                                if (z) {
                                    String str = el.this.a.a.b.a.e;
                                    el.this.c.data = el.this.d.toByteArray();
                                    long currentTimeMillis = System.currentTimeMillis();
                                    el.this.b.a(str, el.this.c);
                                    cl.b("anet.NetworkTask", "write cache", el.this.a.c, "cost", Long.valueOf(System.currentTimeMillis() - currentTimeMillis), "size", Integer.valueOf(el.this.c.data.length), "key", str);
                                }
                            }
                        } catch (Exception e) {
                            cl.a("anet.NetworkTask", "[onDataReceive] error.", el.this.a.c, e, new Object[0]);
                        }
                    }
                }

                public final void onFinish(int i, String str, RequestStatistic requestStatistic) {
                    DefaultFinishEvent defaultFinishEvent;
                    String str2;
                    if (!el.this.h.getAndSet(true)) {
                        int i2 = 3;
                        if (cl.a(2)) {
                            cl.b("anet.NetworkTask", "[onFinish]", el.this.a.c, "code", Integer.valueOf(i), "msg", str);
                        }
                        if (i < 0) {
                            try {
                                dy dyVar = el.this.a.a;
                                if (dyVar.e < dyVar.d) {
                                    if (el.this.k || el.this.l) {
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(requestStatistic.msg);
                                        sb.append(":回调后触发重试");
                                        requestStatistic.msg = sb.toString();
                                        if (el.this.l) {
                                            requestStatistic.roaming = 2;
                                        } else if (el.this.k) {
                                            requestStatistic.roaming = 1;
                                        }
                                        cl.d("anet.NetworkTask", "Cannot retry request after onHeader/onDataReceived callback!", el.this.a.c, new Object[0]);
                                    } else {
                                        cl.d("anet.NetworkTask", "clear response buffer and retry", el.this.a.c, new Object[0]);
                                        if (el.this.m != null) {
                                            if (!el.this.m.c.isEmpty()) {
                                                i2 = 4;
                                            }
                                            requestStatistic.roaming = i2;
                                            for (aa a2 : el.this.m.c) {
                                                a2.a();
                                            }
                                            el.this.m = null;
                                        }
                                        dy dyVar2 = el.this.a.a;
                                        dyVar2.e++;
                                        dyVar2.f.retryTimes = dyVar2.e;
                                        el.this.a.d = new AtomicBoolean();
                                        el.this.a.e = new el(el.this.a, el.this.b, el.this.c);
                                        if (requestStatistic.tnetErrorCode != 0) {
                                            StringBuilder sb2 = new StringBuilder();
                                            sb2.append(i);
                                            sb2.append(MergeUtil.SEPARATOR_KV);
                                            sb2.append(requestStatistic.tnetErrorCode);
                                            str2 = sb2.toString();
                                            requestStatistic.tnetErrorCode = 0;
                                        } else {
                                            str2 = String.valueOf(i);
                                        }
                                        requestStatistic.appendErrorTrace(str2);
                                        long currentTimeMillis = System.currentTimeMillis();
                                        requestStatistic.retryCostTime += currentTimeMillis - requestStatistic.start;
                                        requestStatistic.start = currentTimeMillis;
                                        ck.a(el.this.a.e, c.a);
                                        return;
                                    }
                                }
                            } catch (Exception unused) {
                                return;
                            }
                        }
                        if (el.this.m != null) {
                            el.this.m.a(el.this.a.b, el.this.i);
                        }
                        el.this.a.a();
                        requestStatistic.isDone.set(true);
                        if ("wv_h5".equals(el.this.e)) {
                            cl.d("anet.NetworkTask", null, el.this.a.c, "url", a2.a.f, "content-length", Integer.valueOf(el.this.i), "recDataLength", Long.valueOf(requestStatistic.rspBodyDeflateSize));
                        }
                        if (!(!"true".equals(el.this.a.a.a.a((String) "CheckContentLength")) || requestStatistic.contentLength == 0 || requestStatistic.contentLength == requestStatistic.rspBodyDeflateSize)) {
                            requestStatistic.ret = 0;
                            requestStatistic.statusCode = -206;
                            str = co.a(-206);
                            requestStatistic.msg = str;
                            cl.d("anet.NetworkTask", "received data length not match with content-length", el.this.a.c, "content-length", Integer.valueOf(el.this.i), "recDataLength", Long.valueOf(requestStatistic.rspBodyDeflateSize));
                            ExceptionStatistic exceptionStatistic = new ExceptionStatistic(-206, str, "rt");
                            exceptionStatistic.url = el.this.a.a.b.a.e;
                            x.a().a((StatObject) exceptionStatistic);
                            i = -206;
                        }
                        if (i != 304 || el.this.c == null) {
                            defaultFinishEvent = new DefaultFinishEvent(i, str, requestStatistic);
                        } else {
                            requestStatistic.protocolType = "cache";
                            defaultFinishEvent = new DefaultFinishEvent(200, str, requestStatistic);
                        }
                        el.this.a.b.a(defaultFinishEvent);
                        if (i >= 0) {
                            ar.a().a(requestStatistic.sendStart, requestStatistic.rspEnd, requestStatistic.rspHeadDeflateSize + requestStatistic.rspBodyDeflateSize);
                        } else {
                            requestStatistic.netType = NetworkStatusHelper.b();
                        }
                        al.a().a(new aj(el.this.e, requestStatistic));
                    }
                }
            });
        }
    }
}
