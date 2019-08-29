package com.amap.location.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.amap.location.b.c.e;
import com.amap.location.b.f.d;
import com.amap.location.common.e.c;
import com.amap.location.common.f.h;
import java.util.List;

/* compiled from: CollectionManager */
public class b {
    /* access modifiers changed from: private */
    public Context a;
    /* access modifiers changed from: private */
    public a b;
    /* access modifiers changed from: private */
    public c c;
    /* access modifiers changed from: private */
    public e d;
    /* access modifiers changed from: private */
    public com.amap.location.b.e.c e;
    private com.amap.location.b.b.b f;
    private com.amap.location.b.b.c g;
    /* access modifiers changed from: private */
    public HandlerThread h;
    /* access modifiers changed from: private */
    public volatile C0011b i;
    /* access modifiers changed from: private */
    public Looper j;
    /* access modifiers changed from: private */
    public boolean k = false;
    /* access modifiers changed from: private */
    public final Object l = new Object();
    /* access modifiers changed from: private */
    public a m;
    private com.amap.location.b.a.e n;
    private com.amap.location.b.g.b o;
    private com.amap.location.b.g.a p;

    /* compiled from: CollectionManager */
    class a extends BroadcastReceiver {
        private a() {
        }

        public void onReceive(Context context, Intent intent) {
            try {
                String action = intent.getAction();
                if (action != null) {
                    char c = 65535;
                    int hashCode = action.hashCode();
                    if (hashCode != -2128145023) {
                        if (hashCode == -1454123155) {
                            if (action.equals("android.intent.action.SCREEN_ON")) {
                                c = 1;
                            }
                        }
                    } else if (action.equals("android.intent.action.SCREEN_OFF")) {
                        c = 0;
                    }
                    switch (c) {
                        case 0:
                            if (b.this.b.f()) {
                                b.this.f();
                                return;
                            }
                            break;
                        case 1:
                            if (b.this.b.f()) {
                                b.this.e();
                                break;
                            }
                            break;
                    }
                }
            } catch (Throwable unused) {
            }
        }
    }

    /* renamed from: com.amap.location.b.b$b reason: collision with other inner class name */
    /* compiled from: CollectionManager */
    class C0011b extends Handler {
        C0011b(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            if (message.what == 1) {
                if (b.this.m != null) {
                    try {
                        b.this.a.unregisterReceiver(b.this.m);
                        b.this.m = null;
                    } catch (Throwable th) {
                        com.amap.location.common.d.a.a(th);
                    }
                }
                b.this.f();
                removeCallbacksAndMessages(null);
                b.this.e.b();
                b.this.d.b();
                post(new Runnable() {
                    public void run() {
                        try {
                            b.this.h.quit();
                        } catch (Throwable th) {
                            com.amap.location.common.d.a.a(th);
                        }
                    }
                });
            }
        }
    }

    public b(Context context, a aVar, c cVar) {
        this.a = context;
        this.b = aVar;
        this.c = cVar;
    }

    public void a() {
        if (d()) {
            this.h = new HandlerThread("collection") {
                /* access modifiers changed from: protected */
                public void onLooperPrepared() {
                    try {
                        b.this.j = getLooper();
                        b.this.d = new e(b.this.a, b.this.j);
                        b.this.d.a();
                        b bVar = b.this;
                        com.amap.location.b.e.c cVar = new com.amap.location.b.e.c(b.this.a, b.this.j, b.this.d, b.this.c, b.this.b);
                        bVar.e = cVar;
                        b.this.e.a();
                        synchronized (b.this.l) {
                            b.this.i = new C0011b(b.this.j);
                            if (b.this.k) {
                                b.this.k = false;
                                b.this.i.obtainMessage(1).sendToTarget();
                            }
                        }
                        if (b.this.b.f()) {
                            b.this.m = new a();
                            IntentFilter intentFilter = new IntentFilter();
                            intentFilter.addAction("android.intent.action.SCREEN_ON");
                            intentFilter.addAction("android.intent.action.SCREEN_OFF");
                            b.this.a.registerReceiver(b.this.m, intentFilter, null, b.this.i);
                            if (!d.c(b.this.a)) {
                                return;
                            }
                        }
                    } catch (Throwable unused) {
                        return;
                    }
                    b.this.e();
                }
            };
            this.h.start();
        }
    }

    public void b() {
        synchronized (this.l) {
            if (this.i != null) {
                this.i.obtainMessage(1).sendToTarget();
            } else {
                this.k = true;
            }
        }
    }

    public com.amap.location.b.e.a c() {
        com.amap.location.b.e.a aVar = null;
        if (this.i == null) {
            return null;
        }
        try {
            if (this.n == null) {
                this.n = new com.amap.location.b.a.e();
            }
            if (this.e.a(h.a(this.a)) <= 0) {
                return null;
            }
            com.amap.location.b.e.b a2 = this.e.a(true, 1, 1024);
            if (a2 == null || a2.b.size() <= 0) {
                return null;
            }
            byte[] a3 = this.n.a(this.a, this.b, a2);
            if (a3 == null) {
                return null;
            }
            com.amap.location.b.e.a aVar2 = new com.amap.location.b.e.a();
            try {
                aVar2.a = a3;
                aVar2.b = a2;
                return aVar2;
            } catch (Throwable th) {
                th = th;
                aVar = aVar2;
                com.amap.location.common.d.a.a(th);
                return aVar;
            }
        } catch (Throwable th2) {
            th = th2;
            com.amap.location.common.d.a.a(th);
            return aVar;
        }
    }

    public void a(boolean z, com.amap.location.b.e.a aVar) {
        if (!(aVar == null || this.i == null)) {
            try {
                com.amap.location.b.e.b bVar = (com.amap.location.b.e.b) aVar.b;
                this.e.a(h.a(this.a), (Object) bVar);
                if (z) {
                    this.e.a(bVar);
                }
            } catch (Throwable th) {
                com.amap.location.common.d.a.a(th);
            }
        }
    }

    private boolean d() {
        StringBuilder sb = new StringBuilder("fps open:");
        sb.append(this.b.g().a());
        sb.append("track open:");
        sb.append(this.b.h().b());
        com.amap.location.common.d.a.c("CollectionManager", sb.toString());
        return this.b.g().a() || this.b.h().b();
    }

    /* access modifiers changed from: private */
    public void e() {
        long j2;
        if (this.p == null) {
            boolean a2 = this.b.g().a();
            boolean b2 = this.b.h().b();
            long j3 = 0;
            int i2 = 0;
            if (a2) {
                j3 = 1000;
                i2 = 10;
            }
            if (b2) {
                j2 = a2 ? Math.min(j2, 2000) : 2000;
                i2 = a2 ? Math.min(i2, 5) : 5;
            }
            try {
                this.p = new com.amap.location.b.g.a() {
                    public void a(Location location, List<ScanResult> list, long j, long j2) {
                        b.this.a(location, list, j, j2);
                    }
                };
                if (this.o == null) {
                    this.o = new com.amap.location.b.g.b(this.a, this.b.g(), this.p, this.j);
                }
                this.o.a("passive", j2, (float) i2);
            } catch (Throwable th) {
                com.amap.location.common.d.a.a(th);
            }
        }
    }

    /* access modifiers changed from: private */
    public void f() {
        try {
            if (!(this.p == null || this.o == null)) {
                this.o.c();
                this.o.a();
                this.p = null;
                h();
                com.amap.location.b.d.a.a();
            }
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
        }
    }

    /* access modifiers changed from: private */
    public void a(Location location, List<ScanResult> list, long j2, long j3) {
        try {
            g();
            if (this.b.g().a()) {
                this.f.a(location, list, j2, j3);
            }
            if (this.b.h().b()) {
                this.g.a(location);
            }
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
        }
    }

    private void g() {
        if (this.b.g().a() && this.f == null) {
            this.f = new com.amap.location.b.b.b(this.a, this.d, this.b.g(), this.j);
            this.f.a();
        }
        if (this.b.h().b() && this.g == null) {
            this.g = new com.amap.location.b.b.c(this.a, this.d, this.b.h(), this.j);
            this.g.a();
        }
    }

    private void h() {
        if (this.f != null) {
            this.f.b();
            this.f = null;
        }
        if (this.g != null) {
            this.g.b();
            this.g = null;
        }
    }
}
