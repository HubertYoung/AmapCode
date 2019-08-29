package com.amap.location.e.b;

/* compiled from: NlpConfig */
public class d {
    private static volatile c a;
    private static b b;
    /* access modifiers changed from: private */
    public static a c;
    private static com.amap.location.offline.a d = new a();

    /* compiled from: NlpConfig */
    static class a implements com.amap.location.offline.a {
        private a() {
        }

        public boolean a() {
            if (d.c != null) {
                return d.c.a();
            }
            return true;
        }

        public long b() {
            if (d.c != null) {
                return d.c.b();
            }
            return 0;
        }

        public boolean c() {
            if (d.c != null) {
                return d.c.c();
            }
            return false;
        }

        public int d() {
            if (d.c != null) {
                return d.c.d();
            }
            return 6;
        }

        public int e() {
            if (d.c != null) {
                return d.c.e();
            }
            return 8;
        }

        public String[] f() {
            if (d.c != null) {
                return d.c.f();
            }
            return null;
        }

        public int g() {
            if (d.c != null) {
                return d.c.g();
            }
            return 10;
        }

        public int h() {
            if (d.c != null) {
                return d.c.h();
            }
            return 5;
        }

        public int i() {
            if (d.c != null) {
                return d.c.i();
            }
            return 100;
        }

        public boolean j() {
            if (d.c != null) {
                return d.c.j();
            }
            return false;
        }
    }

    public static b a() {
        return b;
    }

    public static void a(b bVar) {
        b = bVar;
    }

    public static a b() {
        return c;
    }

    public static com.amap.location.offline.a c() {
        return d;
    }

    public static void a(a aVar) {
        c = aVar;
    }

    public static c d() {
        return a;
    }

    public static void a(c cVar) {
        a = cVar;
    }
}
