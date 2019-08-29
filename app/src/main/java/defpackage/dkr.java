package defpackage;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: dkr reason: default package */
/* compiled from: AbstractHTTPPollingConnection */
public abstract class dkr {
    public dku a;
    CopyOnWriteArrayList<String> b;
    boolean c = false;
    public boolean d = false;
    public a e = null;
    private dks f;
    private dkt g;

    /* renamed from: dkr$a */
    /* compiled from: AbstractHTTPPollingConnection */
    static class a implements dku {
        private WeakReference<dkr> a;

        public a(dkr dkr) {
            this.a = new WeakReference<>(dkr);
        }

        public final void a(String str, String str2) {
            dkr dkr = (dkr) this.a.get();
            if (dkr != null && dkr.a != null && dkr.c) {
                ((dkr) this.a.get()).a.a(str, str2);
            }
        }

        public final void b(String str) {
            dkr dkr = (dkr) this.a.get();
            if (dkr != null && dkr.a != null && dkr.c) {
                ((dkr) this.a.get()).a.b(str);
            }
        }

        public final void m() {
            dkr dkr = (dkr) this.a.get();
            if (dkr != null && dkr.a != null && dkr.c) {
                ((dkr) this.a.get()).a.m();
            }
        }

        public final void n() {
            dkr dkr = (dkr) this.a.get();
            if (dkr != null && dkr.a != null && dkr.c) {
                ((dkr) this.a.get()).a.n();
            }
        }
    }

    public abstract void a(String str, dku dku);

    public final void a(dkw dkw) {
        if (!this.c) {
            this.c = true;
            this.b = new CopyOnWriteArrayList<>();
            if (!bno.a || dkw != null) {
                if (dkw.e == 0) {
                    dkw.e = 10000;
                } else if (dkw.e <= 100) {
                    dkw.e *= 1000;
                }
                this.f = dks.a();
                this.f.a = dkw.e;
                this.e = new a(this);
                this.g = new dkt() {
                    public final void a() {
                        Iterator<String> it = dkr.this.b.iterator();
                        while (it.hasNext()) {
                            long currentTimeMillis = System.currentTimeMillis();
                            dkr.this.a(it.next(), dkr.this.e);
                            ye b = ye.b();
                            StringBuilder sb = new StringBuilder("onTimer doPull 消耗时长: ");
                            sb.append(System.currentTimeMillis() - currentTimeMillis);
                            b.a(sb.toString());
                        }
                    }
                };
                this.f.a(this.g);
                this.f.sendEmptyMessage(0);
                return;
            }
            throw new IllegalArgumentException("轮询配置不能为空");
        }
    }

    public final void a(String[] strArr) {
        if (strArr != null) {
            for (String add : strArr) {
                this.b.add(add);
            }
        }
    }

    public final void a() {
        this.d = true;
        this.f.b(this.g);
        this.f.removeCallbacksAndMessages(null);
    }

    public final void b() {
        if (this.d) {
            this.f.a(this.g);
            this.f.sendEmptyMessage(0);
            this.d = false;
        }
    }

    public final void c() {
        this.f.b(this.g);
        this.f.b();
        this.b.clear();
        this.f = null;
        this.a = null;
        this.g = null;
        this.c = false;
        this.d = false;
        this.e = null;
    }
}
