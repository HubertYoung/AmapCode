package com.amap.location.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import com.amap.location.a.a.b;
import com.amap.location.common.f.f;
import com.amap.location.security.Core;
import com.google.flatbuffers.FlatBufferBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: LocationCloudScheduler */
public class c {
    private com.amap.location.a.a.a a;
    private Context b;
    /* access modifiers changed from: private */
    public Handler c;
    /* access modifiers changed from: private */
    public a d;
    /* access modifiers changed from: private */
    public ReentrantReadWriteLock e = new ReentrantReadWriteLock();
    private final List<com.amap.location.a.b.a> f = new ArrayList();
    private a g;
    /* access modifiers changed from: private */
    public boolean h;
    private Runnable i = new Runnable() {
        public void run() {
            c.this.e();
        }
    };

    /* compiled from: LocationCloudScheduler */
    final class a extends HandlerThread {
        protected volatile boolean a;

        public a(String str, int i) {
            super(str, i);
        }

        /* access modifiers changed from: protected */
        public final void onLooperPrepared() {
            c.this.e.writeLock().lock();
            try {
                if (this.a) {
                    Looper looper = getLooper();
                    if (looper != null) {
                        looper.quit();
                    }
                    return;
                }
                c.this.c = new Handler(Looper.myLooper());
                c.this.e.writeLock().unlock();
                try {
                    c.this.b();
                    c.this.c();
                } catch (Throwable th) {
                    com.amap.location.common.d.a.a(th);
                }
            } finally {
                c.this.e.writeLock().unlock();
            }
        }
    }

    protected c() {
    }

    /* access modifiers changed from: protected */
    public void a(Context context, com.amap.location.a.a.a aVar) {
        this.b = context;
        this.a = aVar;
        this.g = new a("LocationCloudScheduler", 10);
        this.g.a = false;
        this.g.start();
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    public void a(final com.amap.location.a.b.a aVar) {
        if (aVar != null) {
            synchronized (this.f) {
                if (!this.f.contains(aVar)) {
                    this.e.readLock().lock();
                    try {
                        Handler handler = this.c;
                        if (handler != null) {
                            handler.post(new Runnable() {
                                public void run() {
                                    if (c.this.d != null) {
                                        a aVar = new a();
                                        aVar.e = c.this.d.e;
                                        aVar.b = c.this.d.b;
                                        aVar.a(aVar);
                                        return;
                                    }
                                    if (c.this.h) {
                                        aVar.a();
                                    }
                                }
                            });
                        }
                        this.e.readLock().unlock();
                        this.f.add(aVar);
                    } catch (Throwable th) {
                        this.e.readLock().unlock();
                        throw th;
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void b(com.amap.location.a.b.a aVar) {
        if (aVar != null) {
            synchronized (this.f) {
                if (this.f.contains(aVar)) {
                    this.f.remove(aVar);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a() {
        if (this.g != null) {
            this.g.a = true;
        }
        this.e.writeLock().lock();
        final Handler handler = this.c;
        this.c = null;
        this.e.writeLock().unlock();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler.post(new Runnable() {
                public void run() {
                    Looper looper = handler.getLooper();
                    if (looper != null) {
                        looper.quit();
                    }
                }
            });
        }
        synchronized (this.f) {
            this.f.clear();
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        SharedPreferences sharedPreferences = this.b.getSharedPreferences("LocationCloudConfig", 0);
        String string = sharedPreferences.getString("command", "");
        long j = sharedPreferences.getLong("lasttime", 0);
        if (!TextUtils.isEmpty(string) && b.a(string)) {
            a aVar = new a();
            if (aVar.a(string)) {
                aVar.d = j;
                this.d = aVar;
                a(aVar);
                com.amap.location.common.d.a.b("loccldmain", "load local success!");
                return;
            }
        }
        com.amap.location.common.d.a.c("loccldmain", "load local failed");
        g();
    }

    private void a(String str) {
        SharedPreferences sharedPreferences = this.b.getSharedPreferences("LocationCloudConfig", 0);
        a aVar = new a();
        if (aVar.a(str)) {
            long currentTimeMillis = System.currentTimeMillis();
            sharedPreferences.edit().putString("command", str).putLong("lasttime", currentTimeMillis).commit();
            aVar.d = currentTimeMillis;
            this.d = aVar;
            a(aVar);
            this.e.readLock().lock();
            if (this.c != null) {
                this.c.postDelayed(this.i, this.d.a);
            }
            this.e.readLock().unlock();
            StringBuilder sb = new StringBuilder("load net cloud success ");
            sb.append(this.d.a);
            com.amap.location.common.d.a.b("loccldmain", sb.toString());
            return;
        }
        h();
        com.amap.location.common.d.a.c("loccldmain", "load net cloud failed ");
    }

    /* access modifiers changed from: private */
    public void c() {
        this.e.readLock().lock();
        try {
            if (this.c != null) {
                if (d()) {
                    this.c.post(this.i);
                } else {
                    this.c.postDelayed(this.i, this.d.a);
                }
            }
        } finally {
            this.e.readLock().unlock();
        }
    }

    private boolean d() {
        if (this.d == null) {
            return true;
        }
        long currentTimeMillis = System.currentTimeMillis() - this.d.d;
        if (currentTimeMillis >= this.d.a || currentTimeMillis < 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void e() {
        com.amap.location.common.d.a.b("loccldmain", "start net request");
        byte[] f2 = f();
        if (f2 != null) {
            a(b.a(com.amap.location.a.a.a.a ? "http://aps.testing.amap.com/conf/r?type=3&mid=300&sver=140" : "http://control.aps.amap.com/conf/r?type=3&mid=300&sver=140", f2, this.a));
            return;
        }
        com.amap.location.common.d.a.c("loccldmain", "build request failed");
        h();
    }

    private byte[] f() {
        try {
            FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder();
            int createString = flatBufferBuilder.createString((CharSequence) this.b.getPackageName());
            int createString2 = flatBufferBuilder.createString((CharSequence) this.a.b());
            int createString3 = flatBufferBuilder.createString((CharSequence) com.amap.location.common.a.a());
            String e2 = this.a.e();
            if (TextUtils.isEmpty(e2)) {
                e2 = com.amap.location.common.a.b(this.b);
            }
            int createString4 = flatBufferBuilder.createString((CharSequence) e2);
            int createString5 = flatBufferBuilder.createString((CharSequence) com.amap.location.common.a.a(this.b));
            int createString6 = flatBufferBuilder.createString((CharSequence) com.amap.location.common.a.d(this.b));
            int createString7 = flatBufferBuilder.createString((CharSequence) com.amap.location.common.a.c());
            int createString8 = flatBufferBuilder.createString((CharSequence) com.amap.location.common.a.b());
            int createString9 = flatBufferBuilder.createString((CharSequence) this.a.d());
            int createString10 = flatBufferBuilder.createString((CharSequence) this.a.c());
            com.amap.location.common.b.b.a(flatBufferBuilder);
            com.amap.location.common.b.b.a(flatBufferBuilder, this.a.a());
            com.amap.location.common.b.b.a(flatBufferBuilder, createString);
            com.amap.location.common.b.b.b(flatBufferBuilder, createString2);
            com.amap.location.common.b.b.b(flatBufferBuilder, (byte) com.amap.location.common.a.d());
            com.amap.location.common.b.b.c(flatBufferBuilder, createString3);
            com.amap.location.common.b.b.d(flatBufferBuilder, createString4);
            com.amap.location.common.b.b.e(flatBufferBuilder, createString5);
            com.amap.location.common.b.b.f(flatBufferBuilder, createString6);
            com.amap.location.common.b.b.a(flatBufferBuilder, com.amap.location.common.a.e(this.b));
            com.amap.location.common.b.b.g(flatBufferBuilder, createString7);
            com.amap.location.common.b.b.h(flatBufferBuilder, createString8);
            com.amap.location.common.b.b.i(flatBufferBuilder, createString9);
            com.amap.location.common.b.b.j(flatBufferBuilder, createString10);
            flatBufferBuilder.finish(com.amap.location.common.b.b.b(flatBufferBuilder));
            return Core.xxt(flatBufferBuilder.sizedByteArray(), 1);
        } catch (Error | Exception unused) {
            return null;
        }
    }

    private void a(byte[] bArr) {
        String b2 = b(bArr);
        if (b2 != null) {
            a(b2);
            return;
        }
        com.amap.location.common.d.a.c("loccldmain", "net server check failed");
        h();
    }

    private String b(byte[] bArr) {
        if (bArr != null) {
            try {
                byte[] xxt = Core.xxt(f.b(bArr), -1);
                if (xxt == null) {
                    return null;
                }
                String intern = new String(xxt, "utf-8").intern();
                if (b.a(intern)) {
                    return intern;
                }
            } catch (Exception unused) {
            }
        }
        return null;
    }

    private void a(a aVar) {
        synchronized (this.f) {
            for (int i2 = 0; i2 < this.f.size(); i2++) {
                this.f.get(i2).a(aVar);
            }
        }
    }

    private void g() {
        this.h = true;
        synchronized (this.f) {
            for (int i2 = 0; i2 < this.f.size(); i2++) {
                this.f.get(i2).a();
            }
        }
    }

    private void h() {
        this.e.readLock().lock();
        try {
            if (this.c != null) {
                this.c.postDelayed(this.i, 3600000);
            }
        } finally {
            this.e.readLock().unlock();
        }
    }
}
