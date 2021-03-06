package com.xiaomi.smack;

import android.util.Pair;
import com.xiaomi.channel.commonutils.string.c;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.aq;
import com.xiaomi.push.service.at;
import com.xiaomi.slim.b;
import com.xiaomi.smack.packet.d;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class a {
    public static boolean a = false;
    private static final AtomicInteger p = new AtomicInteger(0);
    protected int b = 0;
    protected long c = -1;
    protected volatile long d = 0;
    protected volatile long e = 0;
    protected int f;
    protected final Map<f, C0086a> g = new ConcurrentHashMap();
    protected final Map<f, C0086a> h = new ConcurrentHashMap();
    protected com.xiaomi.smack.debugger.a i = null;
    protected String j = "";
    protected String k = "";
    protected final int l = p.getAndIncrement();
    protected b m;
    protected XMPushService n;
    protected long o = 0;
    private LinkedList<Pair<Integer, Long>> q = new LinkedList<>();
    private final Collection<d> r = new CopyOnWriteArrayList();
    private int s = 2;
    private long t = 0;

    /* renamed from: com.xiaomi.smack.a$a reason: collision with other inner class name */
    public static class C0086a {
        private f a;
        private com.xiaomi.smack.filter.a b;

        public C0086a(f fVar, com.xiaomi.smack.filter.a aVar) {
            this.a = fVar;
            this.b = aVar;
        }

        public void a(b bVar) {
            this.a.a(bVar);
        }

        public void a(d dVar) {
            if (this.b == null || this.b.a(dVar)) {
                this.a.b(dVar);
            }
        }
    }

    static {
        try {
            a = Boolean.getBoolean("smack.debugEnabled");
        } catch (Exception unused) {
        }
        g.a();
    }

    protected a(XMPushService xMPushService, b bVar) {
        this.m = bVar;
        this.n = xMPushService;
        j();
    }

    private String a(int i2) {
        return i2 == 1 ? "connected" : i2 == 0 ? "connecting" : i2 == 2 ? "disconnected" : "unknown";
    }

    private void b(int i2) {
        synchronized (this.q) {
            if (i2 == 1) {
                try {
                    this.q.clear();
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                this.q.add(new Pair(Integer.valueOf(i2), Long.valueOf(System.currentTimeMillis())));
                if (this.q.size() > 6) {
                    this.q.remove(0);
                }
            }
        }
    }

    public void a(int i2, int i3, Exception exc) {
        if (i2 != this.s) {
            com.xiaomi.channel.commonutils.logger.b.a(String.format("update the connection status. %1$s -> %2$s : %3$s ", new Object[]{a(this.s), a(i2), at.a(i3)}));
        }
        if (com.xiaomi.channel.commonutils.network.d.c(this.n)) {
            b(i2);
        }
        if (i2 == 1) {
            this.n.a(10);
            if (this.s != 0) {
                com.xiaomi.channel.commonutils.logger.b.a((String) "try set connected while not connecting.");
            }
            this.s = i2;
            for (d a2 : this.r) {
                a2.a(this);
            }
        } else if (i2 == 0) {
            if (this.s != 2) {
                com.xiaomi.channel.commonutils.logger.b.a((String) "try set connecting while not disconnected.");
            }
            this.s = i2;
            for (d b2 : this.r) {
                b2.b(this);
            }
        } else {
            if (i2 == 2) {
                this.n.a(10);
                if (this.s == 0) {
                    for (d a3 : this.r) {
                        a3.a(this, exc == null ? new CancellationException("disconnect while connecting") : exc);
                    }
                } else if (this.s == 1) {
                    for (d a4 : this.r) {
                        a4.a(this, i3, exc);
                    }
                }
                this.s = i2;
            }
        }
    }

    public abstract void a(aq.b bVar);

    public void a(d dVar) {
        if (dVar != null && !this.r.contains(dVar)) {
            this.r.add(dVar);
        }
    }

    public void a(f fVar, com.xiaomi.smack.filter.a aVar) {
        if (fVar == null) {
            throw new NullPointerException("Packet listener is null.");
        }
        this.g.put(fVar, new C0086a(fVar, aVar));
    }

    public abstract void a(d dVar);

    public synchronized void a(String str) {
        if (this.s == 0) {
            StringBuilder sb = new StringBuilder("setChallenge hash = ");
            sb.append(c.a(str).substring(0, 8));
            com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
            this.j = str;
            a(1, 0, null);
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.a((String) "ignore setChallenge because connection was disconnected");
    }

    public abstract void a(String str, String str2);

    public abstract void a(b[] bVarArr);

    public synchronized boolean a(long j2) {
        return this.t >= j2;
    }

    public abstract void b(int i2, Exception exc);

    public abstract void b(b bVar);

    public void b(d dVar) {
        this.r.remove(dVar);
    }

    public void b(f fVar, com.xiaomi.smack.filter.a aVar) {
        if (fVar == null) {
            throw new NullPointerException("Packet listener is null.");
        }
        this.h.put(fVar, new C0086a(fVar, aVar));
    }

    public abstract void b(boolean z);

    public boolean b() {
        return false;
    }

    public b d() {
        return this.m;
    }

    public String e() {
        return this.m.e();
    }

    public String f() {
        return this.m.c();
    }

    public int g() {
        return this.f;
    }

    public long h() {
        return this.e;
    }

    public void i() {
        b(0, (Exception) null);
    }

    /* access modifiers changed from: protected */
    public void j() {
        String str;
        if (this.m.f() && this.i == null) {
            Class cls = null;
            try {
                str = System.getProperty("smack.debuggerClass");
            } catch (Throwable unused) {
                str = null;
            }
            if (str != null) {
                try {
                    cls = Class.forName(str);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (cls == null) {
                this.i = new com.xiaomi.measite.smack.a(this);
                return;
            }
            try {
                this.i = (com.xiaomi.smack.debugger.a) cls.getConstructor(new Class[]{a.class, Writer.class, Reader.class}).newInstance(new Object[]{this});
            } catch (Exception e3) {
                throw new IllegalArgumentException("Can't initialize the configured debugger!", e3);
            }
        }
    }

    public boolean k() {
        return this.s == 0;
    }

    public boolean l() {
        return this.s == 1;
    }

    public int m() {
        return this.b;
    }

    public int n() {
        return this.s;
    }

    public synchronized void o() {
        this.t = System.currentTimeMillis();
    }

    public synchronized boolean p() {
        return System.currentTimeMillis() - this.t < ((long) g.b());
    }

    public synchronized boolean q() {
        return System.currentTimeMillis() - this.o < ((long) (g.b() << 1));
    }

    public void r() {
        synchronized (this.q) {
            this.q.clear();
        }
    }
}
