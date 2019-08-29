package com.xiaomi.metoknlp.devicediscover;

import android.content.Context;
import com.autonavi.minimap.ajx3.util.Constants;

public class c {
    private Context a;
    private String b;
    private long c;
    private long d;
    private long e;
    private long f;

    public c(Context context) {
        this.a = context;
        a();
    }

    public void a() {
        this.b = null;
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
    }

    public void a(String str) {
        i();
        a();
        b(str);
    }

    public String b() {
        return this.b;
    }

    public void b(String str) {
        String b2 = j.b(this.a, str, Constants.ANIMATOR_NONE);
        if (b2 == null || Constants.ANIMATOR_NONE.equals(b2)) {
            a();
            this.b = str;
            long currentTimeMillis = System.currentTimeMillis();
            this.f = currentTimeMillis;
            this.e = currentTimeMillis;
            this.c = currentTimeMillis;
            return;
        }
        try {
            String[] split = b2.split("_");
            this.b = str;
            this.c = Long.valueOf(split[1]).longValue();
            this.d = Long.valueOf(split[2]).longValue();
            this.e = Long.valueOf(split[3]).longValue();
            this.f = Long.valueOf(split[4]).longValue();
        } catch (Exception unused) {
        }
    }

    public long c() {
        return this.c;
    }

    public long d() {
        return this.d;
    }

    public long e() {
        return this.f;
    }

    public void f() {
        this.d += System.currentTimeMillis() - this.c;
    }

    public void g() {
        this.f = System.currentTimeMillis();
    }

    public void h() {
        f();
        i();
        a();
    }

    public void i() {
        if (this.b != null) {
            j.a(this.a, this.b, toString());
        }
    }

    public String toString() {
        if (this.b == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.b);
        sb.append("_");
        sb.append(this.c);
        sb.append("_");
        sb.append(this.d);
        sb.append("_");
        sb.append(this.e);
        sb.append("_");
        sb.append(this.f);
        return sb.toString();
    }
}
