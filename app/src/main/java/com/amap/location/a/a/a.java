package com.amap.location.a.a;

import com.amap.location.common.e.c;

/* compiled from: CloudConfig */
public class a {
    public static boolean a = false;
    private byte b = -1;
    private String c = "";
    private String d = "";
    private String e = "";
    private String f = "";
    private String g = "";
    private c h;

    public void a(c cVar) {
        this.h = cVar;
    }

    public void a(byte b2) {
        this.b = b2;
    }

    public void a(String str) {
        this.c = str;
    }

    public void b(String str) {
        this.d = str;
    }

    public void c(String str) {
        this.e = str;
    }

    public void d(String str) {
        if (str != null) {
            this.g = str;
        }
    }

    public void e(String str) {
        if (str != null) {
            this.f = str;
        }
    }

    public byte a() {
        return this.b;
    }

    public String b() {
        return this.c;
    }

    public String c() {
        return this.d;
    }

    public String d() {
        return this.e;
    }

    public String e() {
        return this.g;
    }

    public c f() {
        return this.h;
    }
}
