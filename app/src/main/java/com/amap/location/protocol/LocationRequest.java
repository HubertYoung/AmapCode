package com.amap.location.protocol;

import com.amap.location.common.model.FPS;
import com.amap.location.common.model.HisLocation;
import com.amap.location.protocol.b.b;
import com.amap.location.protocol.e.e;
import java.util.List;

public class LocationRequest extends bpj {
    private FPS a;
    private boolean b;
    private boolean c;
    private boolean d = true;
    private byte[] e;
    private List<HisLocation> f;
    private d g;
    private int h;
    private b i;
    private String j;
    private String k;
    private boolean l;

    public LocationRequest(FPS fps, byte[] bArr, List<HisLocation> list, d dVar) {
        this.a = fps;
        this.e = bArr;
        this.f = list;
        this.g = dVar;
        this.j = e.b();
    }

    public FPS a() {
        return this.a;
    }

    public byte[] b() {
        return this.e;
    }

    public List<HisLocation> c() {
        return this.f;
    }

    public d d() {
        return this.g;
    }

    public boolean e() {
        return this.b;
    }

    public void a(boolean z) {
        this.b = z;
    }

    public boolean f() {
        return this.c;
    }

    public void b(boolean z) {
        this.c = z;
    }

    public boolean g() {
        return this.d;
    }

    public void c(boolean z) {
        this.d = z;
    }

    public int h() {
        return this.h;
    }

    public void a(int i2) {
        this.h = i2;
    }

    public b i() {
        return this.i;
    }

    public void a(b bVar) {
        this.i = bVar;
    }

    public String j() {
        return this.j;
    }

    public String k() {
        return this.k;
    }

    public void a(String str) {
        this.k = str;
    }

    public boolean l() {
        return this.l;
    }

    public void d(boolean z) {
        this.l = z;
    }
}
