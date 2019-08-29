package defpackage;

import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.RequestStatistic;
import anet.channel.statist.StatObject;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.cache.Cache;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.MonitorItemConstants;
import com.ta.audid.store.UtdidContentBuilder;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* renamed from: en reason: default package */
/* compiled from: UnifiedRequestTask */
public final class en {
    protected em a;

    /* renamed from: en$a */
    /* compiled from: UnifiedRequestTask */
    class a {
        private int b = 0;
        private ay c = null;
        private eb d = null;

        a(int i, ay ayVar, eb ebVar) {
            this.b = i;
            this.c = ayVar;
            this.d = ebVar;
        }

        public final Future a(ay ayVar, eb ebVar) {
            if (en.this.a.d.get()) {
                cl.b("anet.UnifiedRequestTask", "request canneled or timeout in processing interceptor", ayVar.e, new Object[0]);
                return null;
            } else if (this.b < ed.a()) {
                new a(this.b + 1, ayVar, ebVar);
                return ed.a(this.b).a();
            } else {
                en.this.a.a.b = ayVar;
                en.this.a.b = ebVar;
                Cache a2 = ds.i() ? dp.a(en.this.a.a.c()) : null;
                en.this.a.e = a2 != null ? new eh(en.this.a, a2) : new el(en.this.a, null, null);
                en.this.a.e.run();
                en.this.c();
                return null;
            }
        }
    }

    public en(dy dyVar, dx dxVar) {
        dxVar.a = dyVar.i;
        this.a = new em(dyVar, dxVar);
    }

    public final Future a() {
        this.a.a.f.start = System.currentTimeMillis();
        if (cl.a(2)) {
            cl.b("anet.UnifiedRequestTask", "request", this.a.c, "Url", this.a.a.b.a.e);
        }
        if (ds.b(this.a.a.b.a)) {
            ei eiVar = new ei(this.a);
            this.a.e = eiVar;
            eiVar.a = new ax(ck.c(new Runnable() {
                public final void run() {
                    en.this.a.e.run();
                }
            }), this.a.a.b.e);
            c();
            return new ej(this);
        }
        ck.a(new Runnable() {
            public final void run() {
                new a(0, en.this.a.a.b, en.this.a.b).a(en.this.a.a.b, en.this.a.b);
            }
        }, c.a);
        return new ej(this);
    }

    /* access modifiers changed from: private */
    public void c() {
        this.a.f = ck.a(new Runnable() {
            public final void run() {
                if (en.this.a.d.compareAndSet(false, true)) {
                    RequestStatistic requestStatistic = en.this.a.a.f;
                    if (requestStatistic.isDone.compareAndSet(false, true)) {
                        requestStatistic.statusCode = -202;
                        requestStatistic.msg = co.a(-202);
                        requestStatistic.rspEnd = System.currentTimeMillis();
                        cl.d("anet.UnifiedRequestTask", "task time out", en.this.a.c, UtdidContentBuilder.TYPE_RS, requestStatistic);
                        x.a().a((StatObject) new ExceptionStatistic(-202, null, requestStatistic, null));
                    }
                    en.this.a.b();
                    en.this.a.b.a(new DefaultFinishEvent(-202, null, requestStatistic));
                }
            }
        }, (long) this.a.a.a(), TimeUnit.MILLISECONDS);
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        if (this.a.d.compareAndSet(false, true)) {
            cs csVar = this.a.a.b.a;
            cl.d("anet.UnifiedRequestTask", "task cancelled", this.a.c, MonitorItemConstants.KEY_URL, csVar.f);
            RequestStatistic requestStatistic = this.a.a.f;
            if (requestStatistic.isDone.compareAndSet(false, true)) {
                requestStatistic.ret = 2;
                requestStatistic.statusCode = -204;
                requestStatistic.msg = co.a(-204);
                requestStatistic.rspEnd = System.currentTimeMillis();
                x.a().a((StatObject) new ExceptionStatistic(-204, null, requestStatistic, null));
                if (requestStatistic.recDataSize > 102400) {
                    ar.a().a(requestStatistic.sendStart, requestStatistic.rspEnd, requestStatistic.recDataSize);
                }
            }
            this.a.b();
            this.a.a();
            this.a.b.a(new DefaultFinishEvent(-204, null, requestStatistic));
        }
    }
}
