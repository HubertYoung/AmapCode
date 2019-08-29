package com.amap.location.common.d;

/* compiled from: LogConfig */
public class d {
    /* access modifiers changed from: private */
    public boolean a;
    /* access modifiers changed from: private */
    public boolean b;
    /* access modifiers changed from: private */
    public boolean c;
    /* access modifiers changed from: private */
    public String d;
    /* access modifiers changed from: private */
    public c e;
    /* access modifiers changed from: private */
    public b f;
    private boolean g;
    private boolean h;
    private int i;
    private int j;
    private int k;

    /* compiled from: LogConfig */
    public static class a {
        private d a = new d();

        public a a(boolean z) {
            this.a.a = z;
            return this;
        }

        public a b(boolean z) {
            this.a.b = z;
            return this;
        }

        public a c(boolean z) {
            this.a.c = z;
            return this;
        }

        public a a(b bVar) {
            this.a.f = bVar;
            return this;
        }

        public d a(c cVar, String str) {
            if (cVar == null) {
                throw new IllegalArgumentException("product 不能为 null ");
            }
            if (this.a.b && (str == null || str.trim().length() == 0)) {
                this.a.b = false;
                str = null;
            }
            this.a.e = cVar;
            this.a.d = str;
            return this.a;
        }
    }

    /* compiled from: LogConfig */
    public interface b {
        void a(String str);

        boolean a();
    }

    /* compiled from: LogConfig */
    public enum c {
        FLP,
        NLP,
        SDK
    }

    private d() {
        this.a = false;
        this.b = false;
        this.c = false;
        this.d = "";
        this.e = c.SDK;
        this.g = false;
        this.h = true;
        this.i = 204800;
        this.j = 1048576;
        this.k = 20;
    }

    public boolean a() {
        return this.a;
    }

    public boolean b() {
        return this.b;
    }

    public boolean c() {
        return this.c;
    }

    public boolean d() {
        return this.h;
    }

    public boolean e() {
        return this.g;
    }

    public int f() {
        return this.k;
    }

    public int g() {
        return this.i;
    }

    public int h() {
        return this.j;
    }

    public String i() {
        return this.d;
    }

    public c j() {
        return this.e;
    }

    public b k() {
        return this.f;
    }
}
