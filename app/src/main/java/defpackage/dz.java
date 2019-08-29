package defpackage;

import android.text.TextUtils;
import anet.channel.request.BodyEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: dz reason: default package */
/* compiled from: RequestImpl */
public final class dz implements dj {
    private String a;
    private boolean b = true;
    private List<dc> c;
    private String d = "GET";
    private List<di> e;
    private int f = 2;
    private String g = "utf-8";
    private BodyEntry h = null;
    private int i;
    private int j;
    private String k;
    private String l;
    private Map<String, String> m;

    public dz() {
    }

    public dz(String str) {
        this.a = str;
    }

    public final String a() {
        return this.a;
    }

    public final boolean b() {
        return this.b;
    }

    public final List<dc> c() {
        return this.c;
    }

    public final void a(List<dc> list) {
        this.c = list;
    }

    public final void a(String str, String str2) {
        if (str2 != null) {
            if (this.c == null) {
                this.c = new ArrayList();
            }
            this.c.add(new dv(str, str2));
        }
    }

    public final String d() {
        return this.d;
    }

    public final void a(String str) {
        this.d = str;
    }

    public final int e() {
        return this.f;
    }

    public final void a(int i2) {
        this.f = i2;
    }

    public final String g() {
        return this.g;
    }

    public final List<di> f() {
        return this.e;
    }

    public final BodyEntry h() {
        return this.h;
    }

    public final void a(BodyEntry bodyEntry) {
        this.h = bodyEntry;
    }

    public final int i() {
        return this.i;
    }

    public final int j() {
        return this.j;
    }

    public final void b(int i2) {
        this.i = i2;
    }

    public final void c(int i2) {
        this.j = i2;
    }

    @Deprecated
    public final void d(int i2) {
        this.k = String.valueOf(i2);
    }

    public final void b(String str) {
        this.k = str;
    }

    public final String k() {
        return this.k;
    }

    public final void c(String str) {
        this.l = str;
    }

    public final String l() {
        return this.l;
    }

    public final void b(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            if (this.m == null) {
                this.m = new HashMap();
            }
            this.m.put(str, str2);
        }
    }

    public final Map<String, String> m() {
        return this.m;
    }
}
