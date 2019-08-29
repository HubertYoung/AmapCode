package defpackage;

import android.text.TextUtils;
import anet.channel.statist.RequestStatistic;
import anetwork.channel.aidl.DefaultFinishEvent;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* renamed from: ei reason: default package */
/* compiled from: DegradeTask */
public final class ei implements ek {
    volatile aw a = null;
    private volatile boolean b = false;
    /* access modifiers changed from: private */
    public em c;
    /* access modifiers changed from: private */
    public int d = 0;
    /* access modifiers changed from: private */
    public int e = 0;
    private ay f;

    public ei(em emVar) {
        this.c = emVar;
        this.f = emVar.a.b;
    }

    public final void a() {
        this.b = true;
        if (this.a != null) {
            this.a.a();
        }
    }

    public final void run() {
        if (!this.b) {
            if (this.c.a.d()) {
                String a2 = dt.a(this.c.a.b.a.e);
                if (!TextUtils.isEmpty(a2)) {
                    a a3 = this.f.a();
                    String str = Collections.unmodifiableMap(this.f.c).get("Cookie");
                    if (!TextUtils.isEmpty(str)) {
                        a2 = cz.a(str, "; ", a2);
                    }
                    a3.a("Cookie", a2);
                    this.f = a3.a();
                }
            }
            this.f.k.degraded = 2;
            this.f.k.sendBeforeTime = System.currentTimeMillis() - this.f.k.reqStart;
            bg.a(this.f, (o) new o() {
                public final void onResponseCode(int i, Map<String, List<String>> map) {
                    if (!ei.this.c.d.get()) {
                        ei.this.c.a();
                        dt.a(ei.this.c.a.b.a.e, map);
                        ei.this.d = cq.b(map);
                        if (ei.this.c.b != null) {
                            ei.this.c.b.a(i, map);
                        }
                    }
                }

                public final void onDataReceive(aa aaVar, boolean z) {
                    if (!ei.this.c.d.get()) {
                        ei.this.e = ei.this.e + 1;
                        if (ei.this.c.b != null) {
                            ei.this.c.b.a(ei.this.e, ei.this.d, aaVar);
                        }
                    }
                }

                public final void onFinish(int i, String str, RequestStatistic requestStatistic) {
                    if (!ei.this.c.d.getAndSet(true)) {
                        if (cl.a(2)) {
                            cl.b("anet.DegradeTask", "[onFinish]", ei.this.c.c, "code", Integer.valueOf(i), "msg", str);
                        }
                        ei.this.c.a();
                        requestStatistic.isDone.set(true);
                        if (ei.this.c.b != null) {
                            ei.this.c.b.a(new DefaultFinishEvent(i, str, requestStatistic));
                        }
                    }
                }
            });
        }
    }
}
