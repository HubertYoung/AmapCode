package com.amap.location.uptunnel.core.c;

import android.support.annotation.NonNull;
import com.amap.location.uptunnel.b.b;

/* compiled from: DataConfigProxy */
public class c implements b {
    private b a;

    public c(@NonNull b bVar) {
        this.a = bVar;
    }

    public int a() {
        return com.amap.location.common.f.c.a(this.a.a(), 0, Integer.MAX_VALUE);
    }

    public void b() {
        this.a.b();
    }

    public long c() {
        return com.amap.location.common.f.c.a(this.a.c(), 0, 20000000);
    }

    public long d() {
        return com.amap.location.common.f.c.a(this.a.d(), 60000, 86400000);
    }

    public long e() {
        return com.amap.location.common.f.c.a(this.a.e(), 1000, 3600000);
    }

    public long a(int i) {
        return com.amap.location.common.f.c.a(this.a.a(i), 1000, 10000000);
    }

    public int f() {
        return com.amap.location.common.f.c.a(this.a.f(), 1000, 600000);
    }

    public long g() {
        return com.amap.location.common.f.c.a(this.a.g(), 0, 50000000);
    }

    public long h() {
        return com.amap.location.common.f.c.a(this.a.h(), 0, Long.MAX_VALUE);
    }

    public long b(int i) {
        return com.amap.location.common.f.c.a(this.a.b(i), 0, 100000000);
    }

    public boolean c(int i) {
        return this.a.c(i);
    }
}
