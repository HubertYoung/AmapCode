package com.sijla.lj;

public class d {
    public int a;
    protected L b;

    public L a() {
        return this.b;
    }

    public void b() {
        this.b.a(-1001000, (long) this.a);
    }

    public boolean c() {
        boolean h;
        synchronized (this.b) {
            b();
            h = this.b.h(-1);
            this.b.l(1);
        }
        return h;
    }

    public boolean d() {
        boolean g;
        synchronized (this.b) {
            b();
            g = this.b.g(-1);
            this.b.l(1);
        }
        return g;
    }

    public boolean e() {
        boolean b2;
        synchronized (this.b) {
            b();
            b2 = this.b.b(-1);
            this.b.l(1);
        }
        return b2;
    }

    public boolean f() {
        boolean c;
        synchronized (this.b) {
            b();
            c = this.b.c(-1);
            this.b.l(1);
        }
        return c;
    }

    public boolean g() {
        boolean d;
        synchronized (this.b) {
            b();
            d = this.b.d(-1);
            this.b.l(1);
        }
        return d;
    }

    public boolean h() {
        boolean n;
        synchronized (this.b) {
            b();
            n = this.b.n(-1);
            this.b.l(1);
        }
        return n;
    }

    public boolean i() {
        boolean o;
        synchronized (this.b) {
            b();
            o = this.b.o(-1);
            this.b.l(1);
        }
        return o;
    }

    public boolean j() {
        boolean f;
        synchronized (this.b) {
            b();
            f = this.b.f(-1);
            this.b.l(1);
        }
        return f;
    }

    public boolean k() {
        boolean e;
        synchronized (this.b) {
            b();
            e = this.b.e(-1);
            this.b.l(1);
        }
        return e;
    }

    public boolean l() {
        boolean j;
        synchronized (this.b) {
            b();
            j = this.b.j(-1);
            this.b.l(1);
        }
        return j;
    }

    public double m() {
        double i;
        synchronized (this.b) {
            b();
            i = this.b.i(-1);
            this.b.l(1);
        }
        return i;
    }

    public String n() {
        String k;
        synchronized (this.b) {
            b();
            k = this.b.k(-1);
            this.b.l(1);
        }
        return k;
    }

    public Object o() {
        Object m;
        synchronized (this.b) {
            b();
            m = this.b.m(-1);
            this.b.l(1);
        }
        return m;
    }

    public String toString() {
        synchronized (this.b) {
            try {
                if (c()) {
                    return "nil";
                }
                if (d()) {
                    String valueOf = String.valueOf(l());
                    return valueOf;
                } else if (e()) {
                    String valueOf2 = String.valueOf(m());
                    return valueOf2;
                } else if (f()) {
                    String n = n();
                    return n;
                } else if (g()) {
                    return "Lua Function";
                } else {
                    if (h()) {
                        String obj = o().toString();
                        return obj;
                    } else if (k()) {
                        return "Userdata";
                    } else {
                        if (j()) {
                            return "Lua Table";
                        }
                        if (i()) {
                            return "Java Function";
                        }
                        return null;
                    }
                }
            } catch (c unused) {
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
