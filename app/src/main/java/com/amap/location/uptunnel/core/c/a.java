package com.amap.location.uptunnel.core.c;

import android.support.annotation.NonNull;
import com.amap.location.common.f.c;

/* compiled from: CountConfigProxy */
public class a implements com.amap.location.uptunnel.b.a {
    private com.amap.location.uptunnel.b.a a;

    public a(@NonNull com.amap.location.uptunnel.b.a aVar) {
        this.a = aVar;
    }

    public long a() {
        return c.a(this.a.a(), 0, Long.MAX_VALUE);
    }

    public void b() {
        this.a.b();
    }

    public long c() {
        return c.a(this.a.c(), 0, 1000000);
    }

    public long d() {
        return c.a(this.a.d(), 60000, 86400000);
    }

    public long e() {
        return c.a(this.a.e(), 1000, 3600000);
    }

    public long a(int i) {
        return c.a(this.a.a(i), 1000, 10000000);
    }

    public int f() {
        return c.a(this.a.f(), 1000, 600000);
    }

    public long g() {
        return c.a(this.a.g(), 0, 50000000);
    }

    public long h() {
        return c.a(this.a.h(), 0, Long.MAX_VALUE);
    }

    public long b(int i) {
        return c.a(this.a.b(i), 0, 50000000);
    }

    public boolean c(int i) {
        return this.a.c(i);
    }
}
